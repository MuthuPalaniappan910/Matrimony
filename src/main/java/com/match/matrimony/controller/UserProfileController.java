package com.match.matrimony.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserProfileController {
	@Autowired
	UserProfileService userProfileService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequestDto loginRequestDto) {
		log.info("Inside login method");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		Long userProfileId = loginRequestDto.getUserProfileId();
		String userProfilePassword = loginRequestDto.getUserProfilePassword();
		Optional<UserProfile> userProfile = userProfileService.userLogin(userProfileId, userProfilePassword);
		if (userProfile.isPresent()) {
			loginResponseDto.setUserProfileId(userProfile.get().getUserProfileId());
			loginResponseDto.setGender(userProfile.get().getGender());
			loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
			loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_SUCCESS_MESSAGE);
			return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
		}
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.NOT_FOUND);
	}

}
