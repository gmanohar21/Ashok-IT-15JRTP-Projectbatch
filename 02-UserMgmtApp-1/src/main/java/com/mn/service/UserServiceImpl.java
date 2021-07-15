package com.mn.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.mn.constants.AppConstants;
import com.mn.domain.LoginForm;
import com.mn.domain.RegForm;
import com.mn.domain.UnlockAccForm;
import com.mn.entity.City;
import com.mn.entity.Country;
import com.mn.entity.State;
import com.mn.entity.User;
import com.mn.properties.AppProps;
import com.mn.repository.CityRepo;
import com.mn.repository.CountryRepo;
import com.mn.repository.StateRepo;
import com.mn.repository.UserRepo;
import com.mn.util.EmailUtils;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	public CountryRepo countryrepo;
	@Autowired
	private StateRepo staterepo;
	@Autowired
	private CityRepo cityrepo;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private AppProps props;

	@Autowired
	private EmailUtils utils;

	@Override
	public String logincheck(LoginForm loginform) {
		String msg;
		User userCheck = userrepo.findByemailAndPwd(loginform.getEmail(), loginform.getUserPwd());
		if (userCheck != null) {
			String accStatus = userCheck.getAccStatus();

			if (AppConstants.ACCLOCK_MSG.equals(accStatus)) {
				msg = props.getMessage().get(AppConstants.ACCLOCK_MSG);

			} else {
				msg = AppConstants.SUCCESS_MSG;
			}
		} else {
			msg = props.getMessage().get(AppConstants.ACCINVALID_MSG);
		}
		return msg;
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<Country> countries = countryrepo.findAll();
		Map<Integer, String> countrymap = new HashMap<Integer, String>();
		countries.forEach(Country -> {
			countrymap.put(Country.getCountryId(), Country.getCountryName());
		});
		return countrymap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<State> states = staterepo.findBycountryId(countryId);
		Map<Integer, String> statesmap = new HashMap<Integer, String>();
		states.forEach(State -> {
			statesmap.put(State.getCountryId(), State.getStateName());
		});
		return statesmap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<City> cities = cityrepo.findBystateId(stateId);
		Map<Integer, String> citymap = new HashMap();
		cities.forEach(City -> {
			citymap.put(City.getStateId(), City.getCityName());
		});
		return citymap;
	}

	@Override
	public String emailCheck(String emailId) {
		User user = new User();
		//trim is used to remove space after email.
		user.setEmail(emailId.trim());
		//find by with example
		Example<User> example = Example.of(user);
		Optional<User> findOne = userrepo.findOne(example);

		if (findOne.isPresent()) {
			return AppConstants.DUPLICATE_MSG;
		} else {
			return AppConstants.UNIQUE_MSG;
		}
	}

	@Override
	public boolean Saveusr(RegForm regform) {
		User user = new User();
		//which contains data is called source 
		BeanUtils.copyProperties(regform, user);
		user.setAccStatus(AppConstants.ACCLOCK_MSG);

		user.setPwd(randomCharGen(6));
		//TODO : PASSWORD ENCRYPTION
		user = userrepo.save(user);

		boolean status = utils.sendEmail(regform.getEmail(), "subject", "body");
		return user.getUserId() != null ? true : false;
	}

	@Override
	public boolean UnlockAcc(UnlockAccForm unlockform) {
		String email = unlockform.getEmail();
		String temppwd = unlockform.getTemppwd();
		User user = userrepo.findByemailAndPwd(email, temppwd);
		if (user != null) {
			user.setPwd(unlockform.getChoosenpwd());
			user.setAccStatus(AppConstants.UNLOCK_MSG);
			userrepo.save(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean frgtPwd(String email) {
		User user = new User();
		user.setEmail(email);
		//find by with example(filter the data)
		Example<User> example = Example.of(user);
		Optional<User> findOne = userrepo.findOne(example);
		if (findOne.isPresent()) {
			User userentity = findOne.get();
			String mailBody = readForgotPwdEmailBody(userentity);
			String subject = props.getMessage().get(AppConstants.RECOVER_PWD_EMAIL_SUB);
			utils.sendEmail(user.getEmail(), subject, mailBody);
			return true;
		}
		return false;
	}

	private String readForgotPwdEmailBody(User userentity) {
		StringBuffer sb = new StringBuffer(AppConstants.EMPTY_STRING);
		String mailBody = AppConstants.EMPTY_STRING;
		try {
			String fileName = props.getMessage().get(AppConstants.UNLOCK_ACC_EMAIL_SUB);
			FileReader fr = new FileReader(AppConstants.UNLOCK_ACC_EMAIL_BODY_FILE);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();

			mailBody = sb.toString();
			mailBody = mailBody.replaceAll(AppConstants.FNAME, userentity.getFname());
			mailBody = mailBody.replaceAll(AppConstants.LNAME, userentity.getLname());
			mailBody = mailBody.replaceAll(AppConstants.TEMP_PWD, userentity.getPwd());
			mailBody = mailBody.replaceAll(AppConstants.EMAIL, userentity.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailBody;
	}

	private static String randomCharGen(int length) {
		String candidateChars = AppConstants.CANDIDATE_CHARS;
		StringBuilder builder = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			builder.append(candidateChars.charAt(r.nextInt(candidateChars.length())));
		}
		return builder.toString();
	}

	private String readUnlockAccEmailBody(User entity) {
		StringBuffer sb = new StringBuffer(AppConstants.EMPTY_STRING);
		String mailBody = AppConstants.EMPTY_STRING;
		try {
			String fileName = props.getMessage().get(AppConstants.UNLOCK_ACC_EMAIL_SUB);
			FileReader fr = new FileReader(AppConstants.UNLOCK_ACC_EMAIL_BODY_FILE);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();

			mailBody = sb.toString();
			mailBody = mailBody.replaceAll(AppConstants.FNAME, entity.getFname());
			mailBody = mailBody.replaceAll(AppConstants.LNAME, entity.getLname());
			mailBody = mailBody.replaceAll(AppConstants.TEMP_PWD, entity.getPwd());
			mailBody = mailBody.replaceAll(AppConstants.EMAIL, entity.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mailBody;
	}
}
