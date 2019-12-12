package com.match.matrimony.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserProfileController {
	@Autowired
	UserProfileService userProfileService;


	/**
	 * 
	 * @param userProfileId
	 * @return
	 * @throws UserProfileException
	 */
	@GetMapping("/{userProfileId}")
	public ResponseEntity<Optional<UserProfileResponsedto>> viewProfile(@RequestParam Long userProfileId) throws UserProfileException{
		log.info("Entering into viewProfile() method of UserProfileController");
		Optional<UserProfileResponsedto> userProfileResponsedto=userProfileService.viewProfile(userProfileId);
		if(!userProfileResponsedto.isPresent()) {
			log.error("Exception occured in viewProfile of UserProfileController");
			throw new UserProfileException(ApplicationConstants.NO_PROFILE);
		}
		userProfileResponsedto.get().setMessage(ApplicationConstants.SUCCESS);
		userProfileResponsedto.get().setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		return new ResponseEntity<>(userProfileResponsedto,HttpStatus.OK);		
	}

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
