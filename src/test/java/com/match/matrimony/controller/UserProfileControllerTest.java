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

	@Before
	public void before() {
		dashboardResponses=new ArrayList<>();
		dashboardResponse=new DashboardResponse();
		dashboardResponse.setProfession("Engineer");
		dashboardResponses.add(dashboardResponse);
		
		dashboardResponseDto=new DashboardResponseDto();
		dashboardResponseDto.setDashboardResponses(dashboardResponses);
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

}
