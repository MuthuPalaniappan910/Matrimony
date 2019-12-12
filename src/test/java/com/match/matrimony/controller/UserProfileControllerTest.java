package com.match.matrimony.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.match.matrimony.controller.UserProfileController;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.service.UserProfileService;
import com.match.matrimony.service.UserRegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileControllerTest {
	@InjectMocks
	UserProfileController userProfileController;
	@Mock
	UserProfileService userProfileService;
	@Mock
	UserRegistrationService userRegistrationService;
	UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
	UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();

	@Before
	public void setUp() {
		userRegistrationRequestDto.setEmail("ans@gmail.com");
		userRegistrationRequestDto.setExpectation("looking for job holder");
		userRegistrationRequestDto.setGender("male");
		userRegistrationRequestDto.setMobile(9403347L);
		userRegistrationRequestDto.setMotherTongue("english");
		userRegistrationRequestDto.setPlace("karnataka");
		userRegistrationRequestDto.setProfession("doctor");
		userRegistrationRequestDto.setReligion("hindu");
		userRegistrationRequestDto.setSalary(35644.50);
		userRegistrationRequestDto.setDateOfBirth(LocalDate.now());
		userRegistrationResponseDto.setStatusCode(200);
		userRegistrationResponseDto.setMessage("success");
		userRegistrationResponseDto.setUserProfileId(1004L);
	}

	@Test
	public void matrimonyRegistration() throws GeneralException {
		Mockito.when(userRegistrationService.matrimonyRegistration(userRegistrationRequestDto))
				.thenReturn(Optional.of(userRegistrationResponseDto));
		ResponseEntity<Optional<UserRegistrationResponseDto>> response = userProfileController
				.matrimonyRegistration(userRegistrationRequestDto);
		Assert.assertNotNull(response);

	}

	@Test
	public void matrimonyRegistrationNegative() throws GeneralException {
		Mockito.when(userRegistrationService.matrimonyRegistration(userRegistrationRequestDto))
				.thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<UserRegistrationResponseDto>> response = userProfileController
				.matrimonyRegistration(userRegistrationRequestDto);

		Assert.assertNotNull(response);
	}
}
