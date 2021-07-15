package com.mn.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mn.service.IUserService;

@RestController
public class ForgotPwdRestController {
	@Autowired
	public IUserService service;

	@GetMapping("/forgotpwd")
	public String forgotPwd(@PathVariable String email) {
		boolean status = service.frgtPwd(email);
		if (status) {
			return "Check Email for password";
		} else {
			return "	pls Enter valid Details";
		}

	}
}
