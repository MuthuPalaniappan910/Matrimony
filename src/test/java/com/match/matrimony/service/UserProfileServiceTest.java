package com.match.matrimony.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.FavouriteProfileRequestDto;
import com.match.matrimony.dto.FavouriteProfileResponsedto;
import com.match.matrimony.dto.Favourites;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.exception.UserProfileException;
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

	List<UserProfile> profiles = null;
	List<UserProfile> profiles1 = null;
	LoginRequestDto loginRequestDto = null;
	List<DashboardResponse> responseList = null;
	DashboardResponse dashboardResponse = null;

	FavouriteProfileResponsedto favouriteProfileResponsedto = new FavouriteProfileResponsedto();
	FavouriteProfileRequestDto favouriteProfileRequestDto = new FavouriteProfileRequestDto();

	UserProfile userProfile1 = new UserProfile();

	UserProfile userProfile2 = new UserProfile();
	List<UserFavourite> userFavouriteList = new ArrayList<>();
	List<UserFavourite> userFavouriteList1 = null;
	UserFavourite userFavourite = new UserFavourite();

	@Before
	public void before() {
		userProfile = new UserProfile();
		userProfile1 = new UserProfile();
		loginRequestDto = new LoginRequestDto();
		profiles = new ArrayList<>();
		profiles1 = new ArrayList<>();
		responseList = new ArrayList<>();
		dashboardResponse = new DashboardResponse();

		userProfile.setUserProfileId(1L);
		userProfile.setUserProfilePassword("muthu123");
		userProfile.setDateOfBirth(LocalDate.of(1997, 05, 12));
		userProfile.setGender("Female");
		userProfile.setMotherTongue("Kannada");

		userProfile1.setUserProfileId(5L);
		userProfile1.setDateOfBirth(LocalDate.of(1995, 05, 12));
		userProfile1.setGender("Male");
		userProfile1.setMotherTongue("Kannada");

		profiles.add(userProfile);
		profiles.add(userProfile1);

		loginRequestDto.setUserProfileId(1L);
		loginRequestDto.setUserProfilePassword("muthu123");

		dashboardResponse.setProfession("engineer");
		dashboardResponse.setReligion("Hindu");
		dashboardResponse.setUserProfileId(2L);

		responseList.add(dashboardResponse);

		favouriteProfileRequestDto.setUserMatchId(2L);
		favouriteProfileRequestDto.setUserProfileId(1L);

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

	@Test
	public void testMatchListForPositive() {
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		assertNotNull(response);
	}

	@Test
	public void testMatchListForNegative() {
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles1);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());

	}

	@Test
	public void testMatchListForNegative1() {
		userProfile1.setGender("Female");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());

	}

	@Test
	public void testMatchListForNegative2() {
		userProfile1.setGender("Male");
		userProfile1.setDateOfBirth(LocalDate.of(2000, 05, 12));
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());

	}

	@Test
	public void testMatchListForNegative3() {
		userProfile1.setDateOfBirth(LocalDate.of(1995, 05, 12));
		userProfile1.setGender("Male");
		userProfile1.setMotherTongue("Telugu");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());

	}

	@Test
	public void testMatchListForNegative4() {
		userProfile.setGender("Male");
		userProfile.setDateOfBirth(LocalDate.of(1997, 05, 12));
		userProfile.setMotherTongue("Kannada");
		userProfile1.setGender("Female");
		userProfile1.setDateOfBirth(LocalDate.of(1995, 05, 12));
		userProfile1.setMotherTongue("Kannada");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
	}

	@Test
	public void testMatchListForNegative5() {
		userProfile1.setUserProfileId(5L);
		userProfile1.setDateOfBirth(LocalDate.of(1994, 05, 12));
		userProfile.setGender("Male");
		userProfile1.setGender("Female");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
	}

	@Test
	public void testMatchListForNegative6() {
		userProfile.setGender("Male");
		userProfile1.setGender("Female");
		userProfile1.setMotherTongue("Telugu");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response = userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
	}

	@Test(expected = ProfileNotFoundException.class)
	public void testViewFavouritesNoProfileFound() throws ProfileNotFoundException {
		Mockito.when(userProfileRepository.findById(2L)).thenReturn(Optional.of(userProfile1));
		List<Favourites> expected = userProfileServiceImpl.viewFavourites(1L);
		assertEquals(0, expected.size());
	}

	@Test(expected = ProfileNotFoundException.class)
	public void testViewFavouritesEmptyList() throws ProfileNotFoundException {
		Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile1));
		Mockito.when(userFavouriteRepository.findAllByUserProfileId(userProfile2)).thenReturn(userFavouriteList1);
		List<Favourites> expected = userProfileServiceImpl.viewFavourites(1L);
		assertEquals(0, expected.size());
	}

	@Test
	public void testViewFavouritesSuccess() throws ProfileNotFoundException {
		Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile1));
		Mockito.when(userFavouriteRepository.findAllByUserProfileId(userProfile1)).thenReturn(userFavouriteList);
		Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile1));
		List<Favourites> expected = userProfileServiceImpl.viewFavourites(1L);
		assertEquals(1, expected.size());
	}

	@Test
	public void testViewProfile() throws UserProfileException {
		Mockito.when(userProfileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userProfile));
		Optional<UserProfileResponsedto> userProfileResponsedto = userProfileServiceImpl.viewProfile(1L);
		Assert.assertNotNull(userProfileResponsedto);
	}

	@Test(expected = UserProfileException.class)
	public void testViewProfileNegative() throws UserProfileException {
		Mockito.when(userProfileRepository.findById(2L)).thenReturn(Optional.of(userProfile));
		userProfileServiceImpl.viewProfile(1L);
	}

	@Test(expected = UserProfileException.class)
	public void testAddToFavouriteNegative() throws UserProfileException {
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		userProfileServiceImpl.addFavourite(favouriteProfileRequestDto);
	}

	@Test(expected = UserProfileException.class)
	public void testAddToFavouriteNegativeMatch() throws UserProfileException {
		Mockito.when(userProfileRepository.findByUserProfileId(2L)).thenReturn(userProfile);
		userProfileServiceImpl.addFavourite(favouriteProfileRequestDto);
	}

	@Test
	public void testAddToFavouriteFavourite() throws UserProfileException {
		Mockito.when(userProfileRepository.findByUserProfileId(Mockito.anyLong())).thenReturn(userProfile);
		Optional<FavouriteProfileResponsedto > favouriteProfileResponsedto=userProfileServiceImpl.addFavourite(favouriteProfileRequestDto);
		Assert.assertNotNull(favouriteProfileResponsedto);
	}

	@Test(expected = UserProfileException.class)
	public void testAddToFavouriteFavouriteNegative() throws UserProfileException {
		Mockito.when(userProfileRepository.findByUserProfileId(Mockito.anyLong())).thenReturn(userProfile);
		Mockito.when(userFavouriteRepository.findByUserProfileIdAndUserMatchId(Mockito.any(), Mockito.any()))
				.thenReturn(Optional.of(userFavourite));
		userProfileServiceImpl.addFavourite(favouriteProfileRequestDto);
	}
}
