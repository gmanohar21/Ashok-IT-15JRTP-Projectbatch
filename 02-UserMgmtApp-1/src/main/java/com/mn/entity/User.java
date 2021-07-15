package com.mn.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = " USER")
public class User {
	@Id
	@GeneratedValue
	private Integer userId;
	private String email;
	private String gender;
	private String fname;
	private String lname;
	private String pwd;
	private String accStatus;
	private Integer countryId;
	private Integer cityId;
	private Integer stateId;
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	@CreationTimestamp
	private LocalDate dob;
	public String phno;
	

}
