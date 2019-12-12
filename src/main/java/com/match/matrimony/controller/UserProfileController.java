package com.match.matrimony.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.service.UserProfileService;
import com.match.matrimony.service.UserRegistrationService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/users")
@Slf4j
public class UserProfileController {
	@Autowired
	UserProfileService userProfileService;
	@Autowired
	UserRegistrationService userRegistrationService;

	@PostMapping("")
	public ResponseEntity<Optional<UserRegistrationResponseDto>> matrimonyRegistration(
			@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) throws GeneralException {
		Optional<UserRegistrationResponseDto> userRegistrationResponseDto = userRegistrationService
				.matrimonyRegistration(userRegistrationRequestDto);
		if (userRegistrationResponseDto.isPresent()) {
			log.info("registration method successfully executed");
			userRegistrationResponseDto.get().setStatusCode(ApplicationConstants.REGISTRATION_SUCCESS_CODE);
			userRegistrationResponseDto.get().setMessage(ApplicationConstants.REGISTRATION_SUCCESS_MESSAGE);
			return new ResponseEntity<>(userRegistrationResponseDto, HttpStatus.OK);
		}
		log.error("enterd into registration method");
		UserRegistrationResponseDto userRegistrationResponse= new UserRegistrationResponseDto();
		userRegistrationResponse.setStatusCode(ApplicationConstants.REGISTRATION_FAILURE_CODE);
		userRegistrationResponse.setMessage(ApplicationConstants.REGISTRATION_FAILURE_MESSAGE);
		return new ResponseEntity<>(Optional.of(userRegistrationResponse), HttpStatus.NOT_FOUND);
	}

}
