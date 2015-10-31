
module sha2_sec_ti2_rm0_plain_nand(
    input wire a,
    input wire b,
    output reg q
    );
wire tmp;
assign tmp = ~(a&b);
wire tmp2 = tmp;
reg tmp3;
always @*tmp3 = tmp2;
always @* q = tmp3;
endmodule


module sha2_sec_ti2_rm0_ti2_and_l0 #(
    parameter NOTA = 1'b0,
    parameter NOTB = 1'b0,
    parameter NOTY = 1'b0
    )(
    input wire [1:0] i_a,   //WARNING: must be uniform
    input wire [1:0] i_b,   //WARNING: must be uniform
    output reg [1:0] o_y    //WARNING: non uniform
    );
wire [1:0] a = i_a^ NOTA[0];
wire [1:0] b = i_b^ NOTB[0];
wire n00,n10,n01;
wire n11;

sha2_sec_ti2_rm0_plain_nand nand00_ITK(.a(a[0]), .b(b[0]), .q(n00));
sha2_sec_ti2_rm0_plain_nand nand10_ITK(.a(a[1]), .b(b[0]), .q(n10));
sha2_sec_ti2_rm0_plain_nand nand01_ITK(.a(a[0]), .b(b[1]), .q(n01));
sha2_sec_ti2_rm0_plain_nand nand11_ITK(.a(a[1]), .b(b[1]), .q(n11));

always @* begin
    o_y[0] = n00 ^ n11 ^ NOTY[0];
    o_y[1] = n10 ^ n01;
