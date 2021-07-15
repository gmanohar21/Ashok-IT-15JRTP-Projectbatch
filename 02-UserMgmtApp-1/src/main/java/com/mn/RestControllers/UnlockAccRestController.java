package com.mn.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mn.domain.UnlockAccForm;
import com.mn.service.IUserService;

@RestController
public class UnlockAccRestController {
	@Autowired
	private IUserService service;

	@PostMapping("/unlock")
	public String UnlockAcc(@RequestBody UnlockAccForm unlockAccForm) {
		boolean status = service.UnlockAcc(unlockAccForm);
		if (status) {
			return "Account Unlocked Successfully";
		} else {
			return "Incorrect Temp Pwd";
		}
	}
}
