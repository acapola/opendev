
module keyed_permutation #(
    parameter UNIT_WIDTH = 1
    )(
    input wire i_clk,
    input wire [16*UNIT_WIDTH-1:0] i_dat,
    input wire [16*INDEX_WIDTH-1:0] i_key,
    input wire i_inverse,
    output reg [16*UNIT_WIDTH-1:0] o_dat
    );
localparam NUNITS = 16;
localparam INDEX_WIDTH = 4;
localparam KEY_WIDTH = 49;

function [INDEX_WIDTH-1:0] get_nth_zero_index;
    input [NUNITS-1:0] in;
    input [INDEX_WIDTH-1:0] index;
    integer i;
    reg [INDEX_WIDTH-1:0] zero_index;
    reg [INDEX_WIDTH-1:0] out;
    begin
        out = {INDEX_WIDTH{1'bx}};
        zero_index = 0;
        for(i=0;i<NUNITS;i=i+1) begin
           if(~in[i]) begin
            if(index==zero_index) begin
                out = i;
            end
            zero_index = zero_index + 1;
           end 
        end
        get_nth_zero_index = out;
    end
endfunction

function [NUNITS*INDEX_WIDTH-1:0] compute_map;
	input [KEY_WIDTH-1:0] key;
	reg [NUNITS*INDEX_WIDTH-1:0] map;
    reg [NUNITS-1:0] done;
    reg [INDEX_WIDTH-1:0] outPos;
    reg [NUNITS-1:0] outDone;
	reg [8:0] pos;
    reg [INDEX_WIDTH-1:0] index;
    
    begin
        outDone = {NUNITS{1'b0}};
        pos = 0;
        outPos = 0;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 16;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=0;
		map[0*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 15;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=1;
		map[1*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 14;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=2;
		map[2*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 13;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=3;
		map[3*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 12;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=4;
		map[4*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 11;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=5;
		map[5*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 10;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=6;
		map[6*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:4] % 9;
		pos = pos + 4;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=7;
		map[7*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:3] % 8;
		pos = pos + 3;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=8;
		map[8*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:3] % 7;
		pos = pos + 3;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=9;
		map[9*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:3] % 6;
		pos = pos + 3;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=10;
		map[10*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:3] % 5;
		pos = pos + 3;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=11;
		map[11*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:2] % 4;
		pos = pos + 2;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=12;
		map[12*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:2] % 3;
		pos = pos + 2;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=13;
		map[13*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		index = key[pos+:1] % 2;
		pos = pos + 1;
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=14;
		map[14*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
		index = {INDEX_WIDTH{1'b0}};
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=15;
		map[15*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
        compute_map = map;
    end 
endfunction 

function [NUNITS*UNIT_WIDTH-1:0] permute;
    input [NUNITS*UNIT_WIDTH-1:0] in;
    input [NUNITS*INDEX_WIDTH-1:0] map;
    reg [NUNITS*UNIT_WIDTH-1:0] out;
    integer i;    
    reg [INDEX_WIDTH-1:0] index;    
    begin
        for(i=0;i<NUNITS;i=i+1) begin
            index = map[i*INDEX_WIDTH+:INDEX_WIDTH];
            out[i*UNIT_WIDTH+:UNIT_WIDTH] = in[index*UNIT_WIDTH+:UNIT_WIDTH];
        end
        permute = out;
    end
endfunction

function [NUNITS*UNIT_WIDTH-1:0] unpermute;
    input [NUNITS*UNIT_WIDTH-1:0] in;
    input [NUNITS*INDEX_WIDTH-1:0] map;
    reg [NUNITS*UNIT_WIDTH-1:0] out;
    integer i;    
    reg [INDEX_WIDTH-1:0] index;    
    begin
        for(i=0;i<NUNITS;i=i+1) begin
            index = map[i*INDEX_WIDTH+:INDEX_WIDTH];
            out[index*UNIT_WIDTH+:UNIT_WIDTH] = in[i*UNIT_WIDTH+:UNIT_WIDTH];
        end
        unpermute = out;
    end
endfunction

reg [NUNITS*INDEX_WIDTH-1:0] map;
always @(posedge i_clk) begin
    map <= compute_map(i_key);
    o_dat <= i_inverse ? unpermute(i_dat,map) : permute(i_dat,map);
end

endmodule