end
endmodule
/*

//` default_nettype none
module sha2_sec_ti2_rm0 (
    input wire i_reset,
    input wire i_clk,
    input wire i_sha512,
    input wire i_write,//1 cycle pulse each time i_dat is valid for message input.
    input wire i_write_state,//1 cycle pulse each time i_dat is valid for state input.
    input wire i_read,
    input wire i_init_mask,//pulse must last at least two cycles 
    input wire [64-1:0] i_rnd,
    input wire [64-1:0] i_dat_mdat,
    input wire [64-1:0] i_dat_mask,//input message and state are fed serially word by word.
    output reg [64-1:0] o_dat_mdat,
    output reg [64-1:0] o_dat_mask,
	output reg o_run,
    output reg o_valid
    );
//registers
reg [16*64-1:0] w;
reg [8*64-1:0] state;
reg [8*64-1:0] state_mask;
reg [8*64-1:0] initial_state;
reg [64-1:0] initial_state_mask_seed;
reg [64-1:0] initial_state_mask;
always @* o_dat_mdat = state[7*64+:64];    
always @* o_dat_mask = state_mask[7*64+:64];    

function [3:0] func_prince_sbox;
    input [3:0] in;
    begin
        case(in)
        4'h0: func_prince_sbox = 4'hB;
        4'h1: func_prince_sbox = 4'hF;
        4'h2: func_prince_sbox = 4'h3;
        4'h3: func_prince_sbox = 4'h2;
        4'h4: func_prince_sbox = 4'hA;
        4'h5: func_prince_sbox = 4'hC;
        4'h6: func_prince_sbox = 4'h9;
        4'h7: func_prince_sbox = 4'h1;
        4'h8: func_prince_sbox = 4'h6;
        4'h9: func_prince_sbox = 4'h7;
        4'hA: func_prince_sbox = 4'h8;
        4'hB: func_prince_sbox = 4'h0;
        4'hC: func_prince_sbox = 4'hE;
        4'hD: func_prince_sbox = 4'h5;
        4'hE: func_prince_sbox = 4'hD;
        4'hF: func_prince_sbox = 4'h4;    
        endcase
    end
endfunction

function [64-1:0] func_substitution;
    input [64-1:0] in;
    integer i;
    reg [64-1:0] out;
    begin
        for(i=0;i<64/4;i=i+1) begin
            out[i*4+:4] = func_prince_sbox(in[i*4+:4]);
        end
        func_substitution = out;
    end
endfunction

function [64-1:0] func_permutation;
    input [64-1:0] in;
    integer i;
    integer base4,rank,j;
    reg [64-1:0] out;
    begin
        for(i=0;i<64;i=i+1) begin
            base4 = 4*(i/4);
            rank = i-base4;
            case(rank)
            0: begin
                if(i==3*4) j = 1;
                else j = base4 + 29*4;
            end
            1: begin
                if(i==13*4+1) j = 2;
                else j = base4 + 3*4 + 1;//0,3,6,9,12,15,2,5,8,11,14,1,4,7,10,13
            end
            2: begin
                if(i==11*4+2) j = 3;
                else j = base4 + 5*4 + 2;//0,5,10,15,4,9,14,3,8,13,2,7,12,1,6,11
            end
            3: begin
                if(i==9*4+3) j = 0;
                else j = base4 + 7*4 + 3;//0,7,14,5,12,3,10,1,8,15,6,13,4,11,2,9
            end     
            endcase
            j = j % 64;
            out[j] = in[i];
        end
        func_permutation = out;
end
endfunction

//syndrom[4] and syndrom[5] manually modified to avoid duplicate
//extended_hamming_code_96_64_f
//Compute 32 bits Error Detection Code from a 64 bits input.
//The EDC is an extended hamming code capable of detecting any 1,2 and 3 bits errors in the input data or the EDC.
//There are 18446744073709551616 valid code words out of 79228162514264337593543950336 therefore 99% of errors are detected. 
//Dot graphic view: in[0]...in[63]
//  syndrom[ 0]: x                   xx                    xx                  x  (6 inputs)
//  syndrom[ 1]: x                   x x                  x  x                x   (6 inputs)
//  syndrom[ 2]: x                    xx                 x    x              x    (6 inputs)
//  syndrom[ 3]:  x                 x   x                x   x                 x  (6 inputs)
//  syndrom[ 4]:  x                 x   x                x   x                  X (6 inputs)
//  syndrom[ 5]:  x                  xx                    xx                  X  (6 inputs)
//  syndrom[ 6]:   x               x     x             x       x             x    (6 inputs)
//  syndrom[ 7]:   x               x     x              x     x               x   (6 inputs)
//  syndrom[ 8]:   x                x  x                  x x                   x (6 inputs)
//  syndrom[ 9]:    x             x       x            x       x             x    (6 inputs)
//  syndrom[10]:    x             x       x            x        x          x      (6 inputs)
//  syndrom[11]:    x              x    x                 xx                    x (6 inputs)
//  syndrom[12]:     x           x         x         x           x         x      (6 inputs)
//  syndrom[13]:     x           x         x          x         x           x     (6 inputs)
//  syndrom[14]:     x            x      x              x      x            x     (6 inputs)
//  syndrom[15]:      x         x           x        x           x         x      (6 inputs)
//  syndrom[16]:      x         x           x        x            x      x        (6 inputs)
//  syndrom[17]:      x          x        x             x     x               x   (6 inputs)
//  syndrom[18]:       x       x             x     x               x     x        (6 inputs)
//  syndrom[19]:       x       x             x      x             x       x       (6 inputs)
//  syndrom[20]:       x        x          x          x          x        x       (6 inputs)
//  syndrom[21]:        x     x               x    x               x     x        (6 inputs)
//  syndrom[22]:        x     x               x    x                x  x          (6 inputs)
//  syndrom[23]:        x      x            x         x         x           x     (6 inputs)
//  syndrom[24]:         x   x                 x x                   xx           (6 inputs)
//  syndrom[25]:         x   x                 x  x                 x   x         (6 inputs)
//  syndrom[26]:         x    x              x      x              x    x         (6 inputs)
//  syndrom[27]:          xx                    x x                 x   x         (6 inputs)
//  syndrom[28]:          x x                   xx                    xx          (6 inputs)
//  syndrom[29]:          x  x                x     x             x       x       (6 inputs)
//  syndrom[30]:           xx                  x  x                  xx           (6 inputs)
//  syndrom[31]:           xx                   xx                   x x          (6 inputs)
//Input usage report:
//  input bit  0 used  3 times (syndrom bits 0 1 2)
//  input bit  1 used  3 times (syndrom bits 3 4 5)
//  input bit  2 used  3 times (syndrom bits 6 7 8)
//  input bit  3 used  3 times (syndrom bits 9 10 11)
//  input bit  4 used  3 times (syndrom bits 12 13 14)
//  input bit  5 used  3 times (syndrom bits 15 16 17)
//  input bit  6 used  3 times (syndrom bits 18 19 20)
//  input bit  7 used  3 times (syndrom bits 21 22 23)
//  input bit  8 used  3 times (syndrom bits 24 25 26)
//  input bit  9 used  3 times (syndrom bits 27 28 29)
//  input bit 10 used  3 times (syndrom bits 27 30 31)
//  input bit 11 used  3 times (syndrom bits 28 30 31)
//  input bit 12 used  3 times (syndrom bits 24 25 29)
//  input bit 13 used  3 times (syndrom bits 21 22 26)
//  input bit 14 used  3 times (syndrom bits 18 19 23)
//  input bit 15 used  3 times (syndrom bits 15 16 20)
//  input bit 16 used  3 times (syndrom bits 12 13 17)
//  input bit 17 used  3 times (syndrom bits 9 10 14)
//  input bit 18 used  3 times (syndrom bits 6 7 11)
//  input bit 19 used  3 times (syndrom bits 3 4 8)
//  input bit 20 used  3 times (syndrom bits 0 1 5)
//  input bit 21 used  3 times (syndrom bits 0 2 5)
//  input bit 22 used  3 times (syndrom bits 1 2 8)
//  input bit 23 used  3 times (syndrom bits 3 4 11)
//  input bit 24 used  3 times (syndrom bits 6 7 14)
//  input bit 25 used  3 times (syndrom bits 9 10 17)
//  input bit 26 used  3 times (syndrom bits 12 13 20)
//  input bit 27 used  3 times (syndrom bits 15 16 23)
//  input bit 28 used  3 times (syndrom bits 18 19 26)
//  input bit 29 used  3 times (syndrom bits 21 22 29)
//  input bit 30 used  3 times (syndrom bits 24 25 30)
//  input bit 31 used  3 times (syndrom bits 27 28 31)
//  input bit 32 used  3 times (syndrom bits 24 28 31)
//  input bit 33 used  3 times (syndrom bits 25 27 30)
//  input bit 34 used  3 times (syndrom bits 18 21 22)
//  input bit 35 used  3 times (syndrom bits 19 26 29)
//  input bit 36 used  3 times (syndrom bits 12 15 16)
//  input bit 37 used  3 times (syndrom bits 13 20 23)
//  input bit 38 used  3 times (syndrom bits 6 9 10)
//  input bit 39 used  3 times (syndrom bits 7 14 17)
//  input bit 40 used  3 times (syndrom bits 2 3 4)
//  input bit 41 used  3 times (syndrom bits 1 8 11)
//  input bit 42 used  3 times (syndrom bits 0 5 11)
//  input bit 43 used  3 times (syndrom bits 0 5 8)
//  input bit 44 used  3 times (syndrom bits 1 3 4)
//  input bit 45 used  3 times (syndrom bits 2 7 17)
//  input bit 46 used  3 times (syndrom bits 6 9 14)
//  input bit 47 used  3 times (syndrom bits 10 13 23)
//  input bit 48 used  3 times (syndrom bits 12 15 20)
//  input bit 49 used  3 times (syndrom bits 16 19 29)
//  input bit 50 used  3 times (syndrom bits 18 21 26)
//  input bit 51 used  3 times (syndrom bits 22 25 27)
//  input bit 52 used  3 times (syndrom bits 24 30 31)
//  input bit 53 used  3 times (syndrom bits 24 28 30)
//  input bit 54 used  3 times (syndrom bits 22 28 31)
//  input bit 55 used  3 times (syndrom bits 25 26 27)
//  input bit 56 used  3 times (syndrom bits 16 18 21)
//  input bit 57 used  3 times (syndrom bits 19 20 29)
//  input bit 58 used  3 times (syndrom bits 10 12 15)
//  input bit 59 used  3 times (syndrom bits 13 14 23)
//  input bit 60 used  3 times (syndrom bits 2 6 9)
//  input bit 61 used  3 times (syndrom bits 1 7 17)
//  input bit 62 used  3 times (syndrom bits 0 3 5)
//  input bit 63 used  3 times (syndrom bits 4 8 11)
function [32-1:0] extended_hamming_code_96_64_f;
    input [64-1:0] in;
    reg [32-1:0] syndrom;
    begin
        syndrom[ 0] = in[ 0]^in[20]^in[21]^in[42]^in[43]^in[62];//6 inputs
        syndrom[ 1] = in[ 0]^in[20]^in[22]^in[41]^in[44]^in[61];//6 inputs
        syndrom[ 2] = in[ 0]^in[21]^in[22]^in[40]^in[45]^in[60];//6 inputs
        syndrom[ 3] = in[ 1]^in[19]^in[23]^in[40]^in[44]^in[62];//6 inputs
        syndrom[ 4] = in[ 1]^in[19]^in[23]^in[40]^in[44]^in[63];//6 inputs
        syndrom[ 5] = in[ 1]^in[20]^in[21]^in[42]^in[43]^in[62];//6 inputs
        syndrom[ 6] = in[ 2]^in[18]^in[24]^in[38]^in[46]^in[60];//6 inputs
        syndrom[ 7] = in[ 2]^in[18]^in[24]^in[39]^in[45]^in[61];//6 inputs
        syndrom[ 8] = in[ 2]^in[19]^in[22]^in[41]^in[43]^in[63];//6 inputs
        syndrom[ 9] = in[ 3]^in[17]^in[25]^in[38]^in[46]^in[60];//6 inputs
        syndrom[10] = in[ 3]^in[17]^in[25]^in[38]^in[47]^in[58];//6 inputs
        syndrom[11] = in[ 3]^in[18]^in[23]^in[41]^in[42]^in[63];//6 inputs
        syndrom[12] = in[ 4]^in[16]^in[26]^in[36]^in[48]^in[58];//6 inputs
        syndrom[13] = in[ 4]^in[16]^in[26]^in[37]^in[47]^in[59];//6 inputs
        syndrom[14] = in[ 4]^in[17]^in[24]^in[39]^in[46]^in[59];//6 inputs
        syndrom[15] = in[ 5]^in[15]^in[27]^in[36]^in[48]^in[58];//6 inputs
        syndrom[16] = in[ 5]^in[15]^in[27]^in[36]^in[49]^in[56];//6 inputs
        syndrom[17] = in[ 5]^in[16]^in[25]^in[39]^in[45]^in[61];//6 inputs
        syndrom[18] = in[ 6]^in[14]^in[28]^in[34]^in[50]^in[56];//6 inputs
        syndrom[19] = in[ 6]^in[14]^in[28]^in[35]^in[49]^in[57];//6 inputs
        syndrom[20] = in[ 6]^in[15]^in[26]^in[37]^in[48]^in[57];//6 inputs
        syndrom[21] = in[ 7]^in[13]^in[29]^in[34]^in[50]^in[56];//6 inputs
        syndrom[22] = in[ 7]^in[13]^in[29]^in[34]^in[51]^in[54];//6 inputs
        syndrom[23] = in[ 7]^in[14]^in[27]^in[37]^in[47]^in[59];//6 inputs
        syndrom[24] = in[ 8]^in[12]^in[30]^in[32]^in[52]^in[53];//6 inputs
        syndrom[25] = in[ 8]^in[12]^in[30]^in[33]^in[51]^in[55];//6 inputs
        syndrom[26] = in[ 8]^in[13]^in[28]^in[35]^in[50]^in[55];//6 inputs
        syndrom[27] = in[ 9]^in[10]^in[31]^in[33]^in[51]^in[55];//6 inputs
        syndrom[28] = in[ 9]^in[11]^in[31]^in[32]^in[53]^in[54];//6 inputs
        syndrom[29] = in[ 9]^in[12]^in[29]^in[35]^in[49]^in[57];//6 inputs
        syndrom[30] = in[10]^in[11]^in[30]^in[33]^in[52]^in[53];//6 inputs
        syndrom[31] = in[10]^in[11]^in[31]^in[32]^in[52]^in[54];//6 inputs
        extended_hamming_code_96_64_f = syndrom;
    end
endfunction


function [64-1:0] func_next_mask;
    input [64-1:0] seed;
	input [64-1:0] key;
	reg   [64-1:0] mixin;
	begin
		mixin = func_substitution(func_permutation(seed ^ key));
    	func_next_mask = {extended_hamming_code_96_64_f(func_permutation(mixin)),extended_hamming_code_96_64_f(mixin)};
	end
endfunction


function [63:0] func_next_initial_state_mask;
    input [63:0] seed;
	func_next_initial_state_mask = func_next_mask(seed,64'hE56D24FA_7BC3D5AB);
endfunction

function [63:0] func_next_wi_mask;
    input [63:0] seed;
    func_next_wi_mask = func_next_mask(seed,64'hB2DD568E_019A3FBD);
endfunction


wire [64-1:0] next_initial_state_mask = func_next_initial_state_mask(initial_state_mask);

wire [64-1:0] w0_mdat = w[0+:64];
reg [64-1:0] w0_mask;
wire [64-1:0] next_w0_mask = func_next_wi_mask(w0_mask);
wire [64-1:0] w1_mdat = w[1*64+:64];
reg [64-1:0] w1_mask;
wire [64-1:0] next_w1_mask = func_next_wi_mask(w1_mask);
wire [64-1:0] w6_mdat = w[6*64+:64];
reg [64-1:0] w6_mask;
wire [64-1:0] next_w6_mask = func_next_wi_mask(w6_mask);
wire [64-1:0] w14_mdat = w[14*64+:64];
reg [64-1:0] w14_mask;
wire [64-1:0] next_w14_mask = func_next_wi_mask(w14_mask);
wire [64-1:0] w15_mdat = w[15*64+:64];
reg [64-1:0] w15_mask;
wire [64-1:0] next_w15_mask = func_next_wi_mask(w15_mask);

wire [64-1:0] initial_state_write_delta_mask = initial_state_mask ^ state_mask[7*64+:64];
wire [64-1:0] initial_state_write_mdat = state[7*64+:64] ^ initial_state_write_delta_mask;

wire [64-1:0] initial_a_mask = w[7*64+:64];
wire [64-1:0] initial_b_mask = w[6*64+:64];
wire [64-1:0] initial_c_mask = w[5*64+:64];
wire [64-1:0] initial_d_mask = w[4*64+:64];
wire [64-1:0] initial_e_mask = w[3*64+:64];
wire [64-1:0] initial_f_mask = w[2*64+:64];
wire [64-1:0] initial_g_mask = w[1*64+:64];
wire [64-1:0] initial_h_mask = w[0*64+:64];

//SHA256 funcs
function [31:0] sigma0_256;
    input [31:0] x;
    sigma0_256 = s_256(x,2) ^ s_256(x,13) ^ s_256(x,22);
endfunction
function [31:0] sigma1_256;
    input [31:0] x;
    sigma1_256 = s_256(x,6) ^ s_256(x,11) ^ s_256(x,25);
endfunction
function [31:0] s_256;
    input [31:0] x;
    input integer n;
    s_256 = (x >> n) | (x<<(32-n));
endfunction
function [31:0] gamma0_256;
    input [31:0] x;
    gamma0_256 = s_256(x,7) ^ s_256(x,18) ^ (x>>3);
endfunction
function [31:0] gamma1_256;
    input [31:0] x;
    gamma1_256 = s_256(x,17) ^ s_256(x,19) ^ (x>>10);
endfunction

//SHA512 funcs
function [63:0] sigma0;//boolean
    input [63:0] x;
    sigma0 = s(x,28) ^ s(x,34) ^ s(x,39);
endfunction
function [63:0] sigma1;//boolean
    input [63:0] x;
    sigma1 = s(x,14) ^ s(x,18) ^ s(x,41);
endfunction
function [63:0] s;//boolean
    input [63:0] x;
    input integer n;
    s = (x >> n) | (x<<(64-n));
endfunction
function [63:0] gamma0;//boolean
    input [63:0] x;
    gamma0 = s(x,1) ^ s(x,8) ^ (x>>7);
endfunction
function [63:0] gamma1;//boolean
    input [63:0] x;
    gamma1 = s(x,61) ^ s(x,19) ^ (x>>6);
endfunction

reg state_update;    
reg [7:0] step;   
reg [7:0] state_step;
wire state_stage_ready = state_step == step;
wire [7:0] step_incr = step + 1'b1; 
wire [7:0] step_rounds_max = i_sha512 ? 80+15+1 : 64+15+1;
wire [7:0] step_max = step_rounds_max+4;
wire [6:0] adders_width = i_sha512 ? 64 : 32;
always @(posedge i_clk,posedge i_reset) begin: CONTROL
    if(i_reset) begin
        step <= 0;
        o_run <= 0;
        o_valid <= 0;
        state_update <= 0;
    end else begin
        if(i_write) begin
            step <= step_incr;
            o_valid <= 0;
            if(step==15) begin
                o_run <= 1'b1;               
                state_update <= 1'b1;            
            end            
        end else if(o_run) begin
            if(state_stage_ready) begin
                if(step==step_max+1'b1) begin
                    state_update <= 1'b0;
                    o_valid <= 1'b1;
                    o_run <= 1'b0;
                end else begin
                    state_update <= 1'b1;        
                end
                step <= step_incr;
            end
        end else if(o_valid) begin
            if(step==step_max+1+8) begin
                o_valid <= 0;
                step <= 0;
            end else if(i_read) step <= step_incr;
        end else begin
            state_update <= 1'b0;        
        end
    end
end  
reg [64-1:0] gamma1_out_mdat;
reg [64-1:0] gamma1_out_mask;
reg [64-1:0] gamma0_out_mdat;
reg [64-1:0] gamma0_out_mask;
reg [64-1:0] w_wi_out_mdat;
reg [64-1:0] w_wi_out_mask;
wire w_wi_adder_out_mdat;
wire w_wi_adder_out_mask;
wire [64-1:0] w_wi_delta_mask;
sha2_sec_ti2_rm0_xor #(.WIDTH(64)) u_wi_delta_mask_xor_ITK(.a(w_wi_out_mask), .b(next_w0_mask), .y(w_wi_delta_mask));
wire [64-1:0] w_wi_write_to_w0 = w_wi_out_mdat ^ w_wi_delta_mask;
wire [64-1:0] i_dat_delta_mask;
sha2_sec_ti2_rm0_xor #(.WIDTH(64)) u_dat_delta_mask_xor_ITK(.a(i_dat_mask), .b(next_w0_mask), .y(i_dat_delta_mask));
wire [64-1:0] i_dat_write_to_w0 = i_dat_mdat ^ i_dat_delta_mask;

wire [64*64-1:0] k_256 = 2048'hc67178f2bef9a3f7a4506ceb90befffa8cc7020884c8781478a5636f748f82ee682e6ff35b9cca4f4ed8aa4a391c0cb334b0bcb52748774c1e376c0819a4c116106aa070f40e3585d6990624d192e819c76c51a3c24b8b70a81a664ba2bfe8a192722c8581c2c92e766a0abb650a735453380d134d2c6dfc2e1b213827b70a851429296706ca6351d5a79147c6e00bf3bf597fc7b00327c8a831c66d983e515276f988da5cb0a9dc4a7484aa2de92c6f240ca1cc0fc19dc6efbe4786e49b69c1c19bf1749bdc06a780deb1fe72be5d74550c7dc3243185be12835b01d807aa98ab1c5ed5923f82a459f111f13956c25be9b5dba5b5c0fbcf71374491428a2f98;
wire [80*64-1:0] k = 5120'h6c44198c4a4758175fcb6fab3ad6faec597f299cfc657e2a4cc5d4becb3e42b6431d67c49c100d4c3c9ebe0a15c9bebc32caab7b40c7249328db77f523047d841b710b35131c471b113f9804bef90dae0a637dc5a2c898a606f067aa72176fbaf57d4f7fee6ed178eada7dd6cde0eb1ed186b8c721c0c207ca273eceea26619cc67178f2e372532bbef9a3f7b2c67915a4506cebde82bde990befffa23631e288cc702081a6439ec84c87814a1f0ab7278a5636f43172f60748f82ee5defb2fc682e6ff3d6b2b8a35b9cca4f7763e3734ed8aa4ae3418acb391c0cb3c5c95a6334b0bcb5e19b48a82748774cdf8eeb991e376c085141ab5319a4c116b8d2d0c8106aa07032bbd1b8f40e35855771202ad69906245565a910d192e819d6ef5218c76c51a30654be30c24b8b70d0f89791a81a664bbc423001a2bfe8a14cf1036492722c851482353b81c2c92e47edaee6766a0abb3c77b2a8650a73548baf63de53380d139d95b3df4d2c6dfc5ac42aed2e1b21385c26c92627b70a8546d22ffc142929670a0e6e7006ca6351e003826fd5a79147930aa725c6e00bf33da88fc2bf597fc7beef0ee4b00327c898fb213fa831c66d2db43210983e5152ee66dfab76f988da831153b55cb0a9dcbd41fbd44a7484aa6ea6e4832de92c6f592b0275240ca1cc77ac9c650fc19dc68b8cd5b5efbe4786384f25e3e49b69c19ef14ad2c19bf174cf6926949bdc06a725c7123580deb1fe3b1696b172be5d74f27b896f550c7dc3d5ffb4e2243185be4ee4b28c12835b0145706fbed807aa98a3030242ab1c5ed5da6d8118923f82a4af194f9b59f111f1b605d0193956c25bf348b538e9b5dba58189dbbcb5c0fbcfec4d3b2f7137449123ef65cd428a2f98d728ae22;

reg [64-1:0] initial_a,initial_b,initial_c,initial_d,initial_e,initial_f,initial_g,initial_h;
always @* {initial_a,initial_b,initial_c,initial_d,initial_e,initial_f,initial_g,initial_h} = initial_state;
reg [64-1:0] a_mdat,b_mdat,c_mdat,d_mdat,e_mdat,f_mdat,g_mdat,h_mdat;
always @* {a_mdat,b_mdat,c_mdat,d_mdat,e_mdat,f_mdat,g_mdat,h_mdat} = state;
wire [31:0] a_256_mdat = a_mdat[31:0];
wire [31:0] b_256_mdat = b_mdat[31:0];
wire [31:0] c_256_mdat = c_mdat[31:0];
wire [31:0] d_256_mdat = d_mdat[31:0];
wire [31:0] e_256_mdat = e_mdat[31:0];
wire [31:0] f_256_mdat = f_mdat[31:0];
wire [31:0] g_256_mdat = g_mdat[31:0];
wire [31:0] h_256_mdat = h_mdat[31:0];
reg [64-1:0] a_mask,b_mask,c_mask,d_mask,e_mask,f_mask,g_mask,h_mask;
always @* {a_mask,b_mask,c_mask,d_mask,e_mask,f_mask,g_mask,h_mask} = state_mask;
wire [31:0] a_256_mask = a_mask[31:0];
wire [31:0] b_256_mask = b_mask[31:0];
wire [31:0] c_256_mask = c_mask[31:0];
wire [31:0] d_256_mask = d_mask[31:0];
wire [31:0] e_256_mask = e_mask[31:0];
wire [31:0] f_256_mask = f_mask[31:0];
wire [31:0] g_256_mask = g_mask[31:0];
wire [31:0] h_256_mask = h_mask[31:0];
reg [64-1:0] ki;
wire [31:0] ki_256 = ki[0+:32];

wire [31:0] sigma1_e_256_mdat = sigma1_256(e_256_mdat);
wire [31:0] sigma1_e_256_mask = sigma1_256(e_256_mask);
wire [31:0] gamma1_256_mdat = gamma1_256(w1_mdat[0+:32]);
wire [31:0] gamma1_256_mask = gamma1_256(w1_mask[0+:32]);
wire [31:0] gamma0_256_mdat = gamma0_256(w14_mdat[0+:32]);
wire [31:0] gamma0_256_mask = gamma0_256(w14_mask);
wire [31:0] sigma0_a_256_mdat = sigma0_256(a_256_mdat);
wire [31:0] sigma0_a_256_mask = sigma0_256(a_256_mask);

wire [63:0] sigma1_e_mdat = sigma1(e_mdat);
wire [63:0] sigma1_e_mask = sigma1(e_mask);
wire [63:0] gamma1_mdat = gamma1(w1_mdat);
wire [63:0] gamma1_mask = gamma1(w1_mask);
wire [63:0] gamma0_mdat = gamma0(w14_mdat);
wire [63:0] gamma0_mask = gamma0(w14_mask);               
wire [63:0] sigma0_a_mdat = sigma0(a_mdat);
wire [63:0] sigma0_a_mask = sigma0(a_mask);

reg [64-1:0] t1_adder_sigma1_mdat;
reg [64-1:0] t1_adder_sigma1_mask;

reg [6-1:0] maj_rnd_in;
wire serial_maj_mdat;
wire serial_maj_mask;
sha2_sec_ti2_rm0_masked_maj u_serial_maj(
    .i_clk(i_clk),.i_rnd(maj_rnd_in),
    .i_x_mdat(b_mdat[0]),.i_y_mdat(c_mdat[0]),.i_z_mdat(d_mdat[0]),
    .i_x_mask(b_mask[0]),.i_y_mask(c_mask[0]),.i_z_mask(d_mask[0]),
    .o_mdat(serial_maj_mdat), .o_mask(serial_maj_mask)
    );
wire ch_out_mdat;
wire ch_out_mask;
reg [2-1:0] ch_rnd_in;
sha2_sec_ti2_rm0_serial_masked_ch u_ch(
    .i_clk(i_clk),.i_rnd(ch_rnd_in),
    .i_x_mdat(f_mdat[0]),.i_y_mdat(g_mdat[0]),.i_z_mdat(h_mdat[0]),
    .i_x_mask(f_mask[0]),.i_y_mask(g_mask[0]),.i_z_mask(h_mask[0]),
    .o_mdat(ch_out_mdat), .o_mask(ch_out_mask)
    );    

reg [64-1:0] t2_adder_sigma0_mdat;
reg [64-1:0] t2_adder_sigma0_mask;

function [63:0] rr64;
    input [63:0] in;
    rr64 = {in[0],in[1+:63]};
endfunction
function [63:0] sr64;
    input [63:0] in;
    input msb;
    sr64 = {msb,in[1+:63]};
endfunction
function [64-1:0] rr32;
    input [64-1:0] in;
    rr32 = {in[32+:32],in[0],in[1+:31]};
endfunction
function [64-1:0] sr32;
    input [64-1:0] in;
    input msb;
    sr32 = {in[32+:32],msb,in[1+:31]};
endfunction
                      
function [64-1:0] rr_word;
    input [64-1:0] in;
    rr_word = i_sha512 ? rr64(in) : rr32(in);
endfunction
function [64-1:0] sr_word;
    input [64-1:0] in;
    input msb;
    sr_word = i_sha512 ? sr64(in,msb) : sr32(in,msb);
endfunction

//return {mdat,mask}
function [2*64-1:0] rr_masked_word;
    input [64-1:0] in_mdat;
    input [64-1:0] in_mask;
    rr_masked_word = {rr_word(in_mdat),rr_word(in_mask)};
endfunction
localparam A_IDX = 3'h7;
localparam B_IDX = 3'h6;
localparam C_IDX = 3'h5;
localparam D_IDX = 3'h4;
localparam E_IDX = 3'h3;
localparam F_IDX = 3'h2;
localparam G_IDX = 3'h1;
localparam H_IDX = 3'h0;
function [2*64-1:0] rr_state_word;
    input [2:0] word_idx;
    rr_state_word = rr_masked_word(state[word_idx*64+:64],state_mask[word_idx*64+:64]);
endfunction

wire t1_adder_out_mdat,t1_adder_out_mask;
wire t2_adder_out_mdat,t2_adder_out_mask;
wire next_a_adder_out_mdat,next_a_adder_out_mask;
wire next_e_adder_out_mdat,next_e_adder_out_mask;

wire [64-1:0] next_a = h_mdat;//t1 + t2;
wire [64-1:0] next_b = a_mdat;
wire [64-1:0] next_c = b_mdat;
wire [64-1:0] next_d = c_mdat;
wire [64-1:0] next_e = d_mdat;//d + t1;
wire [64-1:0] next_f = e_mdat;
wire [64-1:0] next_g = f_mdat;
wire [64-1:0] next_h = g_mdat;
wire [64-1:0] next_a_mask = h_mask;
wire [64-1:0] next_b_mask = a_mask;
wire [64-1:0] next_c_mask = b_mask;
wire [64-1:0] next_d_mask = c_mask;
wire [64-1:0] next_e_mask = d_mask;
wire [64-1:0] next_f_mask = e_mask;
wire [64-1:0] next_g_mask = f_mask;
wire [64-1:0] next_h_mask = g_mask;

reg [6:0] adders_step;
reg adders_load_in;
wire maj_start = adders_step == 1;
wire maj_stop = adders_step == adders_width+1;
wire ch_stop = adders_step == adders_width-1;
wire t1_adder_start = adders_step == 1;
wire t1_adder_stop = adders_step == adders_width;
wire w_wi_adder_stop = adders_step == adders_width+2;
wire t2_adder_start = adders_step == 3;
wire t2_adder_stop = adders_step == adders_width+3;
wire next_e_adder_start = adders_step == 4;
wire next_e_adder_stop = adders_step == adders_width+4;
wire next_a_adder_start = next_e_adder_start;
wire next_a_adder_stop = next_e_adder_stop;
reg t1_adder_run,t2_adder_run,next_e_adder_run,adders_run,w_wi_adder_run;
wire next_a_adder_run = next_e_adder_run;

reg [12-1:0] w_wi_adder_rnd_in;
reg [2:0] w_wi_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_4op w_wi_adder (
    .i_clk(i_clk),.i_start(t1_adder_start),.i_rnd(w_wi_adder_rnd_in),.i_c_mdat(w_wi_adder_ci_rnd),.i_c_mask(w_wi_adder_ci_rnd),
    .i_op0_mdat(gamma0_out_mdat[0]), .i_op1_mdat(gamma1_out_mdat[0]), .i_op2_mdat(w15_mdat[0]), .i_op3_mdat(w6_mdat[0]),
    .i_op0_mask(gamma0_out_mask[0]), .i_op1_mask(gamma1_out_mask[0]), .i_op2_mask(w15_mask[0]), .i_op3_mask(w6_mask[0]),
    .o_dat_mdat(w_wi_adder_out_mdat), .o_dat_mask(w_wi_adder_out_mask)
);
reg [16-1:0] t1_adder_rnd_in;
reg [3:0] t1_adder_ci_rnd;//used to inject 0 in the carry input
reg ki0_mask_rnd;
wire ki0_mdat = ki[0] ^ ki0_mask_rnd;
sha2_sec_ti2_rm0_serial_masked_add_5op t1_adder (
    .i_clk(i_clk),.i_start(t1_adder_start),.i_rnd(t1_adder_rnd_in),.i_c_mdat(t1_adder_ci_rnd),.i_c_mask(t1_adder_ci_rnd),
    .i_op0_mdat(a_mdat[0]), .i_op1_mdat(t1_adder_sigma1_mdat[0]), .i_op2_mdat(ch_out_mdat), .i_op3_mdat(ki0_mdat), .i_op4_mdat(w0_mdat[0]),
    .i_op0_mask(a_mask[0]), .i_op1_mask(t1_adder_sigma1_mask[0]), .i_op2_mask(ch_out_mask), .i_op3_mask(ki0_mask_rnd),  .i_op4_mask(w0_mask[0]),
    .o_dat_mdat(t1_adder_out_mdat), .o_dat_mask(t1_adder_out_mask)
);
reg [4-1:0] next_e_adder_rnd_in;
reg next_e_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op next_e_adder (
    .i_clk(i_clk),.i_start(next_e_adder_start),.i_rnd(next_e_adder_rnd_in),.i_c_mdat(next_e_adder_ci_rnd),.i_c_mask(next_e_adder_ci_rnd),
    .i_op0_mdat(t1_adder_out_mdat), .i_op1_mdat(e_mdat[0]),
    .i_op0_mask(t1_adder_out_mask), .i_op1_mask(e_mask[0]),
    .o_dat_mdat(next_e_adder_out_mdat), .o_dat_mask(next_e_adder_out_mask)
);
reg [4-1:0] t2_adder_rnd_in;
reg t2_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op t2_adder (
    .i_clk(i_clk),.i_start(t2_adder_start),.i_rnd(t2_adder_rnd_in),.i_c_mdat(t2_adder_ci_rnd),.i_c_mask(t2_adder_ci_rnd),
    .i_op0_mdat(t2_adder_sigma0_mdat[0]), .i_op1_mdat(serial_maj_mdat),
    .i_op0_mask(t2_adder_sigma0_mask[0]), .i_op1_mask(serial_maj_mask),
    .o_dat_mdat(t2_adder_out_mdat), .o_dat_mask(t2_adder_out_mask)
);
reg [4-1:0] next_a_adder_rnd_in;
reg next_a_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op next_a_adder (
    .i_clk(i_clk),.i_start(next_a_adder_start),.i_rnd(next_a_adder_rnd_in),.i_c_mdat(next_a_adder_ci_rnd),.i_c_mask(next_a_adder_ci_rnd),
    .i_op0_mdat(t1_adder_out_mdat), .i_op1_mdat(t2_adder_out_mdat),
    .i_op0_mask(t1_adder_out_mask), .i_op1_mask(t2_adder_out_mask),
    .o_dat_mdat(next_a_adder_out_mdat), .o_dat_mask(next_a_adder_out_mask)
);
reg [4-1:0] final_a_adder_rnd_in;
reg [4-1:0] final_b_adder_rnd_in;
reg [4-1:0] final_c_adder_rnd_in;
reg [4-1:0] final_d_adder_rnd_in;
reg [4-1:0] final_e_adder_rnd_in;
reg [4-1:0] final_f_adder_rnd_in;
reg [4-1:0] final_g_adder_rnd_in;
reg [4-1:0] final_h_adder_rnd_in;

reg final_adders_start;
wire final_a_adder_out_mdat,final_a_adder_out_mask;
reg final_a_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_a_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_a_adder_ci_rnd),.i_c_mask(final_a_adder_ci_rnd),.i_rnd(final_a_adder_rnd_in),
    .i_op0_mdat(a_mdat[0]), .i_op1_mdat(initial_a[0]),
    .i_op0_mask(a_mask[0]), .i_op1_mask(initial_a_mask[0]),
    .o_dat_mdat(final_a_adder_out_mdat), .o_dat_mask(final_a_adder_out_mask)
);
wire final_b_adder_out_mdat,final_b_adder_out_mask;
reg final_b_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_b_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_b_adder_ci_rnd),.i_c_mask(final_b_adder_ci_rnd),.i_rnd(final_b_adder_rnd_in),
    .i_op0_mdat(b_mdat[0]), .i_op1_mdat(initial_b[0]),
    .i_op0_mask(b_mask[0]), .i_op1_mask(initial_b_mask[0]),
    .o_dat_mdat(final_b_adder_out_mdat), .o_dat_mask(final_b_adder_out_mask)
);
wire final_c_adder_out_mdat,final_c_adder_out_mask;
reg final_c_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_c_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_c_adder_ci_rnd),.i_c_mask(final_c_adder_ci_rnd),.i_rnd(final_c_adder_rnd_in),
    .i_op0_mdat(c_mdat[0]), .i_op1_mdat(initial_c[0]),
    .i_op0_mask(c_mask[0]), .i_op1_mask(initial_c_mask[0]),
    .o_dat_mdat(final_c_adder_out_mdat), .o_dat_mask(final_c_adder_out_mask)
);
wire final_d_adder_out_mdat,final_d_adder_out_mask;
reg final_d_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_d_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_d_adder_ci_rnd),.i_c_mask(final_d_adder_ci_rnd),.i_rnd(final_d_adder_rnd_in),
    .i_op0_mdat(d_mdat[0]), .i_op1_mdat(initial_d[0]),
    .i_op0_mask(d_mask[0]), .i_op1_mask(initial_d_mask[0]),
    .o_dat_mdat(final_d_adder_out_mdat), .o_dat_mask(final_d_adder_out_mask)
);
wire final_e_adder_out_mdat,final_e_adder_out_mask;
reg final_e_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_e_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_e_adder_ci_rnd),.i_c_mask(final_e_adder_ci_rnd),.i_rnd(final_e_adder_rnd_in),
    .i_op0_mdat(e_mdat[0]), .i_op1_mdat(initial_e[0]),
    .i_op0_mask(e_mask[0]), .i_op1_mask(initial_e_mask[0]),
    .o_dat_mdat(final_e_adder_out_mdat), .o_dat_mask(final_e_adder_out_mask)
);
wire final_f_adder_out_mdat,final_f_adder_out_mask;
reg final_f_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_f_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_f_adder_ci_rnd),.i_c_mask(final_f_adder_ci_rnd),.i_rnd(final_f_adder_rnd_in),
    .i_op0_mdat(f_mdat[0]), .i_op1_mdat(initial_f[0]),
    .i_op0_mask(f_mask[0]), .i_op1_mask(initial_f_mask[0]),
    .o_dat_mdat(final_f_adder_out_mdat), .o_dat_mask(final_f_adder_out_mask)
);
wire final_g_adder_out_mdat,final_g_adder_out_mask;
reg final_g_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_g_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_g_adder_ci_rnd),.i_c_mask(final_g_adder_ci_rnd),.i_rnd(final_g_adder_rnd_in),
    .i_op0_mdat(g_mdat[0]), .i_op1_mdat(initial_g[0]),
    .i_op0_mask(g_mask[0]), .i_op1_mask(initial_g_mask[0]),
    .o_dat_mdat(final_g_adder_out_mdat), .o_dat_mask(final_g_adder_out_mask)
);
wire final_h_adder_out_mdat,final_h_adder_out_mask;
reg final_h_adder_ci_rnd;//used to inject 0 in the carry input
sha2_sec_ti2_rm0_serial_masked_add_2op final_h_adder (
    .i_clk(i_clk),.i_start(final_adders_start),.i_c_mdat(final_h_adder_ci_rnd),.i_c_mask(final_h_adder_ci_rnd),.i_rnd(final_h_adder_rnd_in),
    .i_op0_mdat(h_mdat[0]), .i_op1_mdat(initial_h[0]),
    .i_op0_mask(h_mask[0]), .i_op1_mask(initial_h_mask[0]),
    .o_dat_mdat(final_h_adder_out_mdat), .o_dat_mask(final_h_adder_out_mask)
);

reg sigma_step;
wire [64-1:0] w15_delta_mask;
sha2_sec_ti2_rm0_xor #(.WIDTH(64)) u_w15_delta_mask_xor_ITK(.a(w15_mask), .b(next_w0_mask), .y(w15_delta_mask));
wire [64-1:0] w15_write_to_w0 = w15_mdat ^ w15_delta_mask;

always @* begin
	{
maj_rnd_in,
ch_rnd_in,
	ki0_mask_rnd,
	w_wi_adder_ci_rnd,t1_adder_ci_rnd,next_e_adder_ci_rnd,t2_adder_ci_rnd,next_a_adder_ci_rnd,
    w_wi_adder_rnd_in,t1_adder_rnd_in,next_e_adder_rnd_in,t2_adder_rnd_in,next_a_adder_rnd_in
	} = {{64{1'bx}},i_rnd};//x is there to make the sim fail if we have i_rnd too small
	{
    final_a_adder_ci_rnd,final_b_adder_ci_rnd,final_c_adder_ci_rnd,final_d_adder_ci_rnd,
	final_e_adder_ci_rnd,final_f_adder_ci_rnd,final_g_adder_ci_rnd,final_h_adder_ci_rnd,
	final_a_adder_rnd_in,final_b_adder_rnd_in,final_c_adder_rnd_in,final_d_adder_rnd_in,
	final_e_adder_rnd_in,final_f_adder_rnd_in,final_g_adder_rnd_in,final_h_adder_rnd_in
	} = {{64{1'bx}},i_rnd};//x is there to make the sim fail if we have i_rnd too small
end	

reg maj_run;
reg ch_run;
always @(posedge i_clk,posedge i_reset) begin: STATE_STAGE
    if(i_reset) begin
        adders_step <= {7{1'b0}};
        state_step <= 0;
        {adders_load_in,t1_adder_run,t2_adder_run,next_e_adder_run,adders_run} <= 0;
        sigma_step <= 1'b1; 
        ch_run <= 1'b0;                   
        maj_run <= 1'b0;                   
        final_adders_start <= 1'b1;
    end else begin
        if(state_update) begin
            if(state_step==15) begin
                state_step <= step;
                w <= {w[0+:15*64],w15_write_to_w0};//let w schedule be 1 step ahead of the state, this way we compute w0 and state can be updated in parallel.
                {w15_mask,w14_mask,w6_mask,w1_mask,w0_mask} <= {next_w15_mask,next_w14_mask,next_w6_mask,next_w1_mask,next_w0_mask}; 
            end else if(state_step<step_rounds_max) begin
                final_adders_start <= 1'b1;           
                case({sigma_step,adders_load_in,adders_run})
                3'b100: begin
                    adders_load_in <= 1'b1;
                    sigma_step <= 1'b0;
                    t1_adder_sigma1_mdat <= {sigma1_e_mdat[32+:32], i_sha512 ? sigma1_e_mdat[0+:32] : sigma1_e_256_mdat};
                    t1_adder_sigma1_mask <= {sigma1_e_mask[32+:32], i_sha512 ? sigma1_e_mask[0+:32] : sigma1_e_256_mask};
                    t2_adder_sigma0_mdat <= {sigma0_a_mdat[32+:32], i_sha512 ? sigma0_a_mdat[0+:32] : sigma0_a_256_mdat};
                    t2_adder_sigma0_mask <= {sigma0_a_mask[32+:32], i_sha512 ? sigma0_a_mask[0+:32] : sigma0_a_256_mask};
                    
                    state <= {next_a,next_b,next_c,next_d,next_e,next_f,next_g,next_h};
                    state_mask <= {next_a_mask,next_b_mask,next_c_mask,next_d_mask,next_e_mask,next_f_mask,next_g_mask,next_h_mask};                                                                                
                end
                3'b010: begin
                    adders_load_in <= 1'b0;
                    adders_run <= 1'b1;
                    t1_adder_run <= 1'b1;
                    adders_step <= 1'b1;
                    w_wi_adder_run <= 1'b1;
                    //t1 operands
                    if(i_sha512) begin
						ki <= k[(state_step-16)*64+:64];
					end else begin
						ki <= k_256[(state_step-16)*32+:32];
					end
                    //wi_adder operands
                    gamma1_out_mdat <= i_sha512 ? gamma1_mdat : {{32{1'bx}},gamma1_256_mdat};
                    gamma1_out_mask <= i_sha512 ? gamma1_mask : {{32{1'bx}},gamma1_256_mask};          
                    gamma0_out_mdat <= i_sha512 ? gamma0_mdat : {{32{1'bx}},gamma0_256_mdat};
                    gamma0_out_mask <= i_sha512 ? gamma0_mask : {{32{1'bx}},gamma0_256_mask};   
                    ch_run <= 1'b1;
                    {state[F_IDX*64+:64],state_mask[F_IDX*64+:64]} <= rr_state_word(F_IDX);
                    {state[G_IDX*64+:64],state_mask[G_IDX*64+:64]} <= rr_state_word(G_IDX);
                    {state[H_IDX*64+:64],state_mask[H_IDX*64+:64]} <= rr_state_word(H_IDX);
                end
                3'b001: begin
                    if(next_a_adder_stop) begin
                        adders_run <= 1'b0;
                        adders_step <= {7{1'b0}};
                        state_step <= step;
                        sigma_step <= 1'b1;
                    end else adders_step <= adders_step + 1'b1;
                    
                    if(ch_stop) ch_run <= 1'b0;
                    
                    if(maj_start) maj_run <= 1'b1;
                    else if(maj_stop) maj_run <= 1'b0;
                    
                    if(t1_adder_stop) t1_adder_run <= 1'b0;    
                    if(w_wi_adder_stop) w_wi_adder_run <= 1'b0;    
                    if(t2_adder_start) t2_adder_run <= 1'b1;
                    else if(t2_adder_stop) t2_adder_run <= 1'b0;                                                
                    
                    if(next_e_adder_start) next_e_adder_run <= 1'b1;
                    else if(next_e_adder_stop) next_e_adder_run <= 1'b0;                                
                
                    if(t1_adder_run) begin//t1 operands, w_wi operands
                        ki <= rr_word(ki);
                        t1_adder_sigma1_mdat <= rr_word(t1_adder_sigma1_mdat);
                        t1_adder_sigma1_mask <= rr_word(t1_adder_sigma1_mask);
                        w[0*64+:64] <= rr_word(w0_mdat);
                        w0_mask <= rr_word(w0_mask);
                        //w_wi operands
                        w[( 7-1)*64+:64] <= rr_word(w6_mdat);
                        w6_mask <= rr_word(w6_mask);
                        w[(16-1)*64+:64] <= rr_word(w15_mdat);
                        w15_mask <= rr_word(w15_mask);
                        gamma0_out_mdat <= rr_word(gamma0_out_mdat);
                        gamma0_out_mask <= rr_word(gamma0_out_mask);
                        gamma1_out_mdat <= rr_word(gamma1_out_mdat);
                        gamma1_out_mask <= rr_word(gamma1_out_mask);
                    end else begin
                        if(next_a_adder_stop) begin             
                            if(state_step<32-1) w <= {w[0+:15*64],w15_write_to_w0};
                            else                w <= {w[0+:15*64],w_wi_write_to_w0};
                            {w15_mask,w14_mask,w6_mask,w1_mask,w0_mask} <= {next_w15_mask,next_w14_mask,next_w6_mask,next_w1_mask,next_w0_mask};
                        end
                    end
                    if(w_wi_adder_run) begin
                        //w_wi out
                        w_wi_out_mdat <= sr_word(w_wi_out_mdat,w_wi_adder_out_mdat);
                        w_wi_out_mask <= sr_word(w_wi_out_mask,w_wi_adder_out_mask);                    
                    end
                    if(t1_adder_run | next_a_adder_run) begin//t1 operand / next_a out
                        state     [A_IDX*64+:64] <= sr_word(a_mdat,next_a_adder_out_mdat);//h operand is stored in state[7*64+:64]
                        state_mask[A_IDX*64+:64] <= sr_word(a_mask,next_a_adder_out_mask);//h operand is stored in state[7*64+:64]
                    end                    
                    //t2 operands
                    if(t2_adder_start|t2_adder_run) begin
                        t2_adder_sigma0_mdat <= rr_word(t2_adder_sigma0_mdat);
                        t2_adder_sigma0_mask <= rr_word(t2_adder_sigma0_mask);
                    end
                    //next_e operand
                    if(next_e_adder_start | next_e_adder_run) begin
                        state     [E_IDX*64+:64] <= sr_word(e_mdat,next_e_adder_out_mdat);//d operand is stored in state[3*64+:64]
                        state_mask[E_IDX*64+:64] <= sr_word(e_mask,next_e_adder_out_mask);//d operand is stored in state[3*64+:64]
                    end 
                    if(ch_run) begin
                        {state[F_IDX*64+:64],state_mask[F_IDX*64+:64]} <= rr_state_word(F_IDX);
                        {state[G_IDX*64+:64],state_mask[G_IDX*64+:64]} <= rr_state_word(G_IDX);
                        {state[H_IDX*64+:64],state_mask[H_IDX*64+:64]} <= rr_state_word(H_IDX);
                    end 
                    if(maj_run) begin
                        {state[B_IDX*64+:64],state_mask[B_IDX*64+:64]} <= rr_state_word(B_IDX);
                        {state[C_IDX*64+:64],state_mask[C_IDX*64+:64]} <= rr_state_word(C_IDX);
                        {state[D_IDX*64+:64],state_mask[D_IDX*64+:64]} <= rr_state_word(D_IDX);
                    end                        
                end
                endcase 
                initial_state_mask <= initial_state_mask_seed;
            end else if(state_step<step_max) begin
                w <= {w[0+:15*64],initial_state_mask};
                {w15_mask,w14_mask,w6_mask,w1_mask,w0_mask} <= {next_w15_mask,next_w14_mask,next_w6_mask,next_w1_mask,next_w0_mask};//we don't use mask anymore, but still generate them to hide our activities
                initial_state_mask <= next_initial_state_mask;
                state_step <= step;
            end else begin
                final_adders_start <= 1'b0;
                {w15_mask,w14_mask,w6_mask,w1_mask,w0_mask} <= {next_w15_mask,next_w14_mask,next_w6_mask,next_w1_mask,next_w0_mask};//we don't use mask anymore, but still generate them to hide our activities
                state <= {
					sr_word(a_mdat,final_a_adder_out_mdat),
					sr_word(b_mdat,final_b_adder_out_mdat),
					sr_word(c_mdat,final_c_adder_out_mdat),
					sr_word(d_mdat,final_d_adder_out_mdat),
					sr_word(e_mdat,final_e_adder_out_mdat),
					sr_word(f_mdat,final_f_adder_out_mdat),
					sr_word(g_mdat,final_g_adder_out_mdat),
					sr_word(h_mdat,final_h_adder_out_mdat)
				};
				state_mask <= {
					sr_word(a_mask,final_a_adder_out_mask),
					sr_word(b_mask,final_b_adder_out_mask),
					sr_word(c_mask,final_c_adder_out_mask),
					sr_word(d_mask,final_d_adder_out_mask),
					sr_word(e_mask,final_e_adder_out_mask),
					sr_word(f_mask,final_f_adder_out_mask),
					sr_word(g_mask,final_g_adder_out_mask),
					sr_word(h_mask,final_h_adder_out_mask)
				};        
				initial_state <= {
					rr_word(initial_a),
					rr_word(initial_b),
					rr_word(initial_c),
					rr_word(initial_d),
					rr_word(initial_e),
					rr_word(initial_f),
					rr_word(initial_g),
					rr_word(initial_h)
				};
				w <= {      w[8*64+:8*64],
					rr_word(w[7*64+:64]),
					rr_word(w[6*64+:64]),
					rr_word(w[5*64+:64]),
					rr_word(w[4*64+:64]),
					rr_word(w[3*64+:64]),
					rr_word(w[2*64+:64]),
					rr_word(w[1*64+:64]),
					rr_word(w[0*64+:64])
				};
                if(adders_step==adders_width-1) begin
                    adders_step <= {7{1'b0}};
                    state_step <= step;
                end else adders_step <= adders_step + 1'b1;
            end    
        end else begin
            adders_step <= {7{1'b0}};
            if(i_write_state) begin
                state <= {state[0+:7*64],i_dat_mdat^i_rnd[0+:64]};
                state_mask <= {state_mask[0+:7*64],i_dat_mask^i_rnd[0+:64]};
            end else if(i_read) begin
                state <= {state[0+:7*64],state[7*64+:64]};
                state_mask <= {state_mask[0+:7*64],state_mask[7*64+:64]};
            end
            if(i_write) begin
                w <= {w[0+:15*64],i_dat_write_to_w0}; 
                w0_mask <= next_w0_mask; 
                if(step>=1) w1_mask <= next_w1_mask;
                if(step>=6) w6_mask <= next_w6_mask;
                if(step>=14) w14_mask <= next_w14_mask;
                if(step>=15) w15_mask <= next_w15_mask; 
                if(step[3]) begin//copy state to initial_state during the load of the last 8 words of the message
                    initial_state <= {initial_state[0+:7*64],initial_state_write_mdat};
                    initial_state_mask <= next_initial_state_mask;
                    state <= {state[0+:7*64],state[7*64+:64]};
                    state_mask <= {state_mask[0+:7*64],state_mask[7*64+:64]};
                end           
            end
            if(i_init_mask) begin
                w0_mask <= i_rnd;
                w1_mask <= i_rnd;
                w6_mask <= i_rnd;
                w14_mask <= i_rnd;
                w15_mask <= i_rnd;
                initial_state_mask_seed <= w0_mask;
                initial_state_mask <= w0_mask;
            end
            state_step <= step;
        end
    end
end    


wire [64-1:0] _dbg_i_dat = i_dat_mdat ^ i_dat_mask;
wire [64-1:0] _dbg_o_dat = o_dat_mdat ^ o_dat_mask;
wire [64-1:0] _dbg_w0 = w0_mdat ^ w0_mask;
wire [64-1:0] _dbg_w1 = w1_mdat ^ w1_mask;
wire [64-1:0] _dbg_w6 = w6_mdat ^ w6_mask;
wire [64-1:0] _dbg_w14 = w14_mdat ^ w14_mask;
wire [64-1:0] _dbg_w15 = w15_mdat ^ w15_mask;
wire _dbg_next_a_adder_out = next_a_adder_out_mdat ^ next_a_adder_out_mask;
wire _dbg_next_e_adder_out = next_e_adder_out_mdat ^ next_e_adder_out_mask;
wire _dbg_t1_adder_out = t1_adder_out_mdat ^ t1_adder_out_mask;
wire _dbg_t2_adder_out = t2_adder_out_mdat ^ t2_adder_out_mask;

wire [64-1:0] _dbg_a = a_mdat ^ a_mask;
wire [64-1:0] _dbg_b = b_mdat ^ b_mask;
wire [64-1:0] _dbg_c = c_mdat ^ c_mask;
wire [64-1:0] _dbg_d = d_mdat ^ d_mask;
wire [64-1:0] _dbg_e = e_mdat ^ e_mask;
wire [64-1:0] _dbg_f = f_mdat ^ f_mask;
wire [64-1:0] _dbg_g = g_mdat ^ g_mask;
wire [64-1:0] _dbg_h = h_mdat ^ h_mask;

wire [8*64-1:0] _dbg_initial_state_final_add = initial_state ^ w[0+:8*64];
wire [64-1:0] _dbg_initial_state_final_add_a = initial_a ^ initial_a_mask;
wire [64-1:0] _dbg_initial_state_final_add_b = initial_b ^ initial_b_mask;
wire [64-1:0] _dbg_initial_state_final_add_c = initial_c ^ initial_c_mask;
wire [64-1:0] _dbg_initial_state_final_add_d = initial_d ^ initial_d_mask;
wire [64-1:0] _dbg_initial_state_final_add_e = initial_e ^ initial_e_mask;
wire [64-1:0] _dbg_initial_state_final_add_f = initial_f ^ initial_f_mask;
wire [64-1:0] _dbg_initial_state_final_add_g = initial_g ^ initial_g_mask;
wire [64-1:0] _dbg_initial_state_final_add_h = initial_h ^ initial_h_mask;
reg [8*64-1:0] _dbg_initial_state;
reg [64-1:0] _dbg_initial_state_mask;
always @* begin: DGB_INITIAL_STATE
    integer i;
    _dbg_initial_state_mask = initial_state_mask_seed;
    for(i=0;i<8;i=i+1) begin
        _dbg_initial_state[(7-i)*64+:64] = initial_state[(7-i)*64+:64] ^ _dbg_initial_state_mask;
        _dbg_initial_state_mask = func_next_initial_state_mask(_dbg_initial_state_mask);
    end
end

reg [16*64-1:0] _dbg_w;
reg [64-1:0] _dbg_w_mask;
always @* begin: DGB_W
    integer i;
    _dbg_w_mask = w15_mask;
    for(i=0;i<16;i=i+1) begin
        _dbg_w[(15-i)*64+:64] = w[(15-i)*64+:64] ^ _dbg_w_mask;
        _dbg_w_mask = func_next_wi_mask(_dbg_w_mask);
    end
end
wire [64-1:0] _dbg_w0_ref = _dbg_w[0*64+:64];
wire [64-1:0] _dbg_w1_ref = _dbg_w[1*64+:64];
wire [64-1:0] _dbg_w2 = _dbg_w[2*64+:64];
wire [64-1:0] _dbg_w3 = _dbg_w[3*64+:64];
wire [64-1:0] _dbg_w4 = _dbg_w[4*64+:64];
wire [64-1:0] _dbg_w5 = _dbg_w[5*64+:64];
wire [64-1:0] _dbg_w6_ref = _dbg_w[6*64+:64];
wire [64-1:0] _dbg_w7 = _dbg_w[7*64+:64];
wire [64-1:0] _dbg_w8 = _dbg_w[8*64+:64];
wire [64-1:0] _dbg_w9 = _dbg_w[9*64+:64];
wire [64-1:0] _dbg_w10 = _dbg_w[10*64+:64];
wire [64-1:0] _dbg_w11 = _dbg_w[11*64+:64];
wire [64-1:0] _dbg_w12 = _dbg_w[12*64+:64];
wire [64-1:0] _dbg_w13 = _dbg_w[13*64+:64];
wire [64-1:0] _dbg_w14_ref = _dbg_w[14*64+:64];
wire [64-1:0] _dbg_w15_ref = _dbg_w[15*64+:64];

wire _dbg_w0_check = _dbg_w0_ref === _dbg_w0;
wire _dbg_w1_check = _dbg_w1_ref === _dbg_w1;
wire _dbg_w6_check = _dbg_w6_ref === _dbg_w6;
wire _dbg_w14_check = _dbg_w14_ref === _dbg_w14;
wire _dbg_w15_check = _dbg_w15_ref === _dbg_w15;

wire _dbg_final_a_adder_ina = a_mdat[0] ^ a_mask[0];
wire _dbg_final_a_adder_inb = initial_a[0] ^ initial_a_mask[0];

wire _dbg_final_a_adder_out = final_a_adder_out_mdat ^ final_a_adder_out_mask;
wire _dbg_final_b_adder_out = final_b_adder_out_mdat ^ final_b_adder_out_mask;
wire _dbg_final_c_adder_out = final_c_adder_out_mdat ^ final_c_adder_out_mask;
wire _dbg_final_d_adder_out = final_d_adder_out_mdat ^ final_d_adder_out_mask;
wire _dbg_final_e_adder_out = final_e_adder_out_mdat ^ final_e_adder_out_mask;
wire _dbg_final_f_adder_out = final_f_adder_out_mdat ^ final_f_adder_out_mask;
wire _dbg_final_g_adder_out = final_g_adder_out_mdat ^ final_g_adder_out_mask;
wire _dbg_final_h_adder_out = final_h_adder_out_mdat ^ final_h_adder_out_mask;

wire [31:0] _dbg_sigma1_256_e = sigma1_e_256_mdat ^ sigma1_e_256_mask;
wire [64-1:0] _dbg_sigma1_e = sigma1_e_mdat ^ sigma1_e_mask;
wire [64-1:0] _dbg_gamma1_out = gamma1_out_mdat ^ gamma1_out_mask;
wire [64-1:0] _dbg_gamma0_out = gamma0_out_mdat ^ gamma0_out_mask;
wire [64-1:0] _dbg_sigma0_a = sigma0_a_mdat ^ sigma0_a_mask;

wire [64-1:0] _dbg_t1_adder_sigma1 = t1_adder_sigma1_mdat ^ t1_adder_sigma1_mask;

wire _dbg_ch_out = ch_out_mdat ^ ch_out_mask;
wire _dbg_serial_maj = serial_maj_mdat ^ serial_maj_mask;

wire _dbg_maj = serial_maj_mdat ^ serial_maj_mask;

wire [64-1:0] _dbg_t2_adder_sigma0 = t2_adder_sigma0_mdat ^ t2_adder_sigma0_mask;

reg [64-1:0] _dbg_t1;
always @(posedge i_clk) begin
    if(t1_adder_start) _dbg_t1 <= {64{1'b0}};
    else _dbg_t1 <= {_dbg_t1_adder_out,_dbg_t1[1+:64-1]};
end
reg [64-1:0] _dbg_next_e;
always @(posedge i_clk) begin
    if(next_e_adder_start) _dbg_next_e <= {64{1'b0}};
    else _dbg_next_e <= {_dbg_next_e_adder_out,_dbg_next_e[1+:64-1]};
end
reg [64-1:0] _dbg_t2;
always @(posedge i_clk) begin
    if(t2_adder_start) _dbg_t2 <= {64{1'b0}};
    else _dbg_t2 <= {_dbg_t2_adder_out,_dbg_t2[1+:64-1]};
end
reg [64-1:0] _dbg_next_a;
always @(posedge i_clk) begin
    if(next_a_adder_start) _dbg_next_a <= {64{1'b0}};
    else _dbg_next_a <= {_dbg_next_a_adder_out,_dbg_next_a[1+:64-1]};
end

endmodule
module sha2_sec_ti2_rm0_masked_maj #(
    parameter WIDTH = 1
)(
    input wire i_clk,
    input wire [3*2*WIDTH-1:0] i_rnd,
    input wire [WIDTH-1:0] i_x_mdat,
    input wire [WIDTH-1:0] i_x_mask,
    input wire [WIDTH-1:0] i_y_mdat,
    input wire [WIDTH-1:0] i_y_mask,
    input wire [WIDTH-1:0] i_z_mdat,//all inputs must be stable during computation (2 cycles)
    input wire [WIDTH-1:0] i_z_mask,        
	output reg [WIDTH-1:0] o_mdat,
    output reg [WIDTH-1:0] o_mask
);
wire [WIDTH-1:0] x0 = i_x_mask;
wire [WIDTH-1:0] x1 = i_x_mdat;

wire [WIDTH-1:0] y0 = i_y_mask;
wire [WIDTH-1:0] y1 = i_y_mdat;

wire [WIDTH-1:0] z0 = i_z_mask;
wire [WIDTH-1:0] z1 = i_z_mdat;
wire [WIDTH-1:0] xy_out0;
wire [WIDTH-1:0] xy_out1;
wire [WIDTH-1:0] xz_out0;
wire [WIDTH-1:0] xz_out1;
wire [WIDTH-1:0] yz_out0;
wire [WIDTH-1:0] yz_out1;

genvar i;
generate
for(i=0;i<WIDTH;i=i+1) begin: BIT
	wire [1:0] xi = {x1[i],x0[i]};
	wire [1:0] yi = {y1[i],y0[i]};
	wire [1:0] zi = {z1[i],z0[i]};
	sha2_sec_ti2_rm0_ti2_and u_xy(
        .i_clk(i_clk),.i_rnd(i_rnd[i*2+:2]),
        .i_a(xi),
        .i_b(yi),
        .o_y({xy_out1[i],xy_out0[i]}));
    sha2_sec_ti2_rm0_ti2_and u_xz(
        .i_clk(i_clk),.i_rnd(i_rnd[2*WIDTH+i*2+:2]),
        .i_a(xi),
        .i_b(zi),
        .o_y({xz_out1[i],xz_out0[i]}));
    sha2_sec_ti2_rm0_ti2_and u_yz(
        .i_clk(i_clk),.i_rnd(i_rnd[4*WIDTH+i*2+:2]),
        .i_a(zi),
        .i_b(yi),
        .o_y({yz_out1[i],yz_out0[i]}));
end
endgenerate
always @* begin
    o_mdat = xy_out0 ^ xz_out0 ^ yz_out0;
    o_mask = xy_out1 ^ xz_out1 ^ yz_out1;
end
endmodule

module sha2_sec_ti2_rm0_serial_masked_ch(
    input wire i_clk,
    input wire [2-1:0] i_rnd,
    input wire i_x_mdat,
    input wire i_x_mask,
    input wire i_y_mdat,
    input wire i_y_mask,
    input wire i_z_mdat,
    input wire i_z_mask,        
    output reg o_mdat,
    output reg o_mask
);
//ch_256 = z ^ (x & (y ^ z));
reg z_mdat_l1;
reg z_mask_l1;
wire temp0 = i_y_mdat ^ i_z_mdat;   
wire temp1 = i_y_mask ^ i_z_mask;
wire x0 = i_x_mask;
wire x1 = i_x_mdat;
always @(posedge i_clk) {z_mdat_l1,z_mask_l1} <= {i_z_mdat,i_z_mask};
wire and_out0;
wire and_out1;
sha2_sec_ti2_rm0_ti2_and u(
	.i_clk(i_clk),.i_rnd(i_rnd),
	.i_a({x1,x0}),
	.i_b({temp1,temp0}),
	.o_y({and_out1,and_out0}));

always @* begin
    o_mdat = and_out0 ^ z_mdat_l1;
    o_mask = and_out1 ^ z_mask_l1;
end
endmodule

module sha2_sec_ti2_rm0_serial_masked_add_5op (
    input wire i_clk,
    input wire i_start,
    input wire [16-1:0] i_rnd,
    input wire [3:0] i_c_mdat,
    input wire [3:0] i_c_mask,
    input wire i_op0_mdat,
    input wire i_op1_mdat,
    input wire i_op2_mdat,
    input wire i_op3_mdat,
    input wire i_op4_mdat,
    input wire i_op0_mask, 
    input wire i_op1_mask,
    input wire i_op2_mask,
    input wire i_op3_mask,
    input wire i_op4_mask,    	
    output reg o_dat_mdat,
    output reg o_dat_mask
);
wire [1:0] op0 = {i_op0_mask,i_op0_mdat};
wire [1:0] op1 = {i_op1_mask,i_op1_mdat};
wire [1:0] op2 = {i_op2_mask,i_op2_mdat};
wire [1:0] op3 = {i_op3_mask,i_op3_mdat};
wire [1:0] op4 = {i_op4_mask,i_op4_mdat};
wire [1:0] c0 = {i_c_mask[0],i_c_mdat[0]};
wire [1:0] c1 = {i_c_mask[1],i_c_mdat[1]};
wire [1:0] c2 = {i_c_mask[2],i_c_mdat[2]};
wire [1:0] c3 = {i_c_mask[3],i_c_mdat[3]};
reg [2-1:0] op4_l1,op4_l2;
reg start_l1,start_l2;
always @(posedge i_clk) begin
    op4_l1 <= op4;
    op4_l2 <= op4_l1;
    start_l1 <= i_start;
    start_l2 <= start_l1;
end
wire [2-1:0] q01,co01;
wire [2-1:0] ci01 = i_start ? c0 : co01;
sha2_sec_ti2_rm0_masked_full_adder_ti add01(
    .i_clk(i_clk),.i_rnd(i_rnd[0*4+:4]),
    .i_a(op0),.i_b(op1),.i_c(ci01),
    .o_q(q01), .o_c(co01));
wire [2-1:0] q23,co23;
wire [2-1:0] ci23 = i_start ? c1 : co23;
sha2_sec_ti2_rm0_masked_full_adder_ti add23(
    .i_clk(i_clk),.i_rnd(i_rnd[1*4+:4]),
    .i_a(op2),.i_b(op3),.i_c(ci23),
    .o_q(q23), .o_c(co23));
wire [2-1:0] q03,co03;
wire [2-1:0] ci03 = start_l1 ? c2 : co03;
sha2_sec_ti2_rm0_masked_full_adder_ti add03(
    .i_clk(i_clk),.i_rnd(i_rnd[2*4+:4]),
    .i_a(q01),.i_b(q23),.i_c(ci03),
    .o_q(q03), .o_c(co03));
wire [2-1:0] q04,co04;
wire [2-1:0] ci04 = start_l2 ? c3 : co04;
sha2_sec_ti2_rm0_masked_full_adder_ti add04(
    .i_clk(i_clk),.i_rnd(i_rnd[3*4+:4]),
    .i_a(op4_l2),.i_b(q03),.i_c(ci04),
    .o_q(q04), .o_c(co04));
always @* begin
    o_dat_mdat = q04[0];
    o_dat_mask = q04[1];
end
endmodule
module sha2_sec_ti2_rm0_serial_masked_add_4op (
    input wire i_clk,
    input wire i_start,
    input wire [12-1:0] i_rnd,
    input wire [2:0] i_c_mdat,
    input wire [2:0] i_c_mask,
    input wire i_op0_mdat,
    input wire i_op1_mdat,
    input wire i_op2_mdat,
    input wire i_op3_mdat,
    input wire i_op0_mask, 
    input wire i_op1_mask,
    input wire i_op2_mask,
    input wire i_op3_mask,
	output reg o_dat_mdat,
    output reg o_dat_mask
);
wire [1:0] op0 = {i_op0_mask,i_op0_mdat};
wire [1:0] op1 = {i_op1_mask,i_op1_mdat};
wire [1:0] op2 = {i_op2_mask,i_op2_mdat};
wire [1:0] op3 = {i_op3_mask,i_op3_mdat};
wire [1:0] c0 = {i_c_mask[0],i_c_mdat[0]};
wire [1:0] c1 = {i_c_mask[1],i_c_mdat[1]};
wire [1:0] c2 = {i_c_mask[2],i_c_mdat[2]};
reg start_l1;
always @(posedge i_clk) begin
    start_l1 <= i_start;
end
wire [2-1:0] q01,co01;
wire [2-1:0] ci01 = i_start ? c0 : co01;
sha2_sec_ti2_rm0_masked_full_adder_ti add01(
    .i_clk(i_clk),.i_rnd(i_rnd[0*4+:4]),
    .i_a(op0),.i_b(op1),.i_c(ci01),
    .o_q(q01), .o_c(co01));
wire [2-1:0] q23,co23;
wire [2-1:0] ci23 = i_start ? c1 : co23;
sha2_sec_ti2_rm0_masked_full_adder_ti add23(
    .i_clk(i_clk),.i_rnd(i_rnd[1*4+:4]),
    .i_a(op2),.i_b(op3),.i_c(ci23),
    .o_q(q23), .o_c(co23));
wire [2-1:0] q03,co03;
wire [2-1:0] ci03 = start_l1 ? c2 : co03;
sha2_sec_ti2_rm0_masked_full_adder_ti add03(
    .i_clk(i_clk),.i_rnd(i_rnd[2*4+:4]),
    .i_a(q01),.i_b(q23),.i_c(ci03),
    .o_q(q03), .o_c(co03));
always @* begin
    o_dat_mdat = q03[0];
    o_dat_mask = q03[1];
end
endmodule

module sha2_sec_ti2_rm0_serial_masked_add_2op (
    input wire i_clk,
    input wire i_start,
    input wire [4-1:0] i_rnd,
    input wire i_c_mdat,
    input wire i_c_mask,
    input wire i_op0_mdat,
    input wire i_op1_mdat,
    input wire i_op0_mask, 
    input wire i_op1_mask,
    output reg o_dat_mdat,
    output reg o_dat_mask
);
wire [1:0] a = {i_op0_mask,i_op0_mdat};
wire [1:0] b = {i_op1_mask,i_op1_mdat};
wire [1:0] c = {i_c_mask,i_c_mdat};
wire [2-1:0] q,co;
wire [2-1:0] ci = i_start ? c : co;
sha2_sec_ti2_rm0_masked_full_adder_ti impl(
    .i_clk(i_clk),.i_rnd(i_rnd[3:0]),
    .i_a(a),.i_b(b),.i_c(ci),
    .o_q(q), .o_c(co));
always @* begin
    o_dat_mdat = q[0];
    o_dat_mask = q[1];
end
endmodule

module sha2_sec_ti2_rm0_masked_full_adder_ti(
	input wire i_clk,
	input wire [2-1:0] i_a,
	input wire [2-1:0] i_b,
	input wire [2-1:0] i_c,
	input wire [3:0] i_rnd,
	output reg [2-1:0] o_q,
	output reg [2-1:0] o_c
);
wire [2-1:0] x0 = i_a ^ i_b;
wire [2-1:0] n0,n1;
sha2_sec_ti2_rm0_ti2_and u0(.i_clk(i_clk), .i_a(x0), .i_b(i_c), .i_rnd(i_rnd[0+:2]), .o_y(n0));
sha2_sec_ti2_rm0_ti2_and u1(.i_clk(i_clk), .i_a(i_a), .i_b(i_b), .i_rnd(i_rnd[2+:2]), .o_y(n1));
reg [2-1:0] x0_l1;
always @(posedge i_clk) x0_l1 <= x0;
reg [2-1:0] c_l1;
always @(posedge i_clk) c_l1 <= i_c;
always @* begin
    o_q = x0_l1 ^ c_l1;
    o_c = n0 ^ n1; 
end
endmodule

module sha2_sec_ti2_rm0_remask(
    input wire [1:0] a,
    input wire r,
    output wire [1:0] y
    );
sha2_sec_ti2_rm0_xor u0(.a(a[0]), .b(r), .y(y[0]));
sha2_sec_ti2_rm0_xor u1(.a(a[1]), .b(r), .y(y[1]));
endmodule

module sha2_sec_ti2_rm0_xor_impl #(
		parameter WIDTH = 1,
		parameter NOTY = 0
	)(
    input wire [WIDTH-1:0] a,
    input wire [WIDTH-1:0] b,
    output reg [WIDTH-1:0] y
    );
always @* y = NOTY ^ a ^ b;
endmodule

module sha2_sec_ti2_rm0_xor #(
		parameter WIDTH = 1
	)(
    input wire [WIDTH-1:0] a,
    input wire [WIDTH-1:0] b,
    output wire [WIDTH-1:0] y
    );
sha2_sec_ti2_rm0_xor_impl #(.WIDTH(WIDTH)) u_ITK(.a(a), .b(b), .y(y));
endmodule

module sha2_sec_ti2_rm0_plain_and(
    input wire a,
    input wire b,
    output reg q
    );
always @* q = a&b;
endmodule

module sha2_sec_ti2_rm0_plain_nand(
    input wire a,
    input wire b,
    output reg q
    );
always @* q = ~(a&b);
endmodule

module sha2_sec_ti2_rm0_ti2_and_l0 #(
    parameter NOTA = 1'b0,
    parameter NOTB = 1'b0,
    parameter NOTY = 1'b0    
    )(
    input wire [1:0] i_a,   //WARNING: must be uniform
    input wire [1:0] i_b,   //WARNING: must be uniform
    output reg [1:0] o_y    //WARNING: non uniform
    );
wire [1:0] a = i_a^ NOTA[0];
wire [1:0] b = i_b^ NOTB[0];
wire n00,n10,n01;
wire n11;

sha2_sec_ti2_rm0_plain_nand nand00_ITK(.a(a[0]), .b(b[0]), .q(n00));
sha2_sec_ti2_rm0_plain_nand nand10_ITK(.a(a[1]), .b(b[0]), .q(n10));
sha2_sec_ti2_rm0_plain_nand nand01_ITK(.a(a[0]), .b(b[1]), .q(n01));
sha2_sec_ti2_rm0_plain_nand nand11_ITK(.a(a[1]), .b(b[1]), .q(n11));

always @* begin
    o_y[0] = n00 ^ n11 ^ NOTY[0];
    o_y[1] = n10 ^ n01;
end
endmodule

module sha2_sec_ti2_rm0_ti2_and_l1 #(
    parameter NOTA = 1'b0,
    parameter NOTB = 1'b0,
    parameter NOTY = 1'b0    
    )(
    input wire i_clk,
    input wire [1:0] i_a,   //WARNING: must be uniform
    input wire [1:0] i_b,   //WARNING: must be uniform
    input wire [1:0] i_rnd,
    output wire [1:0] o_y	//SAFE: this is uniform (remasked)
    );
wire r0 = i_rnd[0];
wire r1 = i_rnd[1];
wire [1:0] a = i_a^ NOTA[0];
wire [1:0] b = i_b^ NOTB[0];
wire n00,n10,n01,n11;

sha2_sec_ti2_rm0_plain_nand nand00_ITK(.a(a[0]), .b(b[0]), .q(n00));
sha2_sec_ti2_rm0_plain_nand nand10_ITK(.a(a[1]), .b(b[0]), .q(n10));
sha2_sec_ti2_rm0_plain_nand nand01_ITK(.a(a[0]), .b(b[1]), .q(n01));
sha2_sec_ti2_rm0_plain_nand nand11_ITK(.a(a[1]), .b(b[1]), .q(n11));

reg tmp00,tmp01,tmp10,tmp11;
wire next_tmp00,next_tmp01,next_tmp10,next_tmp11;
sha2_sec_ti2_rm0_xor_impl xor00_ITK(.a(n00), .b(r0), .y(next_tmp00));
sha2_sec_ti2_rm0_xor_impl xor01_ITK(.a(n01), .b(r1), .y(next_tmp01));
sha2_sec_ti2_rm0_xor_impl xor10_ITK(.a(n10), .b(r0), .y(next_tmp10));
sha2_sec_ti2_rm0_xor_impl xor11_ITK(.a(n11), .b(r1), .y(next_tmp11));

always @(posedge i_clk) begin
    tmp00 <= next_tmp00 ^ NOTY[0];
    tmp01 <= next_tmp01;
    tmp10 <= next_tmp10;
    tmp11 <= next_tmp11;
end
sha2_sec_ti2_rm0_xor_impl u_y0_ITK(.a(tmp00), .b(tmp01), .y(o_y[0]));
sha2_sec_ti2_rm0_xor_impl u_y1_ITK(.a(tmp10), .b(tmp11), .y(o_y[1]));
endmodule

module sha2_sec_ti2_rm0_ti2_and(
    input wire i_clk,
    input wire [1:0] i_a,
    input wire [1:0] i_b,
    input wire [1:0] i_rnd,
    output wire[1:0] o_y
);
sha2_sec_ti2_rm0_ti2_and_l1 #(.NOTA(0), .NOTB(0), .NOTY(0)) impl_ITK(.i_clk(i_clk),.i_a(i_a),.i_b(i_b),.i_rnd(i_rnd),.o_y(o_y));
endmodule

//` default_nettype wire

*/