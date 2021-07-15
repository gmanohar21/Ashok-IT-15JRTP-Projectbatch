package com.mn.RestControllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mn.constants.AppConstants;
import com.mn.domain.RegForm;
import com.mn.properties.AppProps;
import com.mn.service.IUserService;
import com.mn.service.UserServiceImpl;

@RestController
public class RegRestController {
		@Autowired
		private UserServiceImpl userService;
		@Autowired
		private AppProps props;
	
		@GetMapping("/countries")
		public Map<Integer, String> getCountries() {
			return userService.getCountries();
		}
	
		@GetMapping("/states/{countryId}")
		public Map<Integer, String> getStates(@PathVariable Integer countryId) {
			return userService.getStates(countryId);
		}
	
		@GetMapping("/city/{stateId}")
		public Map<Integer, String> getCity(Integer stateId) {
			return userService.getCities(stateId);
		}
	
		@GetMapping("emailcheck/{emailId}")
		public String uniqueEmailCheck(@PathVariable String emailId) {
			String status = userService.emailCheck(emailId);
			return status;
	
		}
	
		@PostMapping("/saveUsr")
		public String saveUser(@RequestBody RegForm regform) {
			boolean saveUser = userService.Saveusr(regform);
			if (saveUser) {
				return props.getMessage().get(AppConstants.REG_SUCC);
			} else {
				return props.getMessage().get(AppConstants.REG_FAIL);
			}
		}
}
