package com.match.matrimony.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.match.matrimony.dto.Favourites;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.repository.UserFavouriteRepository;
import com.match.matrimony.repository.UserProfileRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserProfileServiceTest {
	@Mock
	UserProfileRepository userProfileRepository;

	@Mock
	UserFavouriteRepository userFavouriteRepository;

	@InjectMocks
	UserProfileServiceImpl userProfileServiceImpl;

	UserProfile userProfile = null;
	LoginRequestDto loginRequestDto = null;

	UserProfile userProfile1 = new UserProfile();
	UserProfile userProfile2 = new UserProfile();
	List<UserFavourite> userFavouriteList = new ArrayList<>();
	List<UserFavourite> userFavouriteList1 = null;
	UserFavourite userFavourite = new UserFavourite();

	@Before
	public void before() {
		userProfile = new UserProfile();
		loginRequestDto = new LoginRequestDto();

		userProfile.setUserProfileId(1L);
		userProfile.setUserProfilePassword("muthu123");
		loginRequestDto.setUserProfileId(1L);
		loginRequestDto.setUserProfilePassword("muthu123");

		userFavouriteList1 = new ArrayList<>();

		userProfile1.setUserProfileId(1L);
		userProfile2.setUserProfileId(1L);

		userFavourite.setUserFavouriteId(1L);
		userFavourite.setUserMatchId(userProfile2);
		userFavouriteList.add(userFavourite);
	}

	@Test
	public void testUserLoginPositive() {
		Mockito.when(userProfileRepository.findByUserProfileIdAndUserProfilePassword(loginRequestDto.getUserProfileId(),
				loginRequestDto.getUserProfilePassword())).thenReturn(Optional.of(new UserProfile()));
		Optional<UserProfile> expected = userProfileServiceImpl.userLogin(loginRequestDto.getUserProfileId(),
				loginRequestDto.getUserProfilePassword());
		assertEquals(true, expected.isPresent());
	}

	@Test(expected = ProfileNotFoundException.class)
	public void testViewFavouritesNoProfileFound() throws ProfileNotFoundException {
		Mockito.when(userProfileRepository.findByUserProfileId(2L)).thenReturn(Optional.of(userProfile1));
		List<Favourites> expected = userProfileServiceImpl.viewFavourites(1L);
		assertEquals(0, expected.size());
	}

	@Test(expected = ProfileNotFoundException.class)
	public void testViewFavouritesEmptyList() throws ProfileNotFoundException {
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(Optional.of(userProfile1));
		Mockito.when(userFavouriteRepository.findAllByUserProfileId(userProfile2)).thenReturn(userFavouriteList1);
		List<Favourites> expected = userProfileServiceImpl.viewFavourites(1L);
		assertEquals(0, expected.size());
	}

	@Test
	public void testViewFavouritesSuccess() throws ProfileNotFoundException {
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(Optional.of(userProfile1));
		Mockito.when(userFavouriteRepository.findAllByUserProfileId(userProfile1)).thenReturn(userFavouriteList);
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(Optional.of(userProfile1));
		List<Favourites> expected = userProfileServiceImpl.viewFavourites(1L);
		assertEquals(1, expected.size());
	}
}
