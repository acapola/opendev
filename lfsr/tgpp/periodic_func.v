// Functions for low power/high frequency periodic signal
// Period:         255
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_255_8_bits_init_f;
//     else        state <= period_255_8_bits_next_f;
// end
function automatic [7:0] period_255_8_bits_init_f;
    begin
        period_255_8_bits_init_f = 8'b10000000;
    end
endfunction
function automatic [7:0] period_255_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_255_8_bits_next_f = out;
    end
endfunction
