package com.mn.domain;

import lombok.Data;

@Data
public class UnlockAccForm {
	private String email;
	private String temppwd;
	private String choosenpwd;
	private String confirmpwd;
}
