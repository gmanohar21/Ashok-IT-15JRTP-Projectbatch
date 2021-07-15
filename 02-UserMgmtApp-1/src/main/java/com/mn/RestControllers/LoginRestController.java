package com.mn.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mn.domain.LoginForm;
import com.mn.service.IUserService;

@RestController
public class LoginRestController {
	@Autowired
	private IUserService user;

	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginform) {
		String loginCheck = user.logincheck(loginform);
		return loginCheck;
	}
}
