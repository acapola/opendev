`backtick`timescale 1ns / 1ps
``set UNIT_WIDTH_MAX 16``
module keyed_permutation_tb();

localparam UNIT_WIDTH = 1;//MAX is `$UNIT_WIDTH_MAX`
localparam NUNITS = `$NUNITS`;
localparam INDEX_WIDTH = `$INDEX_WIDTH`;
localparam KEY_WIDTH = `$KEY_WIDTH`;

reg i_clk;
reg [NUNITS*UNIT_WIDTH-1:0] i_dat;
reg [NUNITS*UNIT_WIDTH-1:0] i_dat_d1;
reg [NUNITS*UNIT_WIDTH-1:0] i_dat_d2;
reg [KEY_WIDTH-1:0] i_key;
reg [KEY_WIDTH-1:0] i_key_d1;
wire [NUNITS*UNIT_WIDTH-1:0] o_dat;
wire [NUNITS*UNIT_WIDTH-1:0] unpermuted;
always @(posedge i_clk) {i_key_d1,i_dat_d2,i_dat_d1} <= {i_key,i_dat_d1,i_dat};
keyed_permutation dut(
    .i_clk(i_clk),
    .i_dat(i_dat),
    .i_key(i_key),
    .i_inverse(1'b0),
    .o_dat(o_dat)
    );
    
keyed_permutation checker(
        .i_clk(i_clk),
        .i_dat(o_dat),
        .i_key(i_key_d1),
        .i_inverse(1'b1),
        .o_dat(unpermuted)
        );
integer i,j;
integer cnt;
wire error = cnt>3 ? unpermuted !== i_dat_d2 : 0;

initial begin
    cnt=0;
    for(i=0;i<10;i=i+1) begin
        i_key = {`string repeat "\$random," [expr $KEY_WIDTH/32]`$random};
        for(j=0;j<10;j=j+1) begin
            i_dat = {`string repeat "\$random," [expr ($NUNITS*$UNIT_WIDTH_MAX)/32]`$random};
            @(negedge i_clk);
            cnt=cnt+1;
        end
    end
    @(negedge i_clk);
    @(negedge i_clk);
    @(negedge i_clk);
    
end

initial begin
    i_clk = 0;
    forever i_clk = #10 ~i_clk;
end
endmodule
