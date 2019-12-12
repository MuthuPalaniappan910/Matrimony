package com.match.matrimony.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.GeneralException;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceImplTest {
	@InjectMocks
	UserRegistrationServiceImpl userRegistrationServiceImpl;
	UserRegistrationResponseDto userRegistrationResponseDto = null;
	UserRegistrationRequestDto userRegistrationRequestDto = null;

	@Before
	public void before() {
		userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationResponseDto = new UserRegistrationResponseDto();
		userRegistrationRequestDto.setEmail("ans@gmail.com");
		userRegistrationRequestDto.setExpectation("looking for job holder");
		userRegistrationRequestDto.setGender("male");
		userRegistrationRequestDto.setMobile(9403347L);
		userRegistrationRequestDto.setMotherTongue("english");
		userRegistrationRequestDto.setPlace("karnataka");
		userRegistrationRequestDto.setProfession("doctor");
		userRegistrationRequestDto.setReligion("hindu");
		userRegistrationRequestDto.setSalary(35644.50);
		userRegistrationResponseDto.setStatusCode(200);
		userRegistrationRequestDto.setDateOfBirth(LocalDate.parse("2019-01-12"));
		userRegistrationResponseDto.setMessage("success");
		userRegistrationResponseDto.setUserProfileId(1004L);
	}

	public void matrimonyRegistration() throws GeneralException {
		Optional<UserRegistrationResponseDto> response = userRegistrationServiceImpl
				.matrimonyRegistration(userRegistrationRequestDto);
		assertNotNull(response);
	}

	@Test(expected = Exception.class)
	public void matrimonyRegistrationNegative() throws GeneralException {
		Optional<UserRegistrationResponseDto> response = userRegistrationServiceImpl
				.matrimonyRegistration(userRegistrationRequestDto);
		assertNotNull(response);
	}

}
