package com.match.matrimony.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequestDto {
	private String userProfileName;
	private String gender;
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
