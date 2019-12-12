package com.match.matrimony.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserProfileResponsedto {

	private String userProfileName;
	private Long userProfileId;
	private String gender;
	private String religion;
	private String motherTongue;
	private String profession;
	private String email;
	private String place;
	private LocalDate dateOfBirth;
	private Double salary;
	private String expectation;
	
	private String message;
	private Integer statusCode;
	
}
