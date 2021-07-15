package com.mn.service;

import java.util.Map;

import com.mn.domain.LoginForm;
import com.mn.domain.RegForm;
import com.mn.domain.UnlockAccForm;

public interface IUserService {

	public String logincheck(LoginForm loginform);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public boolean Saveusr(RegForm regform);

	public String emailCheck(String emailId);

	public boolean UnlockAcc(UnlockAccForm unlockform);

	public boolean frgtPwd(String emailId);
}
