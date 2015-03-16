``
source sfr_gen.tcl
proc verilog_width_decl { width } {
	if {$width==1} {
		return ""
	} else {
		``[`$width`-1:0] ``
	}
	return [tgpp::getProcOutput]
}

proc decl_hardware_update_port { bf_name props } {
	dict with props {
		if {[is_updated_by_hardware $props]} {
			if { $set_by_hardware && $cleared_by_hardware} {
				dict set ::hw_update_ports $bf_name $props``
input   wire `verilog_width_decl $width`sfr_`$bf_name`_wr,
input   wire sfr_`$bf_name`_wren,
``
			} else {
				if {$cleared_by_hardware} {
					set action "clear"
					dict set ::hw_clear_ports $bf_name $props
				} else {
					set action "set"
					dict set ::hw_set_ports $bf_name $props
				}``
input   wire sfr_`$bf_name`_`$action`,
``
			}
		}
		if {$read_sensitive} {
			dict set ::hw_read_sens_ports $bf_name $props``
output  reg  sfr_`$bf_name`_read,
``		}
		if {$write_sensitive} {
			dict set ::hw_write_sens_ports $bf_name $props``
output  reg  sfr_`$bf_name`_write,
``		}
	}
	return [string range [tgpp::getProcOutput] 0 end-1]
} 

proc sfr_apb_module { sfrs {custom_clock 0} {latched_read 1} } {
	set clock PCLK
	if { $custom_clock } {
		set clock i_clk
		set clock_en i_apb_clk_en
	}
	set hasContainers 0
	set hasHardwareUpdate 0
	set ::hw_update_ports [dict create]
	set ::hw_set_ports [dict create]
	set ::hw_clear_ports [dict create]
	set ::hw_read_sens_ports [dict create]
	set ::hw_write_sens_ports [dict create]
``	
/*
`bitfield_to_string $sfrs`
*/
module `dict get $sfrs name`_apbif (
//SFRs read values
``dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	if {[is_container $sfr_props]} { #declare each bitfield separately
		set hasContainers 1
		dict for {bf_name bf_props} [dict get $sfr_props bitfields] {
			if {[is_updated_by_hardware $bf_props]} {set hasHardwareUpdate 1}
			dict with bf_props {``
output  reg  `verilog_width_decl $width`sfr_`${sfr_name}_$name`,	
``			}
		}
	} else {	
		if {[is_updated_by_hardware $bf_props]} {set hasHardwareUpdate 1}
		dict with sfr_props {``
output  reg  `verilog_width_decl $width`sfr_`$name`,
``		}
	}
}``
``if {$hasHardwareUpdate} {``//signals for SFRs updated by hardware or read/write sensitive``}
dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	if {[is_container $sfr_props]} { #declare each bitfield separately
		dict for {bf_name bf_props} [dict get $sfr_props bitfields] {
```decl_hardware_update_port ${sfr_name}_$bf_name $bf_props```
		}
	} else {
```decl_hardware_update_port $sfr_name $sfr_props```
	}
}``

//APB inputs
``if {$custom_clock} {``
input   wire        `$clock`,
input   wire        `$clock_en`,
``} else {``
input   wire        PCLK,
``}``
input   wire        PRESETn,
input   wire        PSEL,
input   wire [11:0] PADDR,
input   wire        PENABLE,
input   wire        PWRITE,
input   wire [3:0]  PSTRB,
input   wire [31:0] PWDATA,
//APB outputs
output  reg  [31:0] PRDATA,
output  reg         PREADY,
output  reg         PSLVERR
);

``if {$hasContainers} {``
//bitfields mapping to full SFR words
``	dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
		if {[is_container $sfr_props]} { 
			set sep " "
			set paddingStr ""
			set width [dict get $sfr_props width]
			set padding [expr [dict get $sfr_props bitfields_width]%32]
			if {$padding} {
				set paddingStr "\{[expr 32-$padding]\{1'b0\}\}"
				set sep ","
			}
	``
wire `verilog_width_decl $width` sfr_`$sfr_name` = {`$paddingStr`
``
			set bf_names [lreverse [dict keys [dict get $sfr_props bitfields]]]
			foreach bf_name $bf_names {
				set bf_props [dict get $sfr_props bitfields $bf_name]
``			`$sep`sfr_`$sfr_name`_`$bf_name`
``		
				set sep ","
			}
``};
``			
		} 
	}
}
set width [dict get $sfrs width]
``

