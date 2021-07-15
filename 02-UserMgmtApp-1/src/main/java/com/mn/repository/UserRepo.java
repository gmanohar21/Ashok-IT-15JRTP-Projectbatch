package com.mn.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mn.entity.User;


public interface UserRepo extends JpaRepository<User, Serializable> {
	public User findByemailAndPwd (String email, String pwd);

	public User findByemail(String email);
	
}
