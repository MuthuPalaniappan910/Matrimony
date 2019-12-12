package com.match.matrimony.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.DashboardResponseDto;
import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.service.UserProfileService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserProfileControllerTest {
	
	@InjectMocks
	UserProfileController userProfileController;
	@Mock
	UserProfileService userProfileService;
	
	List<DashboardResponse> dashboardResponses=null;
	DashboardResponse dashboardResponse=null;
	DashboardResponseDto dashboardResponseDto=null;
	
	UserProfile userProfile = null;
	LoginRequestDto loginRequestDto = null;
	LoginRequestDto loginRequestDto1 = null;
	LoginResponseDto loginResponseDto = null;

	@Before
	public void before() {
		dashboardResponses=new ArrayList<>();
		dashboardResponse=new DashboardResponse();
		dashboardResponse.setProfession("Engineer");
		dashboardResponses.add(dashboardResponse);
		
		dashboardResponseDto=new DashboardResponseDto();
		dashboardResponseDto.setDashboardResponses(dashboardResponses);
		
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
	}
	
	
	@Test
	public void matchListForPositive() {
		Mockito.when(userProfileService.matchList(1L)).thenReturn(Optional.of(dashboardResponses));
		Integer status=userProfileController.matchList(1L).getStatusCodeValue();
		assertEquals(200, status);
	}
	
	@Test
	public void matchListForNegative() {
		Optional<List<DashboardResponse>> dashboardResponses1 = Optional.ofNullable(null);
		Mockito.when(userProfileService.matchList(2L)).thenReturn(dashboardResponses1);
		Integer status=userProfileController.matchList(2L).getStatusCodeValue();
		assertEquals(404, status);
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
}
