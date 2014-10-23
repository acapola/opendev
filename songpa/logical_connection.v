//Modules for conditional logical connection of digital signals
//with some security against fault attack and FIB attacks
//outputs are connected to inputs if the condition holds otherwise they are forced to 0 
//sebastien riou, 20141003

//Connect inputs i_dat to outputs o_dat only if i_password==PASSWORD
//This provides some resistance against fault injection and FIB
module logical_connection #(
	parameter PASSWORD = 32'hC1B269AE, //should have a medium hamming weight
	parameter WIDTH = 10
)(
	input wire [31:0] i_password,
	input wire [WIDTH-1:0] i_dat,
	output wire [WIDTH-1:0] o_dat
);
connect_if_match #(
	.WIDTH(WIDTH),
	.CMP_WIDTH(32),
	.SECURITY_LEVEL(8)
	) impl (
	.i_a(i_password),
	.i_b(PASSWORD),
	.i_dat(i_dat),
	.o_dat(o_dat)
	);
endmodule


//Connect inputs i_dat to outputs o_dat only if i_a==i_b
//This provides some resistance against fault injection and FIB
module connect_if_match #(
	parameter WIDTH = 10,
	parameter CMP_WIDTH = 32,
	parameter SECURITY_LEVEL = 8
)(
	input wire [CMP_WIDTH-1:0] i_a,
	input wire [CMP_WIDTH-1:0] i_b,
	input wire [WIDTH-1:0] i_dat,
	output reg [WIDTH-1:0] o_dat
);
reg [WIDTH-1:0] ena;
reg [WIDTH-1:0] dis;
reg [WIDTH-1:0] tmp;
always @* tmp   = ~(i_dat & ena);
always @* o_dat = ~(tmp   | dis);

function [CMP_WIDTH-1:0] hamming_weight;
	input [CMP_WIDTH-1:0] in;
	integer i;
	integer out;
	begin
		out = 0;
		for(i=0;i<CMP_WIDTH;i=i+1) out = out + in[i];
		hamming_weight = out;
	end
endfunction

function [CMP_WIDTH-1:0] next_with_hamming_weight;
	input [CMP_WIDTH-1:0] in;
	input [CMP_WIDTH-1:0] target_hamming_weight;
	reg [CMP_WIDTH-1:0] i;
	begin
		i=in+1;
		while(hamming_weight(i)!=target_hamming_weight) i = i+1;
		next_with_hamming_weight = i;
	end
endfunction

wire [CMP_WIDTH-1:0] match = i_a ^ ~i_b;
always @* begin: DIFFUSION
	integer i;
	reg [CMP_WIDTH-1:0] bit_mask;
	bit_mask = {CMP_WIDTH{1'b0}};
	for(i=0;i<WIDTH;i=i+1) begin
		bit_mask = next_with_hamming_weight(bit_mask,SECURITY_LEVEL);
		ena[i] = &(match | ~bitmask);
	end
	
	for(i=0;i<WIDTH;i=i+1) begin
		bit_mask = next_with_hamming_weight(bit_mask,SECURITY_LEVEL);
		dis[i] = ~ (&(match | ~bitmask));
	end
end
endmodule
