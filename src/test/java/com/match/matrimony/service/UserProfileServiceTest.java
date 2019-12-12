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
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.repository.UserProfileRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {
	@Mock
	UserProfileRepository userProfileRepository;

	@InjectMocks
	UserProfileServiceImpl userProfileServiceImpl;

	UserProfile userProfile = null;
	UserProfile userProfile1 = null;
	List<UserProfile> profiles=null;
	List<UserProfile> profiles1=null;
	LoginRequestDto loginRequestDto = null;
	List<DashboardResponse> responseList =  null;
	DashboardResponse dashboardResponse =null;

	@Before
	public void before() {
		userProfile = new UserProfile();
		userProfile1 = new UserProfile();
		loginRequestDto = new LoginRequestDto();
		profiles=new ArrayList<>();
		profiles1=new ArrayList<>();
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
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		assertNotNull(response);
	}
	
	@Test
	public void testMatchListForNegative() {
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles1);
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
		
	}
	
	@Test
	public void testMatchListForNegative1() {
		userProfile1.setGender("Female");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
		
	}
	
	@Test
	public void testMatchListForNegative2() {
		userProfile1.setGender("Male");
		userProfile1.setDateOfBirth(LocalDate.of(2000, 05, 12));
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
		
	}
	
	@Test
	public void testMatchListForNegative3() {
		userProfile1.setDateOfBirth(LocalDate.of(1995, 05, 12));
		userProfile1.setGender("Male");
		userProfile1.setMotherTongue("Telugu");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
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
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
	}
	
	@Test
	public void testMatchListForNegative5() {
		userProfile1.setDateOfBirth(LocalDate.of(1994, 05, 12));
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
	}
	
	@Test
	public void testMatchListForNegative6() {
		userProfile1.setMotherTongue("Telugu");
		Mockito.when(userProfileRepository.findByUserProfileId(1L)).thenReturn(userProfile);
		Mockito.when(userProfileRepository.findAllByUserProfileIdNot(1L)).thenReturn(profiles);
		Optional<List<DashboardResponse>> response=userProfileServiceImpl.matchList(1L);
		Assert.assertEquals(profiles1, response.get());
	}
	
}
