// Functions for low power/high frequency periodic signal
// Period:         3
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_3_4_bits_val_f(0);
//     else        state <= period_3_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_3_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_3_4_bits_val_f(3);
function automatic [3:0] period_3_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1010;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_3_4_bits_next_f(tmp);
		period_3_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_3_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]&in[0]);
		out[3] = in[0];
        period_3_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         4
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_4_4_bits_val_f(0);
//     else        state <= period_4_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_4_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_4_4_bits_val_f(3);
function automatic [3:0] period_4_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b0000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_4_4_bits_next_f(tmp);
		period_4_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_4_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]~&in[0]);
		out[2] = in[3];
		out[3] = in[0];
        period_4_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         5
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_5_4_bits_val_f(0);
//     else        state <= period_5_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_5_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_5_4_bits_val_f(3);
function automatic [3:0] period_5_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1110;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_5_4_bits_next_f(tmp);
		period_5_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_5_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]~&in[0]);
		out[2] = in[3];
		out[3] = in[0];
        period_5_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         6
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_6_4_bits_val_f(0);
//     else        state <= period_6_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_6_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_6_4_bits_val_f(3);
function automatic [3:0] period_6_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_6_4_bits_next_f(tmp);
		period_6_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_6_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]&in[0]);
		out[3] = in[0];
        period_6_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         7
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_7_4_bits_val_f(0);
//     else        state <= period_7_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_7_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_7_4_bits_val_f(3);
function automatic [3:0] period_7_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b0000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_7_4_bits_next_f(tmp);
		period_7_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_7_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[0]&in[0]);
        period_7_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         8
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_8_4_bits_val_f(0);
//     else        state <= period_8_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_8_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_8_4_bits_val_f(3);
function automatic [3:0] period_8_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_8_4_bits_next_f(tmp);
		period_8_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_8_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]&in[0]);
		out[3] = (in[0]^in[0]);
        period_8_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         9
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_9_4_bits_val_f(0);
//     else        state <= period_9_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_9_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_9_4_bits_val_f(3);
function automatic [3:0] period_9_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1001;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_9_4_bits_next_f(tmp);
		period_9_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_9_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]&in[0]);
		out[3] = (in[0]^in[0]);
        period_9_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         10
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_10_4_bits_val_f(0);
//     else        state <= period_10_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_10_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_10_4_bits_val_f(3);
function automatic [3:0] period_10_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_10_4_bits_next_f(tmp);
		period_10_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_10_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[0];
        period_10_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         11
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_11_4_bits_val_f(0);
//     else        state <= period_11_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_11_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_11_4_bits_val_f(3);
function automatic [3:0] period_11_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_11_4_bits_next_f(tmp);
		period_11_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_11_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[0]&(in[1]|in[0]));
        period_11_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         12
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_12_4_bits_val_f(0);
//     else        state <= period_12_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_12_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_12_4_bits_val_f(3);
function automatic [3:0] period_12_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b0000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_12_4_bits_next_f(tmp);
		period_12_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_12_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[0];
        period_12_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         13
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_13_4_bits_val_f(0);
//     else        state <= period_13_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_13_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_13_4_bits_val_f(3);
function automatic [3:0] period_13_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_13_4_bits_next_f(tmp);
		period_13_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_13_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[0];
        period_13_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         14
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_14_4_bits_val_f(0);
//     else        state <= period_14_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_14_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_14_4_bits_val_f(3);
function automatic [3:0] period_14_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b0000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_14_4_bits_next_f(tmp);
		period_14_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_14_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[0];
        period_14_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         15
// Number of bits: 4
// 
// Typical usage:
// reg [4-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_15_4_bits_val_f(0);
//     else        state <= period_15_4_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_15_4_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_15_4_bits_val_f(3);
function automatic [3:0] period_15_4_bits_val_f;
	input integer target_step;
	reg [3:0] tmp;
	integer i;
	begin
		tmp = 4'b1000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_15_4_bits_next_f(tmp);
		period_15_4_bits_val_f = tmp;
	end
