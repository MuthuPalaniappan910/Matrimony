package com.match.matrimony.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.repository.UserProfileRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserRegistrationServiceImplTest {
	@InjectMocks
	UserRegistrationServiceImpl userRegistrationServiceImpl;

	@Mock
	UserProfileRepository userProfileRepository;

	UserRegistrationResponseDto userRegistrationResponseDto = null;
	UserRegistrationRequestDto userRegistrationRequestDto = null;

	UserRegistrationRequestDto userRegistrationRequestDto1 = null;
	UserProfile userProfile = new UserProfile();

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

		userRegistrationRequestDto1 = new UserRegistrationRequestDto();
		userRegistrationRequestDto1.setEmail("answ@gmail.com");
		userRegistrationRequestDto1.setExpectation("looking for job holder");
		userRegistrationRequestDto1.setGender("male");
		userRegistrationRequestDto1.setMobile(94033484L);
		userRegistrationRequestDto1.setMotherTongue("english");
		userRegistrationRequestDto1.setPlace("karnataka");
		userRegistrationRequestDto1.setProfession("doctor");
		userRegistrationRequestDto1.setReligion("hindu");
		userRegistrationRequestDto1.setSalary(356444.50);
		userRegistrationRequestDto1.setDateOfBirth(LocalDate.parse("1999-01-12"));
		
		userProfile.setMobile(940334L);
	}

	@Test(expected = Exception.class)
	public void matrimonyRegistrationNegative() throws GeneralException {
		Optional<UserRegistrationResponseDto> response = userRegistrationServiceImpl
				.matrimonyRegistration(userRegistrationRequestDto);
		assertNotNull(response);
	}

	@Test
	public void success() throws GeneralException {
		Mockito.when(userProfileRepository.findByMobile(userRegistrationRequestDto.getMobile()))
				.thenReturn(Optional.of(userProfile));
		Optional<UserRegistrationResponseDto> expected = userRegistrationServiceImpl
				.matrimonyRegistration(userRegistrationRequestDto1);
		assertEquals(true, expected.isPresent());
	}
	
	@Test(expected=GeneralException.class)
	public void negative() throws GeneralException {
		Mockito.when(userProfileRepository.findByMobile(userRegistrationRequestDto.getMobile()))
				.thenReturn(Optional.of(userProfile));
		Optional<UserRegistrationResponseDto> expected = userRegistrationServiceImpl
				.matrimonyRegistration(userRegistrationRequestDto);
		assertEquals(ApplicationConstants.USER_REGISTRATION_MOBILE_MESSAGE, expected);
	}

}
