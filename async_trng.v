module ring_stage_basic(
	input wire i_f,
	input wire i_r,
	output reg o_c
	);
always @* begin
	if( i_f & ~i_r) o_c = 1'b1;
	if(~i_f &  i_r) o_c = 1'b0;
end	
endmodule

module async_ring1 #(
	parameter WIDTH = 5,
	parameter INIT_VALUE = 5'b01010
	)(
	input wire i_reset,
	output wire [WIDTH-1:0] o_dat
	);
	
wire [WIDTH-1:0] f;
wire [WIDTH-1:0] r;	
wire [WIDTH-1:0] c;	
always @* o_dat = i_reset ? INIT_VALUE : c;
genvar i
generate
	for(i=0;i<WIDTH;i=i+1) begin: STAGE
		ring_stage stage(.i_f(f[i]), .i_r(r[i]), .o_c(c[i]));
		always @* f[i] = o_dat[(i+WIDTH+1)%WIDTH];
		always @* r[i] = o_dat[(i+1)%WIDTH];
	end
endgenerate
endmodule


module ring_stage(
	input wire i_reset,
	input wire i_init_val,
	input wire i_f,
	input wire i_r,
	output wire o_c
	);
always @* begin
	if(i_reset) o_c = i_init_val;
	else begin
		if(      i_f & ~i_r) o_c = 1'b1;
		else if(~i_f &  i_r) o_c = 1'b0;
	end
end	
endmodule

module ring_stage_spartan6(
	input wire i_reset,
	input wire i_init_val,
	input wire i_f,
	input wire i_r,
	output wire o_c
	);
wire LO;
LUT5_D #(
//i_reset     1111 1111 1111 1111   0000 0000 0000 0000 
//i_init_val  1111 1111 0000 0000   1111 1111 0000 0000   
//LO          1111 0000 1111 0000   1111 0000 1111 0000   
//i_r         1100 1100 1100 1100   1100 1100 1100 1100
//i_f         1010 1010 1010 1010   1010 1010 1010 1010   
	.INIT(32'b1111_1111_0000_0000___1011_0010_1011_0010) // Specify LUT Contents
) LUT5_D_inst (
.LO(LO), // LUT local output
.O(o_c), // LUT general output
.I0(i_f), // LUT input
.I1(i_r), // LUT input
.I2(LO), // LUT input
.I3(i_init_val), // LUT input
.I4(i_reset) // LUT input
);	
endmodule


module async_ring #(
	parameter WIDTH = 5,
	)(
	input wire i_reset,
	input wire [WIDTH-1:0] i_init_val,
	output wire [WIDTH-1:0] o_dat
	);
	
wire [WIDTH-1:0] f;
wire [WIDTH-1:0] r;	
genvar i
generate
	for(i=0;i<WIDTH;i=i+1) begin: STAGE
		ring_stage stage(.i_reset(i_reset), .i_init_val(i_init_val[i]), .i_f(f[i]), .i_r(r[i]), .o_c(o_dat[i]));
		always @* f[i] = o_dat[(i+WIDTH+1)%WIDTH];
		always @* r[i] = o_dat[(i+1)%WIDTH];
	end
endgenerate
endmodule

module entropy_extractor #(
	parameter WIDTH = 5
	)(
	input wire i_clk,
	input wire [WIDTH-1:0] i_rnd_src,
	output reg [WIDTH-1:0] o_sampled,//to avoid automatic optimization and allow testing
	output reg o_rnd
);
always @(posedge i_clk) o_sampled <= i_rnd_src;
always @* o_dat = ^ o_sampled;
endmodule

module async_trng #(
	parameter WIDTH = 8,//max is 255
	parameter SRC_WIDTH = 5//,parameter SRC_INIT_VALUE = 5'b01010
	)(
	input wire i_reset,
	input wire i_src_init_val,
	input wire i_clk,
	input wire i_read,
	output reg [WIDTH-1:0] o_dat,
	output reg o_valid,
	output wire [SRC_WIDTH-1:0] o_sampled
);
wire raw_rnd;
wire [SRC_WIDTH-1:0] rnd_src_dat;
async_ring #(.WIDTH(SRC_WIDTH)) rnd_src (.i_reset(i_reset), .i_init_val(i_src_init_val), .o_dat(rnd_src_dat));
entropy_extractor #(.WIDTH(SRC_WIDTH)) extractor (.i_clk(i_clk), .i_rnd_src(rnd_src_dat),
	.o_sampled(o_sampled), .o_rnd(raw_rnd));
localparam CNT_WIDTH = 8;
reg [CNT_WIDTH-1:0] cnt;	
always @(posedge i_clk) begin
	if(i_reset) cnt <= {CNT_WIDTH{1'b0}};
	else begin
		o_dat <= {o_dat[0+:WIDTH-1],raw_rnd};
		if(i_read) {CNT_WIDTH{1'b0}};
		else cnt <= cnt + 1'b1;
	end
end
always @* o_valid = cnt==WIDTH;
endmodule
	