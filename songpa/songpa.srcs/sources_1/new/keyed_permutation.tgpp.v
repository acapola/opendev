

module keyed_permutation #(
    parameter UNIT_WIDTH = 1
    )(
    input wire i_clk,
    input wire [`$NUNITS`*UNIT_WIDTH-1:0] i_dat,
    input wire [`$NUNITS`*INDEX_WIDTH-1:0] i_key,
    input wire i_inverse,
    output reg [`$NUNITS`*UNIT_WIDTH-1:0] o_dat
    );
localparam NUNITS = `$NUNITS`;
localparam INDEX_WIDTH = `$INDEX_WIDTH`;
localparam KEY_WIDTH = `$KEY_WIDTH`;

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
    
``if {$COMPACT_KEY} {``
    begin
        outDone = {NUNITS{1'b0}};
        pos = 0;
        outPos = 0;
    ``
	set remaining [expr $NUNITS-1]
    for {set i 0} {$i<$NUNITS} {incr i} {
		set indexWidth [binWidth $remaining]
	``
		index = {INDEX_WIDTH{1'b0}};
        ``if {$i!=$NUNITS-1} {``
		index = key[pos+:`$indexWidth`] % `expr $remaining + 1`;
		pos = pos + `$indexWidth`;
        ``}``
		outPos = get_nth_zero_index(outDone,index);
		outDone[outPos]=1'b1;
		//map[outPos*INDEX_WIDTH+:INDEX_WIDTH]=`$i`;
		map[`$i`*INDEX_WIDTH+:INDEX_WIDTH]=outPos;//better synthesis results on FPGA
    ``
		incr remaining -1
	}``
``} else {``
    integer i;
    reg [INDEX_WIDTH:0] remaining;
    begin
        outDone = {NUNITS{1'b0}};
        pos = 0;
        outPos = 0;
        remaining = NUNITS;
        for(i=0;i<NUNITS;i=i+1) begin
            index = {INDEX_WIDTH{1'b0}};
            if(i!=NUNITS-1) begin
                index = key[pos+:INDEX_WIDTH] % remaining;
                remaining = remaining - 1;
                pos = pos + INDEX_WIDTH;
            end
            outPos = get_nth_zero_index(outDone,index);
            outDone[outPos]=1'b1;
            map[i*INDEX_WIDTH+:INDEX_WIDTH]=outPos;
        end
``}``   
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
