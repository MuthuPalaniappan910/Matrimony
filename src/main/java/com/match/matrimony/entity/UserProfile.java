package com.match.matrimony.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "userprofilesequence", initialValue = 1000, allocationSize = 1)
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userprofilesequence")
	private Long userProfileId;
	private String userProfileName;
	private String gender;
	@JsonFormat(pattern = "yyyy-MMM-dd")
	private LocalDate dateOfBirth;
	private Long mobile;
	private String email;
	private String userProfilePassword;
	private String religion;
	private String motherTongue;
	private String profession;
	private String place;
	private String expectation;
	private Double salary;
}
