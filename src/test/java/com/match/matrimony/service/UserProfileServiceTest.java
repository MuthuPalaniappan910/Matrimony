package com.match.matrimony.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.repository.UserProfileRepository;


@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {
	@Mock
	UserProfileRepository userProfileRepository;

	@InjectMocks
	UserProfileServiceImpl userProfileServiceImpl;

	UserProfile userProfile = null;
	LoginRequestDto loginRequestDto = null;

	@Before
	public void before() {
		userProfile = new UserProfile();
		loginRequestDto = new LoginRequestDto();

		userProfile.setUserProfileId(1L);
		userProfile.setUserProfilePassword("muthu123");
		loginRequestDto.setUserProfileId(1L);
		loginRequestDto.setUserProfilePassword("muthu123");
	}

	@Test
	public void testUserLoginPositive() {
		Mockito.when(userProfileRepository.findByUserProfileIdAndUserProfilePassword(loginRequestDto.getUserProfileId(),
				loginRequestDto.getUserProfilePassword())).thenReturn(Optional.of(new UserProfile()));
		Optional<UserProfile> expected = userProfileServiceImpl.userLogin(loginRequestDto.getUserProfileId(),
				loginRequestDto.getUserProfilePassword());
		assertEquals(true, expected.isPresent());
	}
	
	@Test
	public void testViewProfile() throws UserProfileException {
		Mockito.when(userProfileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userProfile));
		Optional<UserProfileResponsedto> userProfileResponsedto=userProfileServiceImpl.viewProfile(1L);
		Assert.assertNotNull(userProfileResponsedto);
	}
	
	@Test(expected=UserProfileException.class)
	public void testViewProfileNegative() throws UserProfileException {
		Mockito.when(userProfileRepository.findById(2L)).thenReturn(Optional.of(userProfile));
		userProfileServiceImpl.viewProfile(1L);
	}
}
