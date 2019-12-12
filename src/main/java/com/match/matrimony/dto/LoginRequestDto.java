package com.match.matrimony.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	private Long userProfileId;
	private String userProfilePassword;
}