endfunction
function automatic [3:0] period_15_4_bits_next_f;
    input [3:0] in;
    reg [3:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[0];
        period_15_4_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         16
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_16_5_bits_val_f(0);
//     else        state <= period_16_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_16_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_16_5_bits_val_f(3);
function automatic [4:0] period_16_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_16_5_bits_next_f(tmp);
		period_16_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_16_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[0];
        period_16_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         17
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_17_5_bits_val_f(0);
//     else        state <= period_17_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_17_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_17_5_bits_val_f(3);
function automatic [4:0] period_17_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_17_5_bits_next_f(tmp);
		period_17_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_17_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[0];
        period_17_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         18
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_18_5_bits_val_f(0);
//     else        state <= period_18_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_18_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_18_5_bits_val_f(3);
function automatic [4:0] period_18_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_18_5_bits_next_f(tmp);
		period_18_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_18_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[0];
        period_18_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         19
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_19_5_bits_val_f(0);
//     else        state <= period_19_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_19_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_19_5_bits_val_f(3);
function automatic [4:0] period_19_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b01000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_19_5_bits_next_f(tmp);
		period_19_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_19_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = ~in[3];
		out[3] = (in[4]&(in[1]|in[0]));
		out[4] = (in[0]^in[0]);
        period_19_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         20
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_20_5_bits_val_f(0);
//     else        state <= period_20_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_20_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_20_5_bits_val_f(3);
function automatic [4:0] period_20_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_20_5_bits_next_f(tmp);
		period_20_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_20_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[0];
        period_20_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         21
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_21_5_bits_val_f(0);
//     else        state <= period_21_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_21_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_21_5_bits_val_f(3);
function automatic [4:0] period_21_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_21_5_bits_next_f(tmp);
		period_21_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_21_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[0];
        period_21_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         22
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_22_5_bits_val_f(0);
//     else        state <= period_22_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_22_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_22_5_bits_val_f(3);
function automatic [4:0] period_22_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_22_5_bits_next_f(tmp);
		period_22_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_22_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[0];
        period_22_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         23
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_23_5_bits_val_f(0);
//     else        state <= period_23_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_23_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_23_5_bits_val_f(3);
function automatic [4:0] period_23_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_23_5_bits_next_f(tmp);
		period_23_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_23_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[0];
        period_23_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         24
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_24_5_bits_val_f(0);
//     else        state <= period_24_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_24_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_24_5_bits_val_f(3);
function automatic [4:0] period_24_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_24_5_bits_next_f(tmp);
		period_24_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_24_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[0];
        period_24_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         25
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_25_5_bits_val_f(0);
//     else        state <= period_25_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_25_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_25_5_bits_val_f(3);
function automatic [4:0] period_25_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_25_5_bits_next_f(tmp);
		period_25_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_25_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[0];
        period_25_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         26
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_26_5_bits_val_f(0);
//     else        state <= period_26_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_26_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_26_5_bits_val_f(3);
function automatic [4:0] period_26_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_26_5_bits_next_f(tmp);
		period_26_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_26_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[0];
        period_26_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         27
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_27_5_bits_val_f(0);
//     else        state <= period_27_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_27_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_27_5_bits_val_f(3);
function automatic [4:0] period_27_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_27_5_bits_next_f(tmp);
		period_27_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_27_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[0];
        period_27_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         28
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_28_5_bits_val_f(0);
//     else        state <= period_28_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_28_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_28_5_bits_val_f(3);
function automatic [4:0] period_28_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_28_5_bits_next_f(tmp);
		period_28_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_28_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[0];
        period_28_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         29
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_29_5_bits_val_f(0);
//     else        state <= period_29_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_29_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_29_5_bits_val_f(3);
function automatic [4:0] period_29_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_29_5_bits_next_f(tmp);
		period_29_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_29_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[0];
        period_29_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         30
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_30_5_bits_val_f(0);
//     else        state <= period_30_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_30_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_30_5_bits_val_f(3);
function automatic [4:0] period_30_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b00000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_30_5_bits_next_f(tmp);
		period_30_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_30_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[0];
        period_30_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         31
// Number of bits: 5
// 
// Typical usage:
// reg [5-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_31_5_bits_val_f(0);
//     else        state <= period_31_5_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_31_5_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_31_5_bits_val_f(3);
function automatic [4:0] period_31_5_bits_val_f;
	input integer target_step;
	reg [4:0] tmp;
	integer i;
	begin
		tmp = 5'b10000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_31_5_bits_next_f(tmp);
		period_31_5_bits_val_f = tmp;
	end
endfunction
function automatic [4:0] period_31_5_bits_next_f;
    input [4:0] in;
    reg [4:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[0];
        period_31_5_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         32
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_32_6_bits_val_f(0);
//     else        state <= period_32_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_32_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_32_6_bits_val_f(3);
function automatic [5:0] period_32_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_32_6_bits_next_f(tmp);
		period_32_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_32_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[0];
        period_32_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         33
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_33_6_bits_val_f(0);
//     else        state <= period_33_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_33_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_33_6_bits_val_f(3);
function automatic [5:0] period_33_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b110000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_33_6_bits_next_f(tmp);
		period_33_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_33_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = (in[0]^in[0]);
        period_33_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         34
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_34_6_bits_val_f(0);
//     else        state <= period_34_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_34_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_34_6_bits_val_f(3);
function automatic [5:0] period_34_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_34_6_bits_next_f(tmp);
		period_34_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_34_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[0];
        period_34_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         35
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_35_6_bits_val_f(0);
//     else        state <= period_35_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_35_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_35_6_bits_val_f(3);
function automatic [5:0] period_35_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b111000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_35_6_bits_next_f(tmp);
		period_35_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_35_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[0];
        period_35_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         36
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_36_6_bits_val_f(0);
//     else        state <= period_36_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_36_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_36_6_bits_val_f(3);
function automatic [5:0] period_36_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_36_6_bits_next_f(tmp);
		period_36_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_36_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[0];
        period_36_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         37
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_37_6_bits_val_f(0);
//     else        state <= period_37_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_37_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_37_6_bits_val_f(3);
function automatic [5:0] period_37_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b010000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_37_6_bits_next_f(tmp);
		period_37_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_37_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = (in[0]^in[0]);
        period_37_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         38
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_38_6_bits_val_f(0);
//     else        state <= period_38_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_38_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_38_6_bits_val_f(3);
function automatic [5:0] period_38_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_38_6_bits_next_f(tmp);
		period_38_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_38_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[0];
        period_38_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         39
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_39_6_bits_val_f(0);
//     else        state <= period_39_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_39_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_39_6_bits_val_f(3);
function automatic [5:0] period_39_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_39_6_bits_next_f(tmp);
		period_39_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_39_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[0];
        period_39_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         40
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_40_6_bits_val_f(0);
//     else        state <= period_40_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_40_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_40_6_bits_val_f(3);
function automatic [5:0] period_40_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b001000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_40_6_bits_next_f(tmp);
		period_40_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_40_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = (in[5]^in[0]);
		out[5] = in[0];
        period_40_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         41
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_41_6_bits_val_f(0);
//     else        state <= period_41_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_41_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_41_6_bits_val_f(3);
function automatic [5:0] period_41_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_41_6_bits_next_f(tmp);
		period_41_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_41_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[0];
        period_41_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         42
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_42_6_bits_val_f(0);
//     else        state <= period_42_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_42_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_42_6_bits_val_f(3);
function automatic [5:0] period_42_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_42_6_bits_next_f(tmp);
		period_42_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_42_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[0];
        period_42_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         43
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_43_6_bits_val_f(0);
//     else        state <= period_43_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_43_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_43_6_bits_val_f(3);
function automatic [5:0] period_43_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_43_6_bits_next_f(tmp);
		period_43_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_43_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[0];
        period_43_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         44
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_44_6_bits_val_f(0);
//     else        state <= period_44_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_44_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_44_6_bits_val_f(3);
function automatic [5:0] period_44_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b010000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_44_6_bits_next_f(tmp);
		period_44_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_44_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[0];
        period_44_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         45
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_45_6_bits_val_f(0);
//     else        state <= period_45_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_45_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_45_6_bits_val_f(3);
function automatic [5:0] period_45_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_45_6_bits_next_f(tmp);
		period_45_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_45_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[0];
        period_45_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         46
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_46_6_bits_val_f(0);
//     else        state <= period_46_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_46_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_46_6_bits_val_f(3);
function automatic [5:0] period_46_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_46_6_bits_next_f(tmp);
		period_46_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_46_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[0];
        period_46_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         47
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_47_6_bits_val_f(0);
//     else        state <= period_47_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_47_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_47_6_bits_val_f(3);
function automatic [5:0] period_47_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_47_6_bits_next_f(tmp);
		period_47_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_47_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[0];
        period_47_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         48
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_48_6_bits_val_f(0);
//     else        state <= period_48_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_48_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_48_6_bits_val_f(3);
function automatic [5:0] period_48_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_48_6_bits_next_f(tmp);
		period_48_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_48_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[0];
        period_48_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         49
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_49_6_bits_val_f(0);
//     else        state <= period_49_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_49_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_49_6_bits_val_f(3);
function automatic [5:0] period_49_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_49_6_bits_next_f(tmp);
		period_49_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_49_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = (in[0]^in[0]);
        period_49_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         50
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_50_6_bits_val_f(0);
//     else        state <= period_50_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_50_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_50_6_bits_val_f(3);
function automatic [5:0] period_50_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_50_6_bits_next_f(tmp);
		period_50_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_50_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[0];
        period_50_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         51
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_51_6_bits_val_f(0);
//     else        state <= period_51_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_51_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_51_6_bits_val_f(3);
function automatic [5:0] period_51_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_51_6_bits_next_f(tmp);
		period_51_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_51_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = (in[0]^in[0]);
        period_51_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         52
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_52_6_bits_val_f(0);
//     else        state <= period_52_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_52_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_52_6_bits_val_f(3);
function automatic [5:0] period_52_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_52_6_bits_next_f(tmp);
		period_52_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_52_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[0];
        period_52_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         53
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_53_6_bits_val_f(0);
//     else        state <= period_53_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_53_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_53_6_bits_val_f(3);
function automatic [5:0] period_53_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_53_6_bits_next_f(tmp);
		period_53_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_53_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[0];
        period_53_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         54
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_54_6_bits_val_f(0);
//     else        state <= period_54_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_54_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_54_6_bits_val_f(3);
function automatic [5:0] period_54_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b110000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_54_6_bits_next_f(tmp);
		period_54_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_54_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[0];
        period_54_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         55
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_55_6_bits_val_f(0);
//     else        state <= period_55_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_55_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_55_6_bits_val_f(3);
function automatic [5:0] period_55_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_55_6_bits_next_f(tmp);
		period_55_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_55_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[0];
        period_55_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         56
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_56_6_bits_val_f(0);
//     else        state <= period_56_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_56_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_56_6_bits_val_f(3);
function automatic [5:0] period_56_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_56_6_bits_next_f(tmp);
		period_56_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_56_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[0];
        period_56_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         57
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_57_6_bits_val_f(0);
//     else        state <= period_57_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_57_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_57_6_bits_val_f(3);
function automatic [5:0] period_57_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_57_6_bits_next_f(tmp);
		period_57_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_57_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[0]^in[0]);
        period_57_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         58
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_58_6_bits_val_f(0);
//     else        state <= period_58_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_58_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_58_6_bits_val_f(3);
function automatic [5:0] period_58_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_58_6_bits_next_f(tmp);
		period_58_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_58_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[0];
        period_58_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         59
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_59_6_bits_val_f(0);
//     else        state <= period_59_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_59_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_59_6_bits_val_f(3);
function automatic [5:0] period_59_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_59_6_bits_next_f(tmp);
		period_59_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_59_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[0];
        period_59_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         60
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_60_6_bits_val_f(0);
//     else        state <= period_60_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_60_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_60_6_bits_val_f(3);
function automatic [5:0] period_60_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_60_6_bits_next_f(tmp);
		period_60_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_60_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[0];
        period_60_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         61
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_61_6_bits_val_f(0);
//     else        state <= period_61_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_61_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_61_6_bits_val_f(3);
function automatic [5:0] period_61_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_61_6_bits_next_f(tmp);
		period_61_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_61_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[0];
        period_61_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         62
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_62_6_bits_val_f(0);
//     else        state <= period_62_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_62_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_62_6_bits_val_f(3);
function automatic [5:0] period_62_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_62_6_bits_next_f(tmp);
		period_62_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_62_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[0];
        period_62_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         63
// Number of bits: 6
// 
// Typical usage:
// reg [6-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_63_6_bits_val_f(0);
//     else        state <= period_63_6_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_63_6_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_63_6_bits_val_f(3);
function automatic [5:0] period_63_6_bits_val_f;
	input integer target_step;
	reg [5:0] tmp;
	integer i;
	begin
		tmp = 6'b100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_63_6_bits_next_f(tmp);
		period_63_6_bits_val_f = tmp;
	end
endfunction
function automatic [5:0] period_63_6_bits_next_f;
    input [5:0] in;
    reg [5:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[0];
        period_63_6_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         64
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_64_7_bits_val_f(0);
//     else        state <= period_64_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_64_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_64_7_bits_val_f(3);
function automatic [6:0] period_64_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_64_7_bits_next_f(tmp);
		period_64_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_64_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_64_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         65
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_65_7_bits_val_f(0);
//     else        state <= period_65_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_65_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_65_7_bits_val_f(3);
function automatic [6:0] period_65_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_65_7_bits_next_f(tmp);
		period_65_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_65_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_65_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         66
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_66_7_bits_val_f(0);
//     else        state <= period_66_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_66_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_66_7_bits_val_f(3);
function automatic [6:0] period_66_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_66_7_bits_next_f(tmp);
		period_66_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_66_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = (in[0]^in[0]);
        period_66_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         67
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_67_7_bits_val_f(0);
//     else        state <= period_67_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_67_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_67_7_bits_val_f(3);
function automatic [6:0] period_67_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_67_7_bits_next_f(tmp);
		period_67_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_67_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_67_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         68
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_68_7_bits_val_f(0);
//     else        state <= period_68_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_68_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_68_7_bits_val_f(3);
function automatic [6:0] period_68_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_68_7_bits_next_f(tmp);
		period_68_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_68_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_68_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         69
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_69_7_bits_val_f(0);
//     else        state <= period_69_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_69_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_69_7_bits_val_f(3);
function automatic [6:0] period_69_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_69_7_bits_next_f(tmp);
		period_69_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_69_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[0];
        period_69_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         70
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_70_7_bits_val_f(0);
//     else        state <= period_70_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_70_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_70_7_bits_val_f(3);
function automatic [6:0] period_70_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_70_7_bits_next_f(tmp);
		period_70_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_70_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[0];
        period_70_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         71
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_71_7_bits_val_f(0);
//     else        state <= period_71_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_71_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_71_7_bits_val_f(3);
function automatic [6:0] period_71_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_71_7_bits_next_f(tmp);
		period_71_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_71_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_71_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         72
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_72_7_bits_val_f(0);
//     else        state <= period_72_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_72_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_72_7_bits_val_f(3);
function automatic [6:0] period_72_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_72_7_bits_next_f(tmp);
		period_72_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_72_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_72_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         73
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_73_7_bits_val_f(0);
//     else        state <= period_73_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_73_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_73_7_bits_val_f(3);
function automatic [6:0] period_73_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_73_7_bits_next_f(tmp);
		period_73_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_73_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]&in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[0];
        period_73_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         74
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_74_7_bits_val_f(0);
//     else        state <= period_74_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_74_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_74_7_bits_val_f(3);
function automatic [6:0] period_74_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1010000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_74_7_bits_next_f(tmp);
		period_74_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_74_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_74_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         75
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_75_7_bits_val_f(0);
//     else        state <= period_75_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_75_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_75_7_bits_val_f(3);
function automatic [6:0] period_75_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_75_7_bits_next_f(tmp);
		period_75_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_75_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_75_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         76
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_76_7_bits_val_f(0);
//     else        state <= period_76_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_76_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_76_7_bits_val_f(3);
function automatic [6:0] period_76_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_76_7_bits_next_f(tmp);
		period_76_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_76_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[0];
        period_76_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         77
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_77_7_bits_val_f(0);
//     else        state <= period_77_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_77_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_77_7_bits_val_f(3);
function automatic [6:0] period_77_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_77_7_bits_next_f(tmp);
		period_77_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_77_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[0]^(in[1]|in[2]));
        period_77_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         78
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_78_7_bits_val_f(0);
//     else        state <= period_78_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_78_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_78_7_bits_val_f(3);
function automatic [6:0] period_78_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_78_7_bits_next_f(tmp);
		period_78_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_78_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_78_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         79
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_79_7_bits_val_f(0);
//     else        state <= period_79_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_79_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_79_7_bits_val_f(3);
function automatic [6:0] period_79_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_79_7_bits_next_f(tmp);
		period_79_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_79_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^~in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_79_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         80
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_80_7_bits_val_f(0);
//     else        state <= period_80_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_80_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_80_7_bits_val_f(3);
function automatic [6:0] period_80_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_80_7_bits_next_f(tmp);
		period_80_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_80_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[0];
        period_80_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         81
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_81_7_bits_val_f(0);
//     else        state <= period_81_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_81_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_81_7_bits_val_f(3);
function automatic [6:0] period_81_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_81_7_bits_next_f(tmp);
		period_81_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_81_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_81_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         82
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_82_7_bits_val_f(0);
//     else        state <= period_82_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_82_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_82_7_bits_val_f(3);
function automatic [6:0] period_82_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_82_7_bits_next_f(tmp);
		period_82_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_82_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_82_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         83
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_83_7_bits_val_f(0);
//     else        state <= period_83_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_83_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_83_7_bits_val_f(3);
function automatic [6:0] period_83_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_83_7_bits_next_f(tmp);
		period_83_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_83_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_83_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         84
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_84_7_bits_val_f(0);
//     else        state <= period_84_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_84_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_84_7_bits_val_f(3);
function automatic [6:0] period_84_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_84_7_bits_next_f(tmp);
		period_84_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_84_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_84_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         85
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_85_7_bits_val_f(0);
//     else        state <= period_85_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_85_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_85_7_bits_val_f(3);
function automatic [6:0] period_85_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_85_7_bits_next_f(tmp);
		period_85_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_85_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[0]^(in[1]|in[2]));
        period_85_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         86
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_86_7_bits_val_f(0);
//     else        state <= period_86_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_86_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_86_7_bits_val_f(3);
function automatic [6:0] period_86_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_86_7_bits_next_f(tmp);
		period_86_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_86_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_86_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         87
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_87_7_bits_val_f(0);
//     else        state <= period_87_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_87_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_87_7_bits_val_f(3);
function automatic [6:0] period_87_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_87_7_bits_next_f(tmp);
		period_87_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_87_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_87_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         88
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_88_7_bits_val_f(0);
//     else        state <= period_88_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_88_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_88_7_bits_val_f(3);
function automatic [6:0] period_88_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_88_7_bits_next_f(tmp);
		period_88_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_88_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[0];
        period_88_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         89
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_89_7_bits_val_f(0);
//     else        state <= period_89_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_89_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_89_7_bits_val_f(3);
function automatic [6:0] period_89_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_89_7_bits_next_f(tmp);
		period_89_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_89_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_89_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         90
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_90_7_bits_val_f(0);
//     else        state <= period_90_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_90_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_90_7_bits_val_f(3);
function automatic [6:0] period_90_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_90_7_bits_next_f(tmp);
		period_90_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_90_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_90_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         91
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_91_7_bits_val_f(0);
//     else        state <= period_91_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_91_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_91_7_bits_val_f(3);
function automatic [6:0] period_91_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_91_7_bits_next_f(tmp);
		period_91_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_91_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_91_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         92
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_92_7_bits_val_f(0);
//     else        state <= period_92_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_92_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_92_7_bits_val_f(3);
function automatic [6:0] period_92_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_92_7_bits_next_f(tmp);
		period_92_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_92_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^~in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_92_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         93
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_93_7_bits_val_f(0);
//     else        state <= period_93_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_93_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_93_7_bits_val_f(3);
function automatic [6:0] period_93_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_93_7_bits_next_f(tmp);
		period_93_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_93_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_93_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         94
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_94_7_bits_val_f(0);
//     else        state <= period_94_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_94_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_94_7_bits_val_f(3);
function automatic [6:0] period_94_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_94_7_bits_next_f(tmp);
		period_94_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_94_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[0]^(in[1]|in[2]));
        period_94_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         95
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_95_7_bits_val_f(0);
//     else        state <= period_95_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_95_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_95_7_bits_val_f(3);
function automatic [6:0] period_95_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_95_7_bits_next_f(tmp);
		period_95_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_95_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_95_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         96
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_96_7_bits_val_f(0);
//     else        state <= period_96_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_96_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_96_7_bits_val_f(3);
function automatic [6:0] period_96_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_96_7_bits_next_f(tmp);
		period_96_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_96_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_96_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         97
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_97_7_bits_val_f(0);
//     else        state <= period_97_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_97_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_97_7_bits_val_f(3);
function automatic [6:0] period_97_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_97_7_bits_next_f(tmp);
		period_97_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_97_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[0];
        period_97_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         98
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_98_7_bits_val_f(0);
//     else        state <= period_98_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_98_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_98_7_bits_val_f(3);
function automatic [6:0] period_98_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_98_7_bits_next_f(tmp);
		period_98_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_98_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_98_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         99
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_99_7_bits_val_f(0);
//     else        state <= period_99_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_99_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_99_7_bits_val_f(3);
function automatic [6:0] period_99_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_99_7_bits_next_f(tmp);
		period_99_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_99_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_99_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         100
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_100_7_bits_val_f(0);
//     else        state <= period_100_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_100_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_100_7_bits_val_f(3);
function automatic [6:0] period_100_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_100_7_bits_next_f(tmp);
		period_100_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_100_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_100_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         101
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_101_7_bits_val_f(0);
//     else        state <= period_101_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_101_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_101_7_bits_val_f(3);
function automatic [6:0] period_101_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_101_7_bits_next_f(tmp);
		period_101_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_101_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[0];
        period_101_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         102
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_102_7_bits_val_f(0);
//     else        state <= period_102_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_102_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_102_7_bits_val_f(3);
function automatic [6:0] period_102_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_102_7_bits_next_f(tmp);
		period_102_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_102_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_102_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         103
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_103_7_bits_val_f(0);
//     else        state <= period_103_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_103_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_103_7_bits_val_f(3);
function automatic [6:0] period_103_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_103_7_bits_next_f(tmp);
		period_103_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_103_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = (in[0]^in[0]);
        period_103_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         104
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_104_7_bits_val_f(0);
//     else        state <= period_104_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_104_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_104_7_bits_val_f(3);
function automatic [6:0] period_104_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_104_7_bits_next_f(tmp);
		period_104_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_104_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_104_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         105
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_105_7_bits_val_f(0);
//     else        state <= period_105_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_105_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_105_7_bits_val_f(3);
function automatic [6:0] period_105_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_105_7_bits_next_f(tmp);
		period_105_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_105_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[0];
        period_105_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         106
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_106_7_bits_val_f(0);
//     else        state <= period_106_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_106_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_106_7_bits_val_f(3);
function automatic [6:0] period_106_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_106_7_bits_next_f(tmp);
		period_106_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_106_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_106_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         107
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_107_7_bits_val_f(0);
//     else        state <= period_107_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_107_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_107_7_bits_val_f(3);
function automatic [6:0] period_107_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_107_7_bits_next_f(tmp);
		period_107_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_107_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^~in[0]);
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_107_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         108
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_108_7_bits_val_f(0);
//     else        state <= period_108_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_108_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_108_7_bits_val_f(3);
function automatic [6:0] period_108_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_108_7_bits_next_f(tmp);
		period_108_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_108_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[0]^(in[1]|in[0]));
        period_108_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         109
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_109_7_bits_val_f(0);
//     else        state <= period_109_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_109_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_109_7_bits_val_f(3);
function automatic [6:0] period_109_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_109_7_bits_next_f(tmp);
		period_109_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_109_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_109_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         110
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_110_7_bits_val_f(0);
//     else        state <= period_110_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_110_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_110_7_bits_val_f(3);
function automatic [6:0] period_110_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_110_7_bits_next_f(tmp);
		period_110_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_110_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_110_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         111
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_111_7_bits_val_f(0);
//     else        state <= period_111_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_111_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_111_7_bits_val_f(3);
function automatic [6:0] period_111_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_111_7_bits_next_f(tmp);
		period_111_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_111_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = ~in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_111_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         112
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_112_7_bits_val_f(0);
//     else        state <= period_112_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_112_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_112_7_bits_val_f(3);
function automatic [6:0] period_112_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_112_7_bits_next_f(tmp);
		period_112_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_112_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_112_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         113
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_113_7_bits_val_f(0);
//     else        state <= period_113_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_113_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_113_7_bits_val_f(3);
function automatic [6:0] period_113_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_113_7_bits_next_f(tmp);
		period_113_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_113_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[0];
        period_113_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         114
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_114_7_bits_val_f(0);
//     else        state <= period_114_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_114_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_114_7_bits_val_f(3);
function automatic [6:0] period_114_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_114_7_bits_next_f(tmp);
		period_114_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_114_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_114_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         115
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_115_7_bits_val_f(0);
//     else        state <= period_115_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_115_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_115_7_bits_val_f(3);
function automatic [6:0] period_115_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_115_7_bits_next_f(tmp);
		period_115_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_115_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_115_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         116
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_116_7_bits_val_f(0);
//     else        state <= period_116_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_116_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_116_7_bits_val_f(3);
function automatic [6:0] period_116_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_116_7_bits_next_f(tmp);
		period_116_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_116_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[0];
        period_116_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         117
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_117_7_bits_val_f(0);
//     else        state <= period_117_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_117_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_117_7_bits_val_f(3);
function automatic [6:0] period_117_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_117_7_bits_next_f(tmp);
		period_117_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_117_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = (in[0]^in[0]);
        period_117_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         118
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_118_7_bits_val_f(0);
//     else        state <= period_118_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_118_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_118_7_bits_val_f(3);
function automatic [6:0] period_118_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_118_7_bits_next_f(tmp);
		period_118_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_118_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[0];
        period_118_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         119
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_119_7_bits_val_f(0);
//     else        state <= period_119_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_119_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_119_7_bits_val_f(3);
function automatic [6:0] period_119_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_119_7_bits_next_f(tmp);
		period_119_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_119_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_119_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         120
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_120_7_bits_val_f(0);
//     else        state <= period_120_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_120_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_120_7_bits_val_f(3);
function automatic [6:0] period_120_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_120_7_bits_next_f(tmp);
		period_120_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_120_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[0];
        period_120_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         121
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_121_7_bits_val_f(0);
//     else        state <= period_121_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_121_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_121_7_bits_val_f(3);
function automatic [6:0] period_121_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_121_7_bits_next_f(tmp);
		period_121_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_121_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_121_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         122
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_122_7_bits_val_f(0);
//     else        state <= period_122_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_122_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_122_7_bits_val_f(3);
function automatic [6:0] period_122_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_122_7_bits_next_f(tmp);
		period_122_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_122_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[0];
        period_122_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         123
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_123_7_bits_val_f(0);
//     else        state <= period_123_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_123_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_123_7_bits_val_f(3);
function automatic [6:0] period_123_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_123_7_bits_next_f(tmp);
		period_123_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_123_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[0];
        period_123_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         124
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_124_7_bits_val_f(0);
//     else        state <= period_124_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_124_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_124_7_bits_val_f(3);
function automatic [6:0] period_124_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_124_7_bits_next_f(tmp);
		period_124_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_124_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_124_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         125
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_125_7_bits_val_f(0);
//     else        state <= period_125_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_125_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_125_7_bits_val_f(3);
function automatic [6:0] period_125_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_125_7_bits_next_f(tmp);
		period_125_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_125_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_125_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         126
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_126_7_bits_val_f(0);
//     else        state <= period_126_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_126_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_126_7_bits_val_f(3);
function automatic [6:0] period_126_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b0000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_126_7_bits_next_f(tmp);
		period_126_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_126_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[0];
        period_126_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         127
