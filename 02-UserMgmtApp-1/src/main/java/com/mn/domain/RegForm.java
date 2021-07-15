package com.mn.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegForm {
	private String countryId;
	private String stateId;
	private String cityId;
	private LocalDate dob;
	private String fname;
	private String lname;
	private String email;
	private Long phno;
	private String gender;
	
}
