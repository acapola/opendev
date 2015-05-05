
/////////////////////////////////////////////////////////////////////////////////////////////////
// test module: instantiate all declared modules
/////////////////////////////////////////////////////////////////////////////////////////////////
module edc_ecc_hc_test(
	input wire i_reset,
	input wire i_clk,
	input wire [384-1:0] i_dat,
	input wire [384*2-1:0] i_fault,
	output wire [69-1:0] o_edc_hc_detection,
	output wire [69-1:0] o_edc_ehc_detection
	output wire [6174-1:0] o_corrected_data,
	output wire [69-1:0] o_correction,
	output wire [69-1:0] o_ecc_detection
);
reg [384-1:0] data_storage;
reg [9764-1:0] edc_storage;
wire [9764-1:0] edc_write;

always @(posedge i_clk, posedge i_reset) begin
	if(i_reset) {data_storage,edc_storage} <= {384+9764{1'b0}};
	else {data_storage,edc_storage} <= {i_dat,edc_write};
end

edc_hc_4_min_width u_edc_hc_4_min_width(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[0+:3]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[0+:3]),
	.o_detection(o_edc_hc_detection[0])
);
edc_ehc_4_min_width u_edc_ehc_4_min_width(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[3+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[3+:4]),
	.o_detection(o_edc_ehc_detection[0])
);
ecc_ehc_4_min_width u_ecc_ehc_4_min_width(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[7+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[7+:4]),
	.o_read_data(o_corrected_data[0+:4]),
	.o_correction(o_correction[0]),
	.o_detection(o_ecc_detection[0])
);
edc_hc_4_min_delay u_edc_hc_4_min_delay(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[11+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[11+:4]),
	.o_detection(o_edc_hc_detection[1])
);
edc_ehc_4_min_delay u_edc_ehc_4_min_delay(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[15+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[15+:4]),
	.o_detection(o_edc_ehc_detection[1])
);
ecc_ehc_4_min_delay u_ecc_ehc_4_min_delay(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[19+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[19+:4]),
	.o_read_data(o_corrected_data[4+:4]),
	.o_correction(o_correction[1]),
	.o_detection(o_ecc_detection[1])
);
edc_hc_4_balanced u_edc_hc_4_balanced(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[23+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[23+:4]),
	.o_detection(o_edc_hc_detection[2])
);
edc_ehc_4_balanced u_edc_ehc_4_balanced(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[27+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[27+:4]),
	.o_detection(o_edc_ehc_detection[2])
);
ecc_ehc_4_balanced u_ecc_ehc_4_balanced(
	.i_write_data(i_dat[4-1:0]),
	.o_write_edc(edc_write[31+:4]),
	.i_stored_data(data_storage[4-1:0]),
	.i_stored_edc(edc_storage[31+:4]),
	.o_read_data(o_corrected_data[8+:4]),
	.o_correction(o_correction[2]),
	.o_detection(o_ecc_detection[2])
);
edc_hc_8_min_width u_edc_hc_8_min_width(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[35+:4]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[35+:4]),
	.o_detection(o_edc_hc_detection[3])
);
edc_ehc_8_min_width u_edc_ehc_8_min_width(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[39+:5]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[39+:5]),
	.o_detection(o_edc_ehc_detection[3])
);
ecc_ehc_8_min_width u_ecc_ehc_8_min_width(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[44+:5]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[44+:5]),
	.o_read_data(o_corrected_data[12+:8]),
	.o_correction(o_correction[3]),
	.o_detection(o_ecc_detection[3])
);
edc_hc_8_min_delay u_edc_hc_8_min_delay(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[49+:8]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[49+:8]),
	.o_detection(o_edc_hc_detection[4])
);
edc_ehc_8_min_delay u_edc_ehc_8_min_delay(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[57+:8]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[57+:8]),
	.o_detection(o_edc_ehc_detection[4])
);
ecc_ehc_8_min_delay u_ecc_ehc_8_min_delay(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[65+:8]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[65+:8]),
	.o_read_data(o_corrected_data[20+:8]),
	.o_correction(o_correction[4]),
	.o_detection(o_ecc_detection[4])
);
edc_hc_8_balanced u_edc_hc_8_balanced(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[73+:4]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[73+:4]),
	.o_detection(o_edc_hc_detection[5])
);
edc_ehc_8_balanced u_edc_ehc_8_balanced(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[77+:6]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[77+:6]),
	.o_detection(o_edc_ehc_detection[5])
);
ecc_ehc_8_balanced u_ecc_ehc_8_balanced(
	.i_write_data(i_dat[8-1:0]),
	.o_write_edc(edc_write[83+:6]),
	.i_stored_data(data_storage[8-1:0]),
	.i_stored_edc(edc_storage[83+:6]),
	.o_read_data(o_corrected_data[28+:8]),
	.o_correction(o_correction[5]),
	.o_detection(o_ecc_detection[5])
);
edc_hc_12_min_width u_edc_hc_12_min_width(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[89+:5]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[89+:5]),
	.o_detection(o_edc_hc_detection[6])
);
edc_ehc_12_min_width u_edc_ehc_12_min_width(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[94+:6]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[94+:6]),
	.o_detection(o_edc_ehc_detection[6])
);
ecc_ehc_12_min_width u_ecc_ehc_12_min_width(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[100+:6]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[100+:6]),
	.o_read_data(o_corrected_data[36+:12]),
	.o_correction(o_correction[6]),
	.o_detection(o_ecc_detection[6])
);
edc_hc_12_min_delay u_edc_hc_12_min_delay(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[106+:12]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[106+:12]),
	.o_detection(o_edc_hc_detection[7])
);
edc_ehc_12_min_delay u_edc_ehc_12_min_delay(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[118+:12]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[118+:12]),
	.o_detection(o_edc_ehc_detection[7])
);
ecc_ehc_12_min_delay u_ecc_ehc_12_min_delay(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[130+:12]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[130+:12]),
	.o_read_data(o_corrected_data[48+:12]),
	.o_correction(o_correction[7]),
	.o_detection(o_ecc_detection[7])
);
edc_hc_12_balanced u_edc_hc_12_balanced(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[142+:6]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[142+:6]),
	.o_detection(o_edc_hc_detection[8])
);
edc_ehc_12_balanced u_edc_ehc_12_balanced(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[148+:6]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[148+:6]),
	.o_detection(o_edc_ehc_detection[8])
);
ecc_ehc_12_balanced u_ecc_ehc_12_balanced(
	.i_write_data(i_dat[12-1:0]),
	.o_write_edc(edc_write[154+:6]),
	.i_stored_data(data_storage[12-1:0]),
	.i_stored_edc(edc_storage[154+:6]),
	.o_read_data(o_corrected_data[60+:12]),
	.o_correction(o_correction[8]),
	.o_detection(o_ecc_detection[8])
);
edc_hc_16_min_width u_edc_hc_16_min_width(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[160+:5]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[160+:5]),
	.o_detection(o_edc_hc_detection[9])
);
edc_ehc_16_min_width u_edc_ehc_16_min_width(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[165+:6]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[165+:6]),
	.o_detection(o_edc_ehc_detection[9])
);
ecc_ehc_16_min_width u_ecc_ehc_16_min_width(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[171+:6]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[171+:6]),
	.o_read_data(o_corrected_data[72+:16]),
	.o_correction(o_correction[9]),
	.o_detection(o_ecc_detection[9])
);
edc_hc_16_min_delay u_edc_hc_16_min_delay(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[177+:16]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[177+:16]),
	.o_detection(o_edc_hc_detection[10])
);
edc_ehc_16_min_delay u_edc_ehc_16_min_delay(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[193+:16]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[193+:16]),
	.o_detection(o_edc_ehc_detection[10])
);
ecc_ehc_16_min_delay u_ecc_ehc_16_min_delay(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[209+:16]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[209+:16]),
	.o_read_data(o_corrected_data[88+:16]),
	.o_correction(o_correction[10]),
	.o_detection(o_ecc_detection[10])
);
edc_hc_16_balanced u_edc_hc_16_balanced(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[225+:8]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[225+:8]),
	.o_detection(o_edc_hc_detection[11])
);
edc_ehc_16_balanced u_edc_ehc_16_balanced(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[233+:8]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[233+:8]),
	.o_detection(o_edc_ehc_detection[11])
);
ecc_ehc_16_balanced u_ecc_ehc_16_balanced(
	.i_write_data(i_dat[16-1:0]),
	.o_write_edc(edc_write[241+:8]),
	.i_stored_data(data_storage[16-1:0]),
	.i_stored_edc(edc_storage[241+:8]),
	.o_read_data(o_corrected_data[104+:16]),
	.o_correction(o_correction[11]),
	.o_detection(o_ecc_detection[11])
);
edc_hc_20_min_width u_edc_hc_20_min_width(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[249+:5]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[249+:5]),
	.o_detection(o_edc_hc_detection[12])
);
edc_ehc_20_min_width u_edc_ehc_20_min_width(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[254+:6]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[254+:6]),
	.o_detection(o_edc_ehc_detection[12])
);
ecc_ehc_20_min_width u_ecc_ehc_20_min_width(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[260+:6]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[260+:6]),
	.o_read_data(o_corrected_data[120+:20]),
	.o_correction(o_correction[12]),
	.o_detection(o_ecc_detection[12])
);
edc_hc_20_min_delay u_edc_hc_20_min_delay(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[266+:20]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[266+:20]),
	.o_detection(o_edc_hc_detection[13])
);
edc_ehc_20_min_delay u_edc_ehc_20_min_delay(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[286+:20]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[286+:20]),
	.o_detection(o_edc_ehc_detection[13])
);
ecc_ehc_20_min_delay u_ecc_ehc_20_min_delay(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[306+:20]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[306+:20]),
	.o_read_data(o_corrected_data[140+:20]),
	.o_correction(o_correction[13]),
	.o_detection(o_ecc_detection[13])
);
edc_hc_20_balanced u_edc_hc_20_balanced(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[326+:10]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[326+:10]),
	.o_detection(o_edc_hc_detection[14])
);
edc_ehc_20_balanced u_edc_ehc_20_balanced(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[336+:10]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[336+:10]),
	.o_detection(o_edc_ehc_detection[14])
);
ecc_ehc_20_balanced u_ecc_ehc_20_balanced(
	.i_write_data(i_dat[20-1:0]),
	.o_write_edc(edc_write[346+:10]),
	.i_stored_data(data_storage[20-1:0]),
	.i_stored_edc(edc_storage[346+:10]),
	.o_read_data(o_corrected_data[160+:20]),
	.o_correction(o_correction[14]),
	.o_detection(o_ecc_detection[14])
);
edc_hc_24_min_width u_edc_hc_24_min_width(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[356+:5]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[356+:5]),
	.o_detection(o_edc_hc_detection[15])
);
edc_ehc_24_min_width u_edc_ehc_24_min_width(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[361+:6]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[361+:6]),
	.o_detection(o_edc_ehc_detection[15])
);
ecc_ehc_24_min_width u_ecc_ehc_24_min_width(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[367+:6]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[367+:6]),
	.o_read_data(o_corrected_data[180+:24]),
	.o_correction(o_correction[15]),
	.o_detection(o_ecc_detection[15])
);
edc_hc_24_min_delay u_edc_hc_24_min_delay(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[373+:24]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[373+:24]),
	.o_detection(o_edc_hc_detection[16])
);
edc_ehc_24_min_delay u_edc_ehc_24_min_delay(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[397+:24]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[397+:24]),
	.o_detection(o_edc_ehc_detection[16])
);
ecc_ehc_24_min_delay u_ecc_ehc_24_min_delay(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[421+:24]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[421+:24]),
	.o_read_data(o_corrected_data[204+:24]),
	.o_correction(o_correction[16]),
	.o_detection(o_ecc_detection[16])
);
edc_hc_24_balanced u_edc_hc_24_balanced(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[445+:12]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[445+:12]),
	.o_detection(o_edc_hc_detection[17])
);
edc_ehc_24_balanced u_edc_ehc_24_balanced(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[457+:12]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[457+:12]),
	.o_detection(o_edc_ehc_detection[17])
);
ecc_ehc_24_balanced u_ecc_ehc_24_balanced(
	.i_write_data(i_dat[24-1:0]),
	.o_write_edc(edc_write[469+:12]),
	.i_stored_data(data_storage[24-1:0]),
	.i_stored_edc(edc_storage[469+:12]),
	.o_read_data(o_corrected_data[228+:24]),
	.o_correction(o_correction[17]),
	.o_detection(o_ecc_detection[17])
);
edc_hc_32_min_width u_edc_hc_32_min_width(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[481+:6]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[481+:6]),
	.o_detection(o_edc_hc_detection[18])
);
edc_ehc_32_min_width u_edc_ehc_32_min_width(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[487+:7]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[487+:7]),
	.o_detection(o_edc_ehc_detection[18])
);
ecc_ehc_32_min_width u_ecc_ehc_32_min_width(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[494+:7]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[494+:7]),
	.o_read_data(o_corrected_data[252+:32]),
	.o_correction(o_correction[18]),
	.o_detection(o_ecc_detection[18])
);
edc_hc_32_min_delay u_edc_hc_32_min_delay(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[501+:32]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[501+:32]),
	.o_detection(o_edc_hc_detection[19])
);
edc_ehc_32_min_delay u_edc_ehc_32_min_delay(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[533+:32]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[533+:32]),
	.o_detection(o_edc_ehc_detection[19])
);
ecc_ehc_32_min_delay u_ecc_ehc_32_min_delay(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[565+:32]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[565+:32]),
	.o_read_data(o_corrected_data[284+:32]),
	.o_correction(o_correction[19]),
	.o_detection(o_ecc_detection[19])
);
edc_hc_32_balanced u_edc_hc_32_balanced(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[597+:16]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[597+:16]),
	.o_detection(o_edc_hc_detection[20])
);
edc_ehc_32_balanced u_edc_ehc_32_balanced(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[613+:16]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[613+:16]),
	.o_detection(o_edc_ehc_detection[20])
);
ecc_ehc_32_balanced u_ecc_ehc_32_balanced(
	.i_write_data(i_dat[32-1:0]),
	.o_write_edc(edc_write[629+:16]),
	.i_stored_data(data_storage[32-1:0]),
	.i_stored_edc(edc_storage[629+:16]),
	.o_read_data(o_corrected_data[316+:32]),
	.o_correction(o_correction[20]),
	.o_detection(o_ecc_detection[20])
);
edc_hc_36_min_width u_edc_hc_36_min_width(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[645+:6]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[645+:6]),
	.o_detection(o_edc_hc_detection[21])
);
edc_ehc_36_min_width u_edc_ehc_36_min_width(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[651+:7]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[651+:7]),
	.o_detection(o_edc_ehc_detection[21])
);
ecc_ehc_36_min_width u_ecc_ehc_36_min_width(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[658+:7]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[658+:7]),
	.o_read_data(o_corrected_data[348+:36]),
	.o_correction(o_correction[21]),
	.o_detection(o_ecc_detection[21])
);
edc_hc_36_min_delay u_edc_hc_36_min_delay(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[665+:36]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[665+:36]),
	.o_detection(o_edc_hc_detection[22])
);
edc_ehc_36_min_delay u_edc_ehc_36_min_delay(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[701+:36]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[701+:36]),
	.o_detection(o_edc_ehc_detection[22])
);
ecc_ehc_36_min_delay u_ecc_ehc_36_min_delay(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[737+:36]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[737+:36]),
	.o_read_data(o_corrected_data[384+:36]),
	.o_correction(o_correction[22]),
	.o_detection(o_ecc_detection[22])
);
edc_hc_36_balanced u_edc_hc_36_balanced(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[773+:18]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[773+:18]),
	.o_detection(o_edc_hc_detection[23])
);
edc_ehc_36_balanced u_edc_ehc_36_balanced(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[791+:18]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[791+:18]),
	.o_detection(o_edc_ehc_detection[23])
);
ecc_ehc_36_balanced u_ecc_ehc_36_balanced(
	.i_write_data(i_dat[36-1:0]),
	.o_write_edc(edc_write[809+:18]),
	.i_stored_data(data_storage[36-1:0]),
	.i_stored_edc(edc_storage[809+:18]),
	.o_read_data(o_corrected_data[420+:36]),
	.o_correction(o_correction[23]),
	.o_detection(o_ecc_detection[23])
);
edc_hc_40_min_width u_edc_hc_40_min_width(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[827+:6]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[827+:6]),
	.o_detection(o_edc_hc_detection[24])
);
edc_ehc_40_min_width u_edc_ehc_40_min_width(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[833+:7]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[833+:7]),
	.o_detection(o_edc_ehc_detection[24])
);
ecc_ehc_40_min_width u_ecc_ehc_40_min_width(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[840+:7]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[840+:7]),
	.o_read_data(o_corrected_data[456+:40]),
	.o_correction(o_correction[24]),
	.o_detection(o_ecc_detection[24])
);
edc_hc_40_min_delay u_edc_hc_40_min_delay(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[847+:40]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[847+:40]),
	.o_detection(o_edc_hc_detection[25])
);
edc_ehc_40_min_delay u_edc_ehc_40_min_delay(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[887+:40]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[887+:40]),
	.o_detection(o_edc_ehc_detection[25])
);
ecc_ehc_40_min_delay u_ecc_ehc_40_min_delay(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[927+:40]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[927+:40]),
	.o_read_data(o_corrected_data[496+:40]),
	.o_correction(o_correction[25]),
	.o_detection(o_ecc_detection[25])
);
edc_hc_40_balanced u_edc_hc_40_balanced(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[967+:20]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[967+:20]),
	.o_detection(o_edc_hc_detection[26])
);
edc_ehc_40_balanced u_edc_ehc_40_balanced(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[987+:20]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[987+:20]),
	.o_detection(o_edc_ehc_detection[26])
);
ecc_ehc_40_balanced u_ecc_ehc_40_balanced(
	.i_write_data(i_dat[40-1:0]),
	.o_write_edc(edc_write[1007+:20]),
	.i_stored_data(data_storage[40-1:0]),
	.i_stored_edc(edc_storage[1007+:20]),
	.o_read_data(o_corrected_data[536+:40]),
	.o_correction(o_correction[26]),
	.o_detection(o_ecc_detection[26])
);
edc_hc_48_min_width u_edc_hc_48_min_width(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1027+:6]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1027+:6]),
	.o_detection(o_edc_hc_detection[27])
);
edc_ehc_48_min_width u_edc_ehc_48_min_width(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1033+:7]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1033+:7]),
	.o_detection(o_edc_ehc_detection[27])
);
ecc_ehc_48_min_width u_ecc_ehc_48_min_width(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1040+:7]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1040+:7]),
	.o_read_data(o_corrected_data[576+:48]),
	.o_correction(o_correction[27]),
	.o_detection(o_ecc_detection[27])
);
edc_hc_48_min_delay u_edc_hc_48_min_delay(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1047+:48]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1047+:48]),
	.o_detection(o_edc_hc_detection[28])
);
edc_ehc_48_min_delay u_edc_ehc_48_min_delay(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1095+:48]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1095+:48]),
	.o_detection(o_edc_ehc_detection[28])
);
ecc_ehc_48_min_delay u_ecc_ehc_48_min_delay(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1143+:48]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1143+:48]),
	.o_read_data(o_corrected_data[624+:48]),
	.o_correction(o_correction[28]),
	.o_detection(o_ecc_detection[28])
);
edc_hc_48_balanced u_edc_hc_48_balanced(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1191+:24]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1191+:24]),
	.o_detection(o_edc_hc_detection[29])
);
edc_ehc_48_balanced u_edc_ehc_48_balanced(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1215+:24]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1215+:24]),
	.o_detection(o_edc_ehc_detection[29])
);
ecc_ehc_48_balanced u_ecc_ehc_48_balanced(
	.i_write_data(i_dat[48-1:0]),
	.o_write_edc(edc_write[1239+:24]),
	.i_stored_data(data_storage[48-1:0]),
	.i_stored_edc(edc_storage[1239+:24]),
	.o_read_data(o_corrected_data[672+:48]),
	.o_correction(o_correction[29]),
	.o_detection(o_ecc_detection[29])
);
edc_hc_64_min_width u_edc_hc_64_min_width(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1263+:7]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1263+:7]),
	.o_detection(o_edc_hc_detection[30])
);
edc_ehc_64_min_width u_edc_ehc_64_min_width(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1270+:8]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1270+:8]),
	.o_detection(o_edc_ehc_detection[30])
);
ecc_ehc_64_min_width u_ecc_ehc_64_min_width(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1278+:8]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1278+:8]),
	.o_read_data(o_corrected_data[720+:64]),
	.o_correction(o_correction[30]),
	.o_detection(o_ecc_detection[30])
);
edc_hc_64_min_delay u_edc_hc_64_min_delay(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1286+:64]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1286+:64]),
	.o_detection(o_edc_hc_detection[31])
);
edc_ehc_64_min_delay u_edc_ehc_64_min_delay(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1350+:64]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1350+:64]),
	.o_detection(o_edc_ehc_detection[31])
);
ecc_ehc_64_min_delay u_ecc_ehc_64_min_delay(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1414+:64]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1414+:64]),
	.o_read_data(o_corrected_data[784+:64]),
	.o_correction(o_correction[31]),
	.o_detection(o_ecc_detection[31])
);
edc_hc_64_balanced u_edc_hc_64_balanced(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1478+:32]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1478+:32]),
	.o_detection(o_edc_hc_detection[32])
);
edc_ehc_64_balanced u_edc_ehc_64_balanced(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1510+:32]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1510+:32]),
	.o_detection(o_edc_ehc_detection[32])
);
ecc_ehc_64_balanced u_ecc_ehc_64_balanced(
	.i_write_data(i_dat[64-1:0]),
	.o_write_edc(edc_write[1542+:32]),
	.i_stored_data(data_storage[64-1:0]),
	.i_stored_edc(edc_storage[1542+:32]),
	.o_read_data(o_corrected_data[848+:64]),
	.o_correction(o_correction[32]),
	.o_detection(o_ecc_detection[32])
);
edc_hc_72_min_width u_edc_hc_72_min_width(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1574+:7]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1574+:7]),
	.o_detection(o_edc_hc_detection[33])
);
edc_ehc_72_min_width u_edc_ehc_72_min_width(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1581+:8]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1581+:8]),
	.o_detection(o_edc_ehc_detection[33])
);
ecc_ehc_72_min_width u_ecc_ehc_72_min_width(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1589+:8]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1589+:8]),
	.o_read_data(o_corrected_data[912+:72]),
	.o_correction(o_correction[33]),
	.o_detection(o_ecc_detection[33])
);
edc_hc_72_min_delay u_edc_hc_72_min_delay(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1597+:72]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1597+:72]),
	.o_detection(o_edc_hc_detection[34])
);
edc_ehc_72_min_delay u_edc_ehc_72_min_delay(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1669+:72]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1669+:72]),
	.o_detection(o_edc_ehc_detection[34])
);
ecc_ehc_72_min_delay u_ecc_ehc_72_min_delay(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1741+:72]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1741+:72]),
	.o_read_data(o_corrected_data[984+:72]),
	.o_correction(o_correction[34]),
	.o_detection(o_ecc_detection[34])
);
edc_hc_72_balanced u_edc_hc_72_balanced(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1813+:36]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1813+:36]),
	.o_detection(o_edc_hc_detection[35])
);
edc_ehc_72_balanced u_edc_ehc_72_balanced(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1849+:36]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1849+:36]),
	.o_detection(o_edc_ehc_detection[35])
);
ecc_ehc_72_balanced u_ecc_ehc_72_balanced(
	.i_write_data(i_dat[72-1:0]),
	.o_write_edc(edc_write[1885+:36]),
	.i_stored_data(data_storage[72-1:0]),
	.i_stored_edc(edc_storage[1885+:36]),
	.o_read_data(o_corrected_data[1056+:72]),
	.o_correction(o_correction[35]),
	.o_detection(o_ecc_detection[35])
);
edc_hc_80_min_width u_edc_hc_80_min_width(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[1921+:7]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[1921+:7]),
	.o_detection(o_edc_hc_detection[36])
);
edc_ehc_80_min_width u_edc_ehc_80_min_width(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[1928+:8]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[1928+:8]),
	.o_detection(o_edc_ehc_detection[36])
);
ecc_ehc_80_min_width u_ecc_ehc_80_min_width(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[1936+:8]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[1936+:8]),
	.o_read_data(o_corrected_data[1128+:80]),
	.o_correction(o_correction[36]),
	.o_detection(o_ecc_detection[36])
);
edc_hc_80_min_delay u_edc_hc_80_min_delay(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[1944+:80]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[1944+:80]),
	.o_detection(o_edc_hc_detection[37])
);
edc_ehc_80_min_delay u_edc_ehc_80_min_delay(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[2024+:80]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[2024+:80]),
	.o_detection(o_edc_ehc_detection[37])
);
ecc_ehc_80_min_delay u_ecc_ehc_80_min_delay(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[2104+:80]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[2104+:80]),
	.o_read_data(o_corrected_data[1208+:80]),
	.o_correction(o_correction[37]),
	.o_detection(o_ecc_detection[37])
);
edc_hc_80_balanced u_edc_hc_80_balanced(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[2184+:40]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[2184+:40]),
	.o_detection(o_edc_hc_detection[38])
);
edc_ehc_80_balanced u_edc_ehc_80_balanced(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[2224+:40]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[2224+:40]),
	.o_detection(o_edc_ehc_detection[38])
);
ecc_ehc_80_balanced u_ecc_ehc_80_balanced(
	.i_write_data(i_dat[80-1:0]),
	.o_write_edc(edc_write[2264+:40]),
	.i_stored_data(data_storage[80-1:0]),
	.i_stored_edc(edc_storage[2264+:40]),
	.o_read_data(o_corrected_data[1288+:80]),
	.o_correction(o_correction[38]),
	.o_detection(o_ecc_detection[38])
);
edc_hc_88_min_width u_edc_hc_88_min_width(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2304+:7]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2304+:7]),
	.o_detection(o_edc_hc_detection[39])
);
edc_ehc_88_min_width u_edc_ehc_88_min_width(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2311+:8]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2311+:8]),
	.o_detection(o_edc_ehc_detection[39])
);
ecc_ehc_88_min_width u_ecc_ehc_88_min_width(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2319+:8]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2319+:8]),
	.o_read_data(o_corrected_data[1368+:88]),
	.o_correction(o_correction[39]),
	.o_detection(o_ecc_detection[39])
);
edc_hc_88_min_delay u_edc_hc_88_min_delay(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2327+:88]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2327+:88]),
	.o_detection(o_edc_hc_detection[40])
);
edc_ehc_88_min_delay u_edc_ehc_88_min_delay(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2415+:88]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2415+:88]),
	.o_detection(o_edc_ehc_detection[40])
);
ecc_ehc_88_min_delay u_ecc_ehc_88_min_delay(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2503+:88]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2503+:88]),
	.o_read_data(o_corrected_data[1456+:88]),
	.o_correction(o_correction[40]),
	.o_detection(o_ecc_detection[40])
);
edc_hc_88_balanced u_edc_hc_88_balanced(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2591+:44]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2591+:44]),
	.o_detection(o_edc_hc_detection[41])
);
edc_ehc_88_balanced u_edc_ehc_88_balanced(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2635+:44]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2635+:44]),
	.o_detection(o_edc_ehc_detection[41])
);
ecc_ehc_88_balanced u_ecc_ehc_88_balanced(
	.i_write_data(i_dat[88-1:0]),
	.o_write_edc(edc_write[2679+:44]),
	.i_stored_data(data_storage[88-1:0]),
	.i_stored_edc(edc_storage[2679+:44]),
	.o_read_data(o_corrected_data[1544+:88]),
	.o_correction(o_correction[41]),
	.o_detection(o_ecc_detection[41])
);
edc_hc_92_min_width u_edc_hc_92_min_width(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[2723+:7]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[2723+:7]),
	.o_detection(o_edc_hc_detection[42])
);
edc_ehc_92_min_width u_edc_ehc_92_min_width(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[2730+:8]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[2730+:8]),
	.o_detection(o_edc_ehc_detection[42])
);
ecc_ehc_92_min_width u_ecc_ehc_92_min_width(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[2738+:8]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[2738+:8]),
	.o_read_data(o_corrected_data[1632+:92]),
	.o_correction(o_correction[42]),
	.o_detection(o_ecc_detection[42])
);
edc_hc_92_min_delay u_edc_hc_92_min_delay(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[2746+:92]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[2746+:92]),
	.o_detection(o_edc_hc_detection[43])
);
edc_ehc_92_min_delay u_edc_ehc_92_min_delay(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[2838+:92]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[2838+:92]),
	.o_detection(o_edc_ehc_detection[43])
);
ecc_ehc_92_min_delay u_ecc_ehc_92_min_delay(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[2930+:92]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[2930+:92]),
	.o_read_data(o_corrected_data[1724+:92]),
	.o_correction(o_correction[43]),
	.o_detection(o_ecc_detection[43])
);
edc_hc_92_balanced u_edc_hc_92_balanced(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[3022+:46]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[3022+:46]),
	.o_detection(o_edc_hc_detection[44])
);
edc_ehc_92_balanced u_edc_ehc_92_balanced(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[3068+:46]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[3068+:46]),
	.o_detection(o_edc_ehc_detection[44])
);
ecc_ehc_92_balanced u_ecc_ehc_92_balanced(
	.i_write_data(i_dat[92-1:0]),
	.o_write_edc(edc_write[3114+:46]),
	.i_stored_data(data_storage[92-1:0]),
	.i_stored_edc(edc_storage[3114+:46]),
	.o_read_data(o_corrected_data[1816+:92]),
	.o_correction(o_correction[44]),
	.o_detection(o_ecc_detection[44])
);
edc_hc_96_min_width u_edc_hc_96_min_width(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3160+:7]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3160+:7]),
	.o_detection(o_edc_hc_detection[45])
);
edc_ehc_96_min_width u_edc_ehc_96_min_width(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3167+:8]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3167+:8]),
	.o_detection(o_edc_ehc_detection[45])
);
ecc_ehc_96_min_width u_ecc_ehc_96_min_width(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3175+:8]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3175+:8]),
	.o_read_data(o_corrected_data[1908+:96]),
	.o_correction(o_correction[45]),
	.o_detection(o_ecc_detection[45])
);
edc_hc_96_min_delay u_edc_hc_96_min_delay(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3183+:96]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3183+:96]),
	.o_detection(o_edc_hc_detection[46])
);
edc_ehc_96_min_delay u_edc_ehc_96_min_delay(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3279+:96]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3279+:96]),
	.o_detection(o_edc_ehc_detection[46])
);
ecc_ehc_96_min_delay u_ecc_ehc_96_min_delay(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3375+:96]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3375+:96]),
	.o_read_data(o_corrected_data[2004+:96]),
	.o_correction(o_correction[46]),
	.o_detection(o_ecc_detection[46])
);
edc_hc_96_balanced u_edc_hc_96_balanced(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3471+:48]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3471+:48]),
	.o_detection(o_edc_hc_detection[47])
);
edc_ehc_96_balanced u_edc_ehc_96_balanced(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3519+:48]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3519+:48]),
	.o_detection(o_edc_ehc_detection[47])
);
ecc_ehc_96_balanced u_ecc_ehc_96_balanced(
	.i_write_data(i_dat[96-1:0]),
	.o_write_edc(edc_write[3567+:48]),
	.i_stored_data(data_storage[96-1:0]),
	.i_stored_edc(edc_storage[3567+:48]),
	.o_read_data(o_corrected_data[2100+:96]),
	.o_correction(o_correction[47]),
	.o_detection(o_ecc_detection[47])
);
edc_hc_110_min_width u_edc_hc_110_min_width(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3615+:7]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3615+:7]),
	.o_detection(o_edc_hc_detection[48])
);
edc_ehc_110_min_width u_edc_ehc_110_min_width(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3622+:8]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3622+:8]),
	.o_detection(o_edc_ehc_detection[48])
);
ecc_ehc_110_min_width u_ecc_ehc_110_min_width(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3630+:8]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3630+:8]),
	.o_read_data(o_corrected_data[2196+:110]),
	.o_correction(o_correction[48]),
	.o_detection(o_ecc_detection[48])
);
edc_hc_110_min_delay u_edc_hc_110_min_delay(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3638+:110]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3638+:110]),
	.o_detection(o_edc_hc_detection[49])
);
edc_ehc_110_min_delay u_edc_ehc_110_min_delay(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3748+:110]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3748+:110]),
	.o_detection(o_edc_ehc_detection[49])
);
ecc_ehc_110_min_delay u_ecc_ehc_110_min_delay(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3858+:110]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3858+:110]),
	.o_read_data(o_corrected_data[2306+:110]),
	.o_correction(o_correction[49]),
	.o_detection(o_ecc_detection[49])
);
edc_hc_110_balanced u_edc_hc_110_balanced(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[3968+:55]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[3968+:55]),
	.o_detection(o_edc_hc_detection[50])
);
edc_ehc_110_balanced u_edc_ehc_110_balanced(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[4023+:55]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[4023+:55]),
	.o_detection(o_edc_ehc_detection[50])
);
ecc_ehc_110_balanced u_ecc_ehc_110_balanced(
	.i_write_data(i_dat[110-1:0]),
	.o_write_edc(edc_write[4078+:55]),
	.i_stored_data(data_storage[110-1:0]),
	.i_stored_edc(edc_storage[4078+:55]),
	.o_read_data(o_corrected_data[2416+:110]),
	.o_correction(o_correction[50]),
	.o_detection(o_ecc_detection[50])
);
edc_hc_120_min_width u_edc_hc_120_min_width(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4133+:7]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4133+:7]),
	.o_detection(o_edc_hc_detection[51])
);
edc_ehc_120_min_width u_edc_ehc_120_min_width(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4140+:8]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4140+:8]),
	.o_detection(o_edc_ehc_detection[51])
);
ecc_ehc_120_min_width u_ecc_ehc_120_min_width(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4148+:8]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4148+:8]),
	.o_read_data(o_corrected_data[2526+:120]),
	.o_correction(o_correction[51]),
	.o_detection(o_ecc_detection[51])
);
edc_hc_120_min_delay u_edc_hc_120_min_delay(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4156+:120]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4156+:120]),
	.o_detection(o_edc_hc_detection[52])
);
edc_ehc_120_min_delay u_edc_ehc_120_min_delay(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4276+:120]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4276+:120]),
	.o_detection(o_edc_ehc_detection[52])
);
ecc_ehc_120_min_delay u_ecc_ehc_120_min_delay(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4396+:120]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4396+:120]),
	.o_read_data(o_corrected_data[2646+:120]),
	.o_correction(o_correction[52]),
	.o_detection(o_ecc_detection[52])
);
edc_hc_120_balanced u_edc_hc_120_balanced(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4516+:60]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4516+:60]),
	.o_detection(o_edc_hc_detection[53])
);
edc_ehc_120_balanced u_edc_ehc_120_balanced(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4576+:60]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4576+:60]),
	.o_detection(o_edc_ehc_detection[53])
);
ecc_ehc_120_balanced u_ecc_ehc_120_balanced(
	.i_write_data(i_dat[120-1:0]),
	.o_write_edc(edc_write[4636+:60]),
	.i_stored_data(data_storage[120-1:0]),
	.i_stored_edc(edc_storage[4636+:60]),
	.o_read_data(o_corrected_data[2766+:120]),
	.o_correction(o_correction[53]),
	.o_detection(o_ecc_detection[53])
);
edc_hc_128_min_width u_edc_hc_128_min_width(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[4696+:8]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[4696+:8]),
	.o_detection(o_edc_hc_detection[54])
);
edc_ehc_128_min_width u_edc_ehc_128_min_width(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[4704+:9]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[4704+:9]),
	.o_detection(o_edc_ehc_detection[54])
);
ecc_ehc_128_min_width u_ecc_ehc_128_min_width(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[4713+:9]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[4713+:9]),
	.o_read_data(o_corrected_data[2886+:128]),
	.o_correction(o_correction[54]),
	.o_detection(o_ecc_detection[54])
);
edc_hc_128_min_delay u_edc_hc_128_min_delay(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[4722+:128]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[4722+:128]),
	.o_detection(o_edc_hc_detection[55])
);
edc_ehc_128_min_delay u_edc_ehc_128_min_delay(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[4850+:128]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[4850+:128]),
	.o_detection(o_edc_ehc_detection[55])
);
ecc_ehc_128_min_delay u_ecc_ehc_128_min_delay(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[4978+:128]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[4978+:128]),
	.o_read_data(o_corrected_data[3014+:128]),
	.o_correction(o_correction[55]),
	.o_detection(o_ecc_detection[55])
);
edc_hc_128_balanced u_edc_hc_128_balanced(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[5106+:64]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[5106+:64]),
	.o_detection(o_edc_hc_detection[56])
);
edc_ehc_128_balanced u_edc_ehc_128_balanced(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[5170+:64]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[5170+:64]),
	.o_detection(o_edc_ehc_detection[56])
);
ecc_ehc_128_balanced u_ecc_ehc_128_balanced(
	.i_write_data(i_dat[128-1:0]),
	.o_write_edc(edc_write[5234+:64]),
	.i_stored_data(data_storage[128-1:0]),
	.i_stored_edc(edc_storage[5234+:64]),
	.o_read_data(o_corrected_data[3142+:128]),
	.o_correction(o_correction[56]),
	.o_detection(o_ecc_detection[56])
);
edc_hc_136_min_width u_edc_hc_136_min_width(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5298+:8]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5298+:8]),
	.o_detection(o_edc_hc_detection[57])
);
edc_ehc_136_min_width u_edc_ehc_136_min_width(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5306+:9]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5306+:9]),
	.o_detection(o_edc_ehc_detection[57])
);
ecc_ehc_136_min_width u_ecc_ehc_136_min_width(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5315+:9]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5315+:9]),
	.o_read_data(o_corrected_data[3270+:136]),
	.o_correction(o_correction[57]),
	.o_detection(o_ecc_detection[57])
);
edc_hc_136_min_delay u_edc_hc_136_min_delay(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5324+:136]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5324+:136]),
	.o_detection(o_edc_hc_detection[58])
);
edc_ehc_136_min_delay u_edc_ehc_136_min_delay(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5460+:136]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5460+:136]),
	.o_detection(o_edc_ehc_detection[58])
);
ecc_ehc_136_min_delay u_ecc_ehc_136_min_delay(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5596+:136]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5596+:136]),
	.o_read_data(o_corrected_data[3406+:136]),
	.o_correction(o_correction[58]),
	.o_detection(o_ecc_detection[58])
);
edc_hc_136_balanced u_edc_hc_136_balanced(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5732+:68]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5732+:68]),
	.o_detection(o_edc_hc_detection[59])
);
edc_ehc_136_balanced u_edc_ehc_136_balanced(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5800+:68]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5800+:68]),
	.o_detection(o_edc_ehc_detection[59])
);
ecc_ehc_136_balanced u_ecc_ehc_136_balanced(
	.i_write_data(i_dat[136-1:0]),
	.o_write_edc(edc_write[5868+:68]),
	.i_stored_data(data_storage[136-1:0]),
	.i_stored_edc(edc_storage[5868+:68]),
	.o_read_data(o_corrected_data[3542+:136]),
	.o_correction(o_correction[59]),
	.o_detection(o_ecc_detection[59])
);
edc_hc_192_min_width u_edc_hc_192_min_width(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[5936+:8]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[5936+:8]),
	.o_detection(o_edc_hc_detection[60])
);
edc_ehc_192_min_width u_edc_ehc_192_min_width(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[5944+:9]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[5944+:9]),
	.o_detection(o_edc_ehc_detection[60])
);
ecc_ehc_192_min_width u_ecc_ehc_192_min_width(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[5953+:9]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[5953+:9]),
	.o_read_data(o_corrected_data[3678+:192]),
	.o_correction(o_correction[60]),
	.o_detection(o_ecc_detection[60])
);
edc_hc_192_min_delay u_edc_hc_192_min_delay(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[5962+:192]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[5962+:192]),
	.o_detection(o_edc_hc_detection[61])
);
edc_ehc_192_min_delay u_edc_ehc_192_min_delay(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[6154+:192]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[6154+:192]),
	.o_detection(o_edc_ehc_detection[61])
);
ecc_ehc_192_min_delay u_ecc_ehc_192_min_delay(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[6346+:192]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[6346+:192]),
	.o_read_data(o_corrected_data[3870+:192]),
	.o_correction(o_correction[61]),
	.o_detection(o_ecc_detection[61])
);
edc_hc_192_balanced u_edc_hc_192_balanced(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[6538+:96]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[6538+:96]),
	.o_detection(o_edc_hc_detection[62])
);
edc_ehc_192_balanced u_edc_ehc_192_balanced(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[6634+:96]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[6634+:96]),
	.o_detection(o_edc_ehc_detection[62])
);
ecc_ehc_192_balanced u_ecc_ehc_192_balanced(
	.i_write_data(i_dat[192-1:0]),
	.o_write_edc(edc_write[6730+:96]),
	.i_stored_data(data_storage[192-1:0]),
	.i_stored_edc(edc_storage[6730+:96]),
	.o_read_data(o_corrected_data[4062+:192]),
	.o_correction(o_correction[62]),
	.o_detection(o_ecc_detection[62])
);
edc_hc_256_min_width u_edc_hc_256_min_width(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[6826+:9]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[6826+:9]),
	.o_detection(o_edc_hc_detection[63])
);
edc_ehc_256_min_width u_edc_ehc_256_min_width(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[6835+:10]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[6835+:10]),
	.o_detection(o_edc_ehc_detection[63])
);
ecc_ehc_256_min_width u_ecc_ehc_256_min_width(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[6845+:10]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[6845+:10]),
	.o_read_data(o_corrected_data[4254+:256]),
	.o_correction(o_correction[63]),
	.o_detection(o_ecc_detection[63])
);
edc_hc_256_min_delay u_edc_hc_256_min_delay(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[6855+:256]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[6855+:256]),
	.o_detection(o_edc_hc_detection[64])
);
edc_ehc_256_min_delay u_edc_ehc_256_min_delay(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[7111+:256]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[7111+:256]),
	.o_detection(o_edc_ehc_detection[64])
);
ecc_ehc_256_min_delay u_ecc_ehc_256_min_delay(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[7367+:256]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[7367+:256]),
	.o_read_data(o_corrected_data[4510+:256]),
	.o_correction(o_correction[64]),
	.o_detection(o_ecc_detection[64])
);
edc_hc_256_balanced u_edc_hc_256_balanced(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[7623+:128]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[7623+:128]),
	.o_detection(o_edc_hc_detection[65])
);
edc_ehc_256_balanced u_edc_ehc_256_balanced(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[7751+:128]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[7751+:128]),
	.o_detection(o_edc_ehc_detection[65])
);
ecc_ehc_256_balanced u_ecc_ehc_256_balanced(
	.i_write_data(i_dat[256-1:0]),
	.o_write_edc(edc_write[7879+:128]),
	.i_stored_data(data_storage[256-1:0]),
	.i_stored_edc(edc_storage[7879+:128]),
	.o_read_data(o_corrected_data[4766+:256]),
	.o_correction(o_correction[65]),
	.o_detection(o_ecc_detection[65])
);
edc_hc_384_min_width u_edc_hc_384_min_width(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[8007+:9]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[8007+:9]),
	.o_detection(o_edc_hc_detection[66])
);
edc_ehc_384_min_width u_edc_ehc_384_min_width(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[8016+:10]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[8016+:10]),
	.o_detection(o_edc_ehc_detection[66])
);
ecc_ehc_384_min_width u_ecc_ehc_384_min_width(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[8026+:10]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[8026+:10]),
	.o_read_data(o_corrected_data[5022+:384]),
	.o_correction(o_correction[66]),
	.o_detection(o_ecc_detection[66])
);
edc_hc_384_min_delay u_edc_hc_384_min_delay(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[8036+:384]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[8036+:384]),
	.o_detection(o_edc_hc_detection[67])
);
edc_ehc_384_min_delay u_edc_ehc_384_min_delay(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[8420+:384]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[8420+:384]),
	.o_detection(o_edc_ehc_detection[67])
);
ecc_ehc_384_min_delay u_ecc_ehc_384_min_delay(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[8804+:384]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[8804+:384]),
	.o_read_data(o_corrected_data[5406+:384]),
	.o_correction(o_correction[67]),
	.o_detection(o_ecc_detection[67])
);
edc_hc_384_balanced u_edc_hc_384_balanced(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[9188+:192]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[9188+:192]),
	.o_detection(o_edc_hc_detection[68])
);
edc_ehc_384_balanced u_edc_ehc_384_balanced(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[9380+:192]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[9380+:192]),
	.o_detection(o_edc_ehc_detection[68])
);
ecc_ehc_384_balanced u_ecc_ehc_384_balanced(
	.i_write_data(i_dat[384-1:0]),
	.o_write_edc(edc_write[9572+:192]),
	.i_stored_data(data_storage[384-1:0]),
	.i_stored_edc(edc_storage[9572+:192]),
	.o_read_data(o_corrected_data[5790+:384]),
	.o_correction(o_correction[68]),
	.o_detection(o_ecc_detection[68])
);
endmodule