// Number of bits: 7
// 
// Typical usage:
// reg [7-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_127_7_bits_val_f(0);
//     else        state <= period_127_7_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_127_7_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_127_7_bits_val_f(3);
function automatic [6:0] period_127_7_bits_val_f;
	input integer target_step;
	reg [6:0] tmp;
	integer i;
	begin
		tmp = 7'b1000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_127_7_bits_next_f(tmp);
		period_127_7_bits_val_f = tmp;
	end
endfunction
function automatic [6:0] period_127_7_bits_next_f;
    input [6:0] in;
    reg [6:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[0];
        period_127_7_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         128
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_128_8_bits_val_f(0);
//     else        state <= period_128_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_128_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_128_8_bits_val_f(3);
function automatic [7:0] period_128_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_128_8_bits_next_f(tmp);
		period_128_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_128_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_128_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         129
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_129_8_bits_val_f(0);
//     else        state <= period_129_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_129_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_129_8_bits_val_f(3);
function automatic [7:0] period_129_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_129_8_bits_next_f(tmp);
		period_129_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_129_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_129_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         130
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_130_8_bits_val_f(0);
//     else        state <= period_130_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_130_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_130_8_bits_val_f(3);
function automatic [7:0] period_130_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_130_8_bits_next_f(tmp);
		period_130_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_130_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_130_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         131
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_131_8_bits_val_f(0);
//     else        state <= period_131_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_131_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_131_8_bits_val_f(3);
function automatic [7:0] period_131_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_131_8_bits_next_f(tmp);
		period_131_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_131_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_131_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         132
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_132_8_bits_val_f(0);
//     else        state <= period_132_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_132_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_132_8_bits_val_f(3);
function automatic [7:0] period_132_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_132_8_bits_next_f(tmp);
		period_132_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_132_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_132_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         133
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_133_8_bits_val_f(0);
//     else        state <= period_133_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_133_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_133_8_bits_val_f(3);
function automatic [7:0] period_133_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_133_8_bits_next_f(tmp);
		period_133_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_133_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_133_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         134
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_134_8_bits_val_f(0);
//     else        state <= period_134_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_134_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_134_8_bits_val_f(3);
function automatic [7:0] period_134_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b01000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_134_8_bits_next_f(tmp);
		period_134_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_134_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_134_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         135
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_135_8_bits_val_f(0);
//     else        state <= period_135_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_135_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_135_8_bits_val_f(3);
function automatic [7:0] period_135_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_135_8_bits_next_f(tmp);
		period_135_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_135_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = in[0];
        period_135_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         136
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_136_8_bits_val_f(0);
//     else        state <= period_136_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_136_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_136_8_bits_val_f(3);
function automatic [7:0] period_136_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b01000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_136_8_bits_next_f(tmp);
		period_136_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_136_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_136_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         137
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_137_8_bits_val_f(0);
//     else        state <= period_137_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_137_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_137_8_bits_val_f(3);
function automatic [7:0] period_137_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_137_8_bits_next_f(tmp);
		period_137_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_137_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[0]));
        period_137_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         138
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_138_8_bits_val_f(0);
//     else        state <= period_138_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_138_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_138_8_bits_val_f(3);
function automatic [7:0] period_138_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b01000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_138_8_bits_next_f(tmp);
		period_138_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_138_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_138_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         139
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_139_8_bits_val_f(0);
//     else        state <= period_139_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_139_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_139_8_bits_val_f(3);
function automatic [7:0] period_139_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_139_8_bits_next_f(tmp);
		period_139_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_139_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_139_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         140
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_140_8_bits_val_f(0);
//     else        state <= period_140_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_140_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_140_8_bits_val_f(3);
function automatic [7:0] period_140_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_140_8_bits_next_f(tmp);
		period_140_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_140_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_140_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         141
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_141_8_bits_val_f(0);
//     else        state <= period_141_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_141_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_141_8_bits_val_f(3);
function automatic [7:0] period_141_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_141_8_bits_next_f(tmp);
		period_141_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_141_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[0]));
        period_141_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         142
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_142_8_bits_val_f(0);
//     else        state <= period_142_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_142_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_142_8_bits_val_f(3);
function automatic [7:0] period_142_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_142_8_bits_next_f(tmp);
		period_142_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_142_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_142_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         143
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_143_8_bits_val_f(0);
//     else        state <= period_143_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_143_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_143_8_bits_val_f(3);
function automatic [7:0] period_143_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_143_8_bits_next_f(tmp);
		period_143_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_143_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[2]));
        period_143_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         144
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_144_8_bits_val_f(0);
//     else        state <= period_144_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_144_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_144_8_bits_val_f(3);
function automatic [7:0] period_144_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_144_8_bits_next_f(tmp);
		period_144_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_144_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_144_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         145
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_145_8_bits_val_f(0);
//     else        state <= period_145_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_145_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_145_8_bits_val_f(3);
function automatic [7:0] period_145_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_145_8_bits_next_f(tmp);
		period_145_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_145_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_145_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         146
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_146_8_bits_val_f(0);
//     else        state <= period_146_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_146_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_146_8_bits_val_f(3);
function automatic [7:0] period_146_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_146_8_bits_next_f(tmp);
		period_146_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_146_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_146_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         147
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_147_8_bits_val_f(0);
//     else        state <= period_147_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_147_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_147_8_bits_val_f(3);
function automatic [7:0] period_147_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_147_8_bits_next_f(tmp);
		period_147_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_147_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_147_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         148
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_148_8_bits_val_f(0);
//     else        state <= period_148_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_148_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_148_8_bits_val_f(3);
function automatic [7:0] period_148_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_148_8_bits_next_f(tmp);
		period_148_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_148_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_148_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         149
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_149_8_bits_val_f(0);
//     else        state <= period_149_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_149_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_149_8_bits_val_f(3);
function automatic [7:0] period_149_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_149_8_bits_next_f(tmp);
		period_149_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_149_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[0]));
        period_149_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         150
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_150_8_bits_val_f(0);
//     else        state <= period_150_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_150_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_150_8_bits_val_f(3);
function automatic [7:0] period_150_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_150_8_bits_next_f(tmp);
		period_150_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_150_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_150_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         151
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_151_8_bits_val_f(0);
//     else        state <= period_151_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_151_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_151_8_bits_val_f(3);
function automatic [7:0] period_151_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_151_8_bits_next_f(tmp);
		period_151_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_151_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_151_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         152
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_152_8_bits_val_f(0);
//     else        state <= period_152_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_152_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_152_8_bits_val_f(3);
function automatic [7:0] period_152_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11100000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_152_8_bits_next_f(tmp);
		period_152_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_152_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_152_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         153
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_153_8_bits_val_f(0);
//     else        state <= period_153_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_153_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_153_8_bits_val_f(3);
function automatic [7:0] period_153_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_153_8_bits_next_f(tmp);
		period_153_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_153_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_153_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         154
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_154_8_bits_val_f(0);
//     else        state <= period_154_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_154_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_154_8_bits_val_f(3);
function automatic [7:0] period_154_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b01000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_154_8_bits_next_f(tmp);
		period_154_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_154_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_154_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         155
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_155_8_bits_val_f(0);
//     else        state <= period_155_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_155_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_155_8_bits_val_f(3);
function automatic [7:0] period_155_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_155_8_bits_next_f(tmp);
		period_155_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_155_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = in[0];
        period_155_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         156
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_156_8_bits_val_f(0);
//     else        state <= period_156_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_156_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_156_8_bits_val_f(3);
function automatic [7:0] period_156_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_156_8_bits_next_f(tmp);
		period_156_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_156_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_156_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         157
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_157_8_bits_val_f(0);
//     else        state <= period_157_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_157_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_157_8_bits_val_f(3);
function automatic [7:0] period_157_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_157_8_bits_next_f(tmp);
		period_157_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_157_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_157_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         158
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_158_8_bits_val_f(0);
//     else        state <= period_158_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_158_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_158_8_bits_val_f(3);
function automatic [7:0] period_158_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_158_8_bits_next_f(tmp);
		period_158_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_158_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_158_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         159
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_159_8_bits_val_f(0);
//     else        state <= period_159_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_159_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_159_8_bits_val_f(3);
function automatic [7:0] period_159_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_159_8_bits_next_f(tmp);
		period_159_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_159_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = in[0];
        period_159_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         160
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_160_8_bits_val_f(0);
//     else        state <= period_160_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_160_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_160_8_bits_val_f(3);
function automatic [7:0] period_160_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_160_8_bits_next_f(tmp);
		period_160_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_160_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_160_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         161
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_161_8_bits_val_f(0);
//     else        state <= period_161_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_161_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_161_8_bits_val_f(3);
function automatic [7:0] period_161_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_161_8_bits_next_f(tmp);
		period_161_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_161_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_161_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         162
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_162_8_bits_val_f(0);
//     else        state <= period_162_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_162_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_162_8_bits_val_f(3);
function automatic [7:0] period_162_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_162_8_bits_next_f(tmp);
		period_162_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_162_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_162_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         163
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_163_8_bits_val_f(0);
//     else        state <= period_163_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_163_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_163_8_bits_val_f(3);
function automatic [7:0] period_163_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_163_8_bits_next_f(tmp);
		period_163_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_163_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = (in[0]^in[0]);
        period_163_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         164
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_164_8_bits_val_f(0);
//     else        state <= period_164_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_164_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_164_8_bits_val_f(3);
function automatic [7:0] period_164_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_164_8_bits_next_f(tmp);
		period_164_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_164_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_164_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         165
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_165_8_bits_val_f(0);
//     else        state <= period_165_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_165_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_165_8_bits_val_f(3);
function automatic [7:0] period_165_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_165_8_bits_next_f(tmp);
		period_165_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_165_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^(in[1]|in[0]));
		out[7] = in[0];
        period_165_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         166
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_166_8_bits_val_f(0);
//     else        state <= period_166_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_166_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_166_8_bits_val_f(3);
function automatic [7:0] period_166_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_166_8_bits_next_f(tmp);
		period_166_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_166_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_166_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         167
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_167_8_bits_val_f(0);
//     else        state <= period_167_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_167_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_167_8_bits_val_f(3);
function automatic [7:0] period_167_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b01000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_167_8_bits_next_f(tmp);
		period_167_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_167_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_167_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         168
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_168_8_bits_val_f(0);
//     else        state <= period_168_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_168_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_168_8_bits_val_f(3);
function automatic [7:0] period_168_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_168_8_bits_next_f(tmp);
		period_168_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_168_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_168_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         169
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_169_8_bits_val_f(0);
//     else        state <= period_169_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_169_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_169_8_bits_val_f(3);
function automatic [7:0] period_169_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_169_8_bits_next_f(tmp);
		period_169_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_169_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_169_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         170
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_170_8_bits_val_f(0);
//     else        state <= period_170_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_170_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_170_8_bits_val_f(3);
function automatic [7:0] period_170_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_170_8_bits_next_f(tmp);
		period_170_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_170_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_170_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         171
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_171_8_bits_val_f(0);
//     else        state <= period_171_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_171_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_171_8_bits_val_f(3);
function automatic [7:0] period_171_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_171_8_bits_next_f(tmp);
		period_171_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_171_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_171_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         172
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_172_8_bits_val_f(0);
//     else        state <= period_172_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_172_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_172_8_bits_val_f(3);
function automatic [7:0] period_172_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_172_8_bits_next_f(tmp);
		period_172_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_172_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_172_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         173
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_173_8_bits_val_f(0);
//     else        state <= period_173_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_173_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_173_8_bits_val_f(3);
function automatic [7:0] period_173_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_173_8_bits_next_f(tmp);
		period_173_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_173_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_173_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         174
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_174_8_bits_val_f(0);
//     else        state <= period_174_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_174_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_174_8_bits_val_f(3);
function automatic [7:0] period_174_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_174_8_bits_next_f(tmp);
		period_174_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_174_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_174_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         175
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_175_8_bits_val_f(0);
//     else        state <= period_175_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_175_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_175_8_bits_val_f(3);
function automatic [7:0] period_175_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_175_8_bits_next_f(tmp);
		period_175_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_175_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_175_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         176
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_176_8_bits_val_f(0);
//     else        state <= period_176_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_176_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_176_8_bits_val_f(3);
function automatic [7:0] period_176_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_176_8_bits_next_f(tmp);
		period_176_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_176_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_176_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         177
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_177_8_bits_val_f(0);
//     else        state <= period_177_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_177_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_177_8_bits_val_f(3);
function automatic [7:0] period_177_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_177_8_bits_next_f(tmp);
		period_177_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_177_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = in[0];
        period_177_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         178
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_178_8_bits_val_f(0);
//     else        state <= period_178_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_178_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_178_8_bits_val_f(3);
function automatic [7:0] period_178_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_178_8_bits_next_f(tmp);
		period_178_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_178_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_178_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         179
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_179_8_bits_val_f(0);
//     else        state <= period_179_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_179_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_179_8_bits_val_f(3);
function automatic [7:0] period_179_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_179_8_bits_next_f(tmp);
		period_179_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_179_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_179_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         180
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_180_8_bits_val_f(0);
//     else        state <= period_180_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_180_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_180_8_bits_val_f(3);
function automatic [7:0] period_180_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_180_8_bits_next_f(tmp);
		period_180_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_180_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_180_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         181
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_181_8_bits_val_f(0);
//     else        state <= period_181_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_181_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_181_8_bits_val_f(3);
function automatic [7:0] period_181_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_181_8_bits_next_f(tmp);
		period_181_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_181_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = in[0];
        period_181_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         182
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_182_8_bits_val_f(0);
//     else        state <= period_182_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_182_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_182_8_bits_val_f(3);
function automatic [7:0] period_182_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_182_8_bits_next_f(tmp);
		period_182_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_182_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_182_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         183
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_183_8_bits_val_f(0);
//     else        state <= period_183_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_183_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_183_8_bits_val_f(3);
function automatic [7:0] period_183_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_183_8_bits_next_f(tmp);
		period_183_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_183_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^~in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_183_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         184
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_184_8_bits_val_f(0);
//     else        state <= period_184_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_184_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_184_8_bits_val_f(3);
function automatic [7:0] period_184_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_184_8_bits_next_f(tmp);
		period_184_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_184_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_184_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         185
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_185_8_bits_val_f(0);
//     else        state <= period_185_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_185_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_185_8_bits_val_f(3);
function automatic [7:0] period_185_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_185_8_bits_next_f(tmp);
		period_185_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_185_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_185_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         186
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_186_8_bits_val_f(0);
//     else        state <= period_186_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_186_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_186_8_bits_val_f(3);
function automatic [7:0] period_186_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_186_8_bits_next_f(tmp);
		period_186_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_186_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_186_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         187
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_187_8_bits_val_f(0);
//     else        state <= period_187_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_187_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_187_8_bits_val_f(3);
function automatic [7:0] period_187_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_187_8_bits_next_f(tmp);
		period_187_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_187_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_187_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         188
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_188_8_bits_val_f(0);
//     else        state <= period_188_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_188_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_188_8_bits_val_f(3);
function automatic [7:0] period_188_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_188_8_bits_next_f(tmp);
		period_188_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_188_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_188_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         189
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_189_8_bits_val_f(0);
//     else        state <= period_189_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_189_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_189_8_bits_val_f(3);
function automatic [7:0] period_189_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_189_8_bits_next_f(tmp);
		period_189_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_189_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_189_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         190
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_190_8_bits_val_f(0);
//     else        state <= period_190_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_190_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_190_8_bits_val_f(3);
function automatic [7:0] period_190_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_190_8_bits_next_f(tmp);
		period_190_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_190_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_190_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         191
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_191_8_bits_val_f(0);
//     else        state <= period_191_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_191_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_191_8_bits_val_f(3);
function automatic [7:0] period_191_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_191_8_bits_next_f(tmp);
		period_191_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_191_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_191_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         192
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_192_8_bits_val_f(0);
//     else        state <= period_192_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_192_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_192_8_bits_val_f(3);
function automatic [7:0] period_192_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_192_8_bits_next_f(tmp);
		period_192_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_192_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_192_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         193
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_193_8_bits_val_f(0);
//     else        state <= period_193_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_193_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_193_8_bits_val_f(3);
function automatic [7:0] period_193_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_193_8_bits_next_f(tmp);
		period_193_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_193_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_193_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         194
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_194_8_bits_val_f(0);
//     else        state <= period_194_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_194_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_194_8_bits_val_f(3);
function automatic [7:0] period_194_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_194_8_bits_next_f(tmp);
		period_194_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_194_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = ~in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_194_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         195
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_195_8_bits_val_f(0);
//     else        state <= period_195_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_195_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_195_8_bits_val_f(3);
function automatic [7:0] period_195_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_195_8_bits_next_f(tmp);
		period_195_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_195_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_195_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         196
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_196_8_bits_val_f(0);
//     else        state <= period_196_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_196_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_196_8_bits_val_f(3);
function automatic [7:0] period_196_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_196_8_bits_next_f(tmp);
		period_196_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_196_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_196_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         197
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_197_8_bits_val_f(0);
//     else        state <= period_197_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_197_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_197_8_bits_val_f(3);
function automatic [7:0] period_197_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_197_8_bits_next_f(tmp);
		period_197_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_197_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_197_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         198
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_198_8_bits_val_f(0);
//     else        state <= period_198_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_198_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_198_8_bits_val_f(3);
function automatic [7:0] period_198_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_198_8_bits_next_f(tmp);
		period_198_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_198_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_198_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         199
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_199_8_bits_val_f(0);
//     else        state <= period_199_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_199_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_199_8_bits_val_f(3);
function automatic [7:0] period_199_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_199_8_bits_next_f(tmp);
		period_199_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_199_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[2]));
        period_199_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         200
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_200_8_bits_val_f(0);
//     else        state <= period_200_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_200_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_200_8_bits_val_f(3);
function automatic [7:0] period_200_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_200_8_bits_next_f(tmp);
		period_200_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_200_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_200_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         201
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_201_8_bits_val_f(0);
//     else        state <= period_201_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_201_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_201_8_bits_val_f(3);
function automatic [7:0] period_201_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_201_8_bits_next_f(tmp);
		period_201_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_201_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_201_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         202
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_202_8_bits_val_f(0);
//     else        state <= period_202_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_202_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_202_8_bits_val_f(3);
function automatic [7:0] period_202_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_202_8_bits_next_f(tmp);
		period_202_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_202_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_202_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         203
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_203_8_bits_val_f(0);
//     else        state <= period_203_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_203_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_203_8_bits_val_f(3);
function automatic [7:0] period_203_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_203_8_bits_next_f(tmp);
		period_203_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_203_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_203_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         204
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_204_8_bits_val_f(0);
//     else        state <= period_204_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_204_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_204_8_bits_val_f(3);
function automatic [7:0] period_204_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_204_8_bits_next_f(tmp);
		period_204_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_204_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = (in[7]^(in[1]|in[2]));
		out[7] = (in[0]^in[0]);
        period_204_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         205
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_205_8_bits_val_f(0);
//     else        state <= period_205_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_205_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_205_8_bits_val_f(3);
function automatic [7:0] period_205_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_205_8_bits_next_f(tmp);
		period_205_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_205_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_205_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         206
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_206_8_bits_val_f(0);
//     else        state <= period_206_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_206_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_206_8_bits_val_f(3);
function automatic [7:0] period_206_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_206_8_bits_next_f(tmp);
		period_206_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_206_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_206_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         207
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_207_8_bits_val_f(0);
//     else        state <= period_207_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_207_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_207_8_bits_val_f(3);
function automatic [7:0] period_207_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_207_8_bits_next_f(tmp);
		period_207_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_207_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_207_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         208
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_208_8_bits_val_f(0);
//     else        state <= period_208_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_208_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_208_8_bits_val_f(3);
function automatic [7:0] period_208_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_208_8_bits_next_f(tmp);
		period_208_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_208_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_208_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         209
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_209_8_bits_val_f(0);
//     else        state <= period_209_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_209_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_209_8_bits_val_f(3);
function automatic [7:0] period_209_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_209_8_bits_next_f(tmp);
		period_209_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_209_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[0]));
        period_209_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         210
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_210_8_bits_val_f(0);
//     else        state <= period_210_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_210_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_210_8_bits_val_f(3);
function automatic [7:0] period_210_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_210_8_bits_next_f(tmp);
		period_210_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_210_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_210_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         211
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_211_8_bits_val_f(0);
//     else        state <= period_211_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_211_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_211_8_bits_val_f(3);
function automatic [7:0] period_211_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_211_8_bits_next_f(tmp);
		period_211_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_211_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = (in[7]^(in[1]|in[0]));
		out[7] = in[0];
        period_211_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         212
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_212_8_bits_val_f(0);
//     else        state <= period_212_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_212_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_212_8_bits_val_f(3);
function automatic [7:0] period_212_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_212_8_bits_next_f(tmp);
		period_212_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_212_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_212_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         213
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_213_8_bits_val_f(0);
//     else        state <= period_213_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_213_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_213_8_bits_val_f(3);
function automatic [7:0] period_213_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_213_8_bits_next_f(tmp);
		period_213_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_213_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = ~in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_213_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         214
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_214_8_bits_val_f(0);
//     else        state <= period_214_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_214_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_214_8_bits_val_f(3);
function automatic [7:0] period_214_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_214_8_bits_next_f(tmp);
		period_214_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_214_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_214_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         215
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_215_8_bits_val_f(0);
//     else        state <= period_215_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_215_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_215_8_bits_val_f(3);
function automatic [7:0] period_215_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_215_8_bits_next_f(tmp);
		period_215_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_215_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_215_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         216
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_216_8_bits_val_f(0);
//     else        state <= period_216_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_216_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_216_8_bits_val_f(3);
function automatic [7:0] period_216_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_216_8_bits_next_f(tmp);
		period_216_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_216_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = in[0];
        period_216_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         217
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_217_8_bits_val_f(0);
//     else        state <= period_217_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_217_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_217_8_bits_val_f(3);
function automatic [7:0] period_217_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_217_8_bits_next_f(tmp);
		period_217_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_217_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_217_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         218
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_218_8_bits_val_f(0);
//     else        state <= period_218_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_218_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_218_8_bits_val_f(3);
function automatic [7:0] period_218_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_218_8_bits_next_f(tmp);
		period_218_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_218_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = (in[7]^(in[1]|in[0]));
		out[7] = in[0];
        period_218_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         219
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_219_8_bits_val_f(0);
//     else        state <= period_219_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_219_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_219_8_bits_val_f(3);
function automatic [7:0] period_219_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_219_8_bits_next_f(tmp);
		period_219_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_219_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^~in[0]);
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_219_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         220
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_220_8_bits_val_f(0);
//     else        state <= period_220_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_220_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_220_8_bits_val_f(3);
function automatic [7:0] period_220_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_220_8_bits_next_f(tmp);
		period_220_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_220_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_220_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         221
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_221_8_bits_val_f(0);
//     else        state <= period_221_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_221_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_221_8_bits_val_f(3);
function automatic [7:0] period_221_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_221_8_bits_next_f(tmp);
		period_221_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_221_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_221_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         222
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_222_8_bits_val_f(0);
//     else        state <= period_222_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_222_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_222_8_bits_val_f(3);
function automatic [7:0] period_222_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_222_8_bits_next_f(tmp);
		period_222_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_222_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_222_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         223
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_223_8_bits_val_f(0);
//     else        state <= period_223_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_223_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_223_8_bits_val_f(3);
function automatic [7:0] period_223_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_223_8_bits_next_f(tmp);
		period_223_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_223_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_223_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         224
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_224_8_bits_val_f(0);
//     else        state <= period_224_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_224_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_224_8_bits_val_f(3);
function automatic [7:0] period_224_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_224_8_bits_next_f(tmp);
		period_224_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_224_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_224_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         225
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_225_8_bits_val_f(0);
//     else        state <= period_225_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_225_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_225_8_bits_val_f(3);
function automatic [7:0] period_225_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_225_8_bits_next_f(tmp);
		period_225_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_225_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = (in[7]^(in[1]|in[2]));
		out[7] = in[0];
        period_225_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         226
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_226_8_bits_val_f(0);
//     else        state <= period_226_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_226_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_226_8_bits_val_f(3);
function automatic [7:0] period_226_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_226_8_bits_next_f(tmp);
		period_226_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_226_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_226_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         227
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_227_8_bits_val_f(0);
//     else        state <= period_227_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_227_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_227_8_bits_val_f(3);
function automatic [7:0] period_227_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_227_8_bits_next_f(tmp);
		period_227_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_227_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_227_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         228
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_228_8_bits_val_f(0);
//     else        state <= period_228_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_228_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_228_8_bits_val_f(3);
function automatic [7:0] period_228_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_228_8_bits_next_f(tmp);
		period_228_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_228_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_228_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         229
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_229_8_bits_val_f(0);
//     else        state <= period_229_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_229_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_229_8_bits_val_f(3);
function automatic [7:0] period_229_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_229_8_bits_next_f(tmp);
		period_229_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_229_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_229_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         230
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_230_8_bits_val_f(0);
//     else        state <= period_230_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_230_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_230_8_bits_val_f(3);
function automatic [7:0] period_230_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_230_8_bits_next_f(tmp);
		period_230_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_230_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_230_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         231
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_231_8_bits_val_f(0);
//     else        state <= period_231_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_231_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_231_8_bits_val_f(3);
function automatic [7:0] period_231_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_231_8_bits_next_f(tmp);
		period_231_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_231_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_231_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         232
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_232_8_bits_val_f(0);
//     else        state <= period_232_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_232_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_232_8_bits_val_f(3);
function automatic [7:0] period_232_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_232_8_bits_next_f(tmp);
		period_232_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_232_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_232_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         233
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_233_8_bits_val_f(0);
//     else        state <= period_233_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_233_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_233_8_bits_val_f(3);
function automatic [7:0] period_233_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_233_8_bits_next_f(tmp);
		period_233_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_233_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^in[0]);
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_233_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         234
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_234_8_bits_val_f(0);
//     else        state <= period_234_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_234_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_234_8_bits_val_f(3);
function automatic [7:0] period_234_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b11000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_234_8_bits_next_f(tmp);
		period_234_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_234_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_234_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         235
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_235_8_bits_val_f(0);
//     else        state <= period_235_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_235_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_235_8_bits_val_f(3);
function automatic [7:0] period_235_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_235_8_bits_next_f(tmp);
		period_235_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_235_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[2]));
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[0]));
        period_235_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         236
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_236_8_bits_val_f(0);
//     else        state <= period_236_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_236_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_236_8_bits_val_f(3);
function automatic [7:0] period_236_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_236_8_bits_next_f(tmp);
		period_236_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_236_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_236_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         237
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_237_8_bits_val_f(0);
//     else        state <= period_237_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_237_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_237_8_bits_val_f(3);
function automatic [7:0] period_237_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_237_8_bits_next_f(tmp);
		period_237_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_237_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = ~in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_237_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         238
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_238_8_bits_val_f(0);
//     else        state <= period_238_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_238_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_238_8_bits_val_f(3);
function automatic [7:0] period_238_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_238_8_bits_next_f(tmp);
		period_238_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_238_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_238_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         239
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_239_8_bits_val_f(0);
//     else        state <= period_239_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_239_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_239_8_bits_val_f(3);
function automatic [7:0] period_239_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_239_8_bits_next_f(tmp);
		period_239_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_239_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = (in[4]^in[0]);
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_239_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         240
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_240_8_bits_val_f(0);
//     else        state <= period_240_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_240_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_240_8_bits_val_f(3);
function automatic [7:0] period_240_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_240_8_bits_next_f(tmp);
		period_240_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_240_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = ~in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = in[0];
        period_240_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         241
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_241_8_bits_val_f(0);
//     else        state <= period_241_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_241_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_241_8_bits_val_f(3);
function automatic [7:0] period_241_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_241_8_bits_next_f(tmp);
		period_241_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_241_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^(in[1]|in[2]));
		out[2] = (in[3]^~in[0]);
		out[3] = in[4];
		out[4] = (in[5]^in[0]);
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_241_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         242
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_242_8_bits_val_f(0);
//     else        state <= period_242_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_242_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_242_8_bits_val_f(3);
function automatic [7:0] period_242_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_242_8_bits_next_f(tmp);
		period_242_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_242_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = in[2];
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^in[0]);
		out[6] = (in[7]^(in[1]|in[2]));
		out[7] = in[0];
        period_242_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         243
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_243_8_bits_val_f(0);
//     else        state <= period_243_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_243_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_243_8_bits_val_f(3);
function automatic [7:0] period_243_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_243_8_bits_next_f(tmp);
		period_243_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_243_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = ~in[2];
		out[2] = (in[3]^~in[0]);
		out[3] = (in[4]^(in[1]|in[2]));
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^(in[1]|in[0]));
		out[7] = in[0];
        period_243_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         244
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_244_8_bits_val_f(0);
//     else        state <= period_244_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_244_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_244_8_bits_val_f(3);
function automatic [7:0] period_244_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_244_8_bits_next_f(tmp);
		period_244_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_244_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = (in[6]^in[0]);
		out[6] = in[7];
		out[7] = (in[0]^in[0]);
        period_244_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         245
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_245_8_bits_val_f(0);
//     else        state <= period_245_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_245_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_245_8_bits_val_f(3);
function automatic [7:0] period_245_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_245_8_bits_next_f(tmp);
		period_245_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_245_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_245_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         246
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_246_8_bits_val_f(0);
//     else        state <= period_246_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_246_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_246_8_bits_val_f(3);
function automatic [7:0] period_246_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_246_8_bits_next_f(tmp);
		period_246_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_246_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = (in[7]^(in[1]|in[2]));
		out[7] = in[0];
        period_246_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         247
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_247_8_bits_val_f(0);
//     else        state <= period_247_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_247_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_247_8_bits_val_f(3);
function automatic [7:0] period_247_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_247_8_bits_next_f(tmp);
		period_247_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_247_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = (in[4]^(in[1]|in[0]));
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_247_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         248
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_248_8_bits_val_f(0);
//     else        state <= period_248_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_248_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_248_8_bits_val_f(3);
function automatic [7:0] period_248_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_248_8_bits_next_f(tmp);
		period_248_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_248_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[2]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_248_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         249
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_249_8_bits_val_f(0);
//     else        state <= period_249_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_249_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_249_8_bits_val_f(3);
function automatic [7:0] period_249_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_249_8_bits_next_f(tmp);
		period_249_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_249_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^~in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = in[7];
		out[7] = in[0];
        period_249_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         250
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_250_8_bits_val_f(0);
//     else        state <= period_250_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_250_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_250_8_bits_val_f(3);
function automatic [7:0] period_250_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_250_8_bits_next_f(tmp);
		period_250_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_250_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = (in[7]^in[0]);
		out[7] = in[0];
        period_250_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         251
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_251_8_bits_val_f(0);
//     else        state <= period_251_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_251_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_251_8_bits_val_f(3);
function automatic [7:0] period_251_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_251_8_bits_next_f(tmp);
		period_251_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_251_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = (in[6]^(in[1]|in[0]));
		out[6] = (in[7]^(in[1]|in[2]));
		out[7] = in[0];
        period_251_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         252
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_252_8_bits_val_f(0);
//     else        state <= period_252_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_252_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_252_8_bits_val_f(3);
function automatic [7:0] period_252_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_252_8_bits_next_f(tmp);
		period_252_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_252_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = in[2];
		out[2] = (in[3]^(in[1]|in[0]));
		out[3] = in[4];
		out[4] = (in[5]^(in[1]|in[0]));
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_252_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         253
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_253_8_bits_val_f(0);
//     else        state <= period_253_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_253_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_253_8_bits_val_f(3);
function automatic [7:0] period_253_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_253_8_bits_next_f(tmp);
		period_253_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_253_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = in[3];
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = (in[0]^(in[1]|in[0]));
        period_253_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         254
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_254_8_bits_val_f(0);
//     else        state <= period_254_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_254_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_254_8_bits_val_f(3);
function automatic [7:0] period_254_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b00000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_254_8_bits_next_f(tmp);
		period_254_8_bits_val_f = tmp;
	end
endfunction
function automatic [7:0] period_254_8_bits_next_f;
    input [7:0] in;
    reg [7:0] out;
    begin
		out[0] = ~in[1];
		out[1] = (in[2]^in[0]);
		out[2] = (in[3]^in[0]);
		out[3] = in[4];
		out[4] = in[5];
		out[5] = in[6];
		out[6] = in[7];
		out[7] = in[0];
        period_254_8_bits_next_f = out;
    end
endfunction
// Functions for low power/high frequency periodic signal
// Period:         255
// Number of bits: 8
// 
// Typical usage:
// reg [8-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= period_255_8_bits_val_f(0);
//     else        state <= period_255_8_bits_next_f(state);
// end
//
// To generate a trigger at a particular cycle, use period_255_8_bits_val_f
// For example at the 3rd cycle:
// wire third_cycle = state == period_255_8_bits_val_f(3);
function automatic [7:0] period_255_8_bits_val_f;
	input integer target_step;
	reg [7:0] tmp;
	integer i;
	begin
		tmp = 8'b10000000;
		for(i=0;i<target_count;i=i+1'b1) tmp = period_255_8_bits_next_f(tmp);
		period_255_8_bits_val_f = tmp;
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