wire sfr_valid_access = PSEL & (PADDR < `expr ($width+7)/8`);
assign PSLVERR = 1'b0;
assign PREADY  = 1'b1;
wire sfr_write_access = sfr_valid_access & PENABLE & PWRITE``if {$custom_clock} {`` & `$clock_en```}``;
``
set register_PRDATA 0
if {$latched_read} {
	set enable_guard ""
} else {
	if {$hasHardwareUpdate} {
		set register_PRDATA 1
	} 
	if {$register_PRDATA} {
		set eq <=
		set sensitivity "posedge $clock, negedge PRESETn"
		set enable_guard ""
	} else {
		set eq =
		set sensitivity "*"
		set enable_guard "& PENABLE "
	}
}
``
wire sfr_read_access  = sfr_valid_access `$enable_guard`& ~PWRITE;
``
if {$register_PRDATA || $latched_read} {``
wire sfr_read_access_1st_cycle = sfr_read_access & ~PENABLE;
wire sfr_read_access_2nd_cycle = sfr_read_access & PENABLE;
``}

if {$latched_read} {``
reg [9:0] word_address;
always @(posedge i_clk) if(i_apb_clk_en) word_address <= PADDR[11:2];
``} else {``
wire [9:0] word_address = PADDR[11:2];
``}``
//////////////////////////////////////////////////////////////////////////////////////////////////
// Read registers
//////////////////////////////////////////////////////////////////////////////////////////////////
``
set sens_ports ""
set sep ""
set sens_ports_cnt 0
set apb ""
if {$latched_read} {set apb _apb}
foreach p [dict keys $::hw_read_sens_ports] {
	append sens_ports "${sep}sfr_${p}_read$apb"
	set sep ","
	incr sens_ports_cnt
}
set sep ""
foreach p [dict keys $::hw_write_sens_ports] {
	append sens_ports "${sep}sfr_${p}_write"
	set sep ","
	incr sens_ports_cnt
}

