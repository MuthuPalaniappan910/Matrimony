package com.match.matrimony.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileSearchResponseDto {
	private Long userProfileId;
	private String userProfileName;
	private String religion;
	private String profession;
	private Integer age;
	private Double salary;
}
