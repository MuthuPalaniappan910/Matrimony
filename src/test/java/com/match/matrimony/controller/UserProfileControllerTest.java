package com.match.matrimony.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.service.UserProfileService;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserProfileControllerTest {
	@InjectMocks
	UserProfileController userProfileController;

	@Mock
	UserProfileService userProfileService;

	UserProfile userProfile = null;
	LoginRequestDto loginRequestDto = null;
	LoginRequestDto loginRequestDto1 = null;
	LoginResponseDto loginResponseDto = null;
	UserProfileResponsedto userProfileResponsedto= new UserProfileResponsedto();
	
	@Before
	public void before() {
		userProfile = new UserProfile();
		loginRequestDto = new LoginRequestDto();
		loginResponseDto = new LoginResponseDto();

		userProfile.setUserProfileId(1L);
		userProfile.setUserProfilePassword("muthu123");

		loginRequestDto.setUserProfileId(1L);
		loginRequestDto.setUserProfilePassword("muthu123");
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_SUCCESS_MESSAGE);

		loginRequestDto1 = new LoginRequestDto();
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);
		
		userProfileResponsedto.setUserProfileId(1L);
	}

	@Test
	public void testUserLoginPositive() {
		Mockito.when(userProfileService.userLogin(loginRequestDto.getUserProfileId(),
				loginRequestDto.getUserProfilePassword())).thenReturn(Optional.of(new UserProfile()));
		Integer expected = userProfileController.userLogin(loginRequestDto).getStatusCodeValue();
		assertEquals(ApplicationConstants.USERPROFILE_SUCCESS_CODE, expected);
	}

	@Test
	public void testUserLoginNegative() {
		Mockito.when(userProfileService.userLogin(loginRequestDto1.getUserProfileId(),
				loginRequestDto1.getUserProfilePassword())).thenReturn(Optional.of(new UserProfile()));
		Integer expected = userProfileController.userLogin(loginRequestDto).getStatusCodeValue();
		assertEquals(ApplicationConstants.USERPROFILE_FAILURE_CODE, expected);
	}
	
	@Test
	public void testViewProfile() throws UserProfileException {
		Mockito.when(userProfileService.viewProfile(1L)).thenReturn(Optional.of(userProfileResponsedto));
		ResponseEntity<Optional<UserProfileResponsedto>> userProfileResponsedto=userProfileController.viewProfile(1L);
		Assert.assertNotNull(userProfileResponsedto);
	}
	
	@Test
	public void x() throws UserProfileException {
		Mockito.when(userProfileService.viewProfile(2L)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<UserProfileResponsedto>> userProfileResponsedto=userProfileController.viewProfile(1L);
		Assert.assertNotNull(userProfileResponsedto);
	}
}