if {$latched_read} {``
reg read_gate;//high during 1 cycle of i_clk
always @(posedge i_clk) begin
	read_gate <= i_apb_clk_en & sfr_read_access_1st_cycle;
end
reg [31:0] sfr_read_value;
always @* begin
	if(read_gate) PRDATA = sfr_read_value;
end
``dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	set sens_read [dict exists $::hw_read_sens_ports $sfr_name]
	if {$sens_read} {``
reg sfr_`$sfr_name`_read_apb;
always @* sfr_`$sfr_name`_read = read_gate & sfr_`$sfr_name`_read_apb; 
``	}	
}``
always @* begin
	sfr_read_value = 32'hDEADBEEF;
	{`$sens_ports`} = {`$sens_ports_cnt`{1'b0}};
	if (sfr_read_access) begin
		case(word_address)		
``dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	set words [expr [dict get $sfr_props width]/32]
	set range ""
	for {set word 0} {$word<$words} {incr word} {
		set word_address [expr [dict get $sfr_props offset]/32+$word]
		set sens_read [dict exists $::hw_read_sens_ports $sfr_name]
		set sens_write [dict exists $::hw_write_sens_ports $sfr_name]
		if {$words>1} {set range "\[$word*32+:32\]"}
``		`$word_address`: ``if {$sens_read || $sens_write} {``begin
			sfr_read_value = sfr_`$sfr_name``$range`;
``
		#TODO: handle sens ports within bitfields, currently we support only full reg sens ports
		if {$sens_read} {``
			sfr_`$sfr_name`_read_apb = 1'b1;
``		}
		if {$sens_write} {``
			sfr_`$sfr_name`_write = 1'b1;
``		}``
		end``} else {``sfr_read_value = sfr_`$sfr_name``$range`;``}``
``  }
}``
		endcase
	end 
end
``} else {``
always @(`$sensitivity`) begin
``

if {$register_PRDATA} {``
	if (~PRESETn) begin
		PRDATA <= 32'hDEADBEEF;
``	if {$sens_ports_cnt} {``
		{`$sens_ports`} <= {`$sens_ports_cnt`{1'b0}};
``	}``
	end else if (sfr_read_access_1st_cycle) begin
``} else {``
	PRDATA = 32'hDEADBEEF;
``	if {$sens_ports_cnt} {``
		{`$sens_ports`} = {`$sens_ports_cnt`{1'b0}};
``	}``
	if (sfr_read_access) begin
``}``
		case(word_address)		
``dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	set words [expr [dict get $sfr_props width]/32]
	set range ""
	for {set word 0} {$word<$words} {incr word} {
		set word_address [expr [dict get $sfr_props offset]/32+$word]
		if {$words>1} {set range "\[$word*32+:32\]"}
``		`$word_address`: PRDATA `$eq` sfr_`$sfr_name``$range`;``
		#TODO: handle sens ports within bitfields, currently we support only full reg sens ports
		if {[dict exists $::hw_read_sens_ports $sfr_name]} {``sfr_`$sfr_name`_read `$eq` 1'b1;``}
		if {[dict exists $::hw_write_sens_ports $sfr_name]} {``sfr_`$sfr_name`_write `$eq` 1'b1;``}``
``  }
}``
		endcase
	end ``if {$register_PRDATA} {``else if(~sfr_read_access_2nd_cycle) begin
		PRDATA <= 32'hDEADBEEF;
		{`$sens_ports`} <= {`$sens_ports_cnt`{1'b0}};
	end ``}``
end
``}``

//////////////////////////////////////////////////////////////////////////////////////////////////
// Write registers
//////////////////////////////////////////////////////////////////////////////////////////////////
always @(posedge `$clock` or negedge PRESETn) begin
	if(~PRESETn) begin
``dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	if {[is_container $sfr_props]} {
		dict for {bf_name bf_props} [dict get $sfr_props bitfields] {
			set reset_value [dict get $bf_props reset_value]
			if {$reset_value!=""} {
``		sfr_`$sfr_name`_`$bf_name` <= `$reset_value`;
``  		}
		}
	} else {
		set reset_value [dict get $sfr_props reset_value]
		if {$reset_value!=""} {
``		sfr_`$sfr_name` <= `$reset_value`;
``  	}
	}
}``
    end else begin
		if(sfr_write_access) begin
			case(word_address)
``dict for {sfr_name sfr_props} [dict get $sfrs bitfields] {
	set words [expr [dict get $sfr_props width]/32]
	set word_base ""
	for {set word 0} {$word<$words} {incr word} {
		#TODO: handle write sens ports
		set word_address [expr [dict get $sfr_props offset]/32+$word]
		if {$words>1} {set word_base "$word*32+"}
``			`$word_address`: begin
``		
		for {set lane 0} {$lane<4} {incr lane} {
			if {[is_container $sfr_props]} {
				set lane_base [expr $word *32 + $lane *8]
				set lane_limit [expr $lane_base + 8]
				dict for {bf_name bf_props} [dict get $sfr_props bitfields] {
					set width [dict get $bf_props width]
					set offset [expr [dict get $bf_props offset]+[dict get $sfr_props offset]]
					set lane_limited_offset $offset 
					if {$lane_base>$lane_limited_offset} {set lane_limited_offset $lane_base}
					set lane_limited_width [expr $offset+$width-$lane_base]
					if {$lane_limited_width>$width} {set lane_limited_width $width}
					if {$lane_limited_width>8} {set lane_limited_width 8}
					set offset_in_lane [expr $lane_limited_offset % 8]
					if {(![dict get $bf_props read_only]) && ($lane_limited_width>0)} {
					#if (PSTRB[`$lane`]) sfr_`$sfr_name`_`$bf_name`[`$lane_limited_offset`+:`$lane_limited_width`] <= PWDATA[`$lane`*8+`$offset_in_lane`+:`$lane_limited_width`];
``				if (PSTRB[`$lane`]) sfr_`$sfr_name`_`$bf_name` <= PWDATA[`$lane`*8+`$offset_in_lane`+:`$lane_limited_width`];
``					}
				}
			} else {
				if {![dict get $sfr_props read_only]} {
``				if (PSTRB[`$lane`]) sfr_`$sfr_name`[`$word_base``$lane`*8+:8] <= PWDATA[`$lane`*8+:8];
``				}
			}
		}``
			end
``  }
}``
			endcase
		end``if {$hasHardwareUpdate} {`` else begin //hardware set or clear (priority to software)
			//TODO: ::hw_set_ports
			//TODO: ::hw_clear_ports
		end
``		dict for {bf_name props} $::hw_update_ports {
			if {![dict get $props read_only]} {``
			//WARNING: this bit is updated by hardware and not read only
``			}``
		if (sfr_`$bf_name`_wren) sfr_`$bf_name` <= sfr_`$bf_name`_wr;
``		}
``	
	end
``	}``
end

endmodule
``
	return [tgpp::getProcOutput]
}
``
`sfr_apb_module $trng_sfrs 1 1`
