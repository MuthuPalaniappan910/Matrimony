package com.match.matrimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.DashboardResponseDto;
import com.match.matrimony.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Bindushree
 *
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/users")
@Slf4j
public class UserProfileController {
	@Autowired
	UserProfileService userProfileService;
	
	/**
	 * The matchList method is used fetch all records based on pre defined criteria
	 * 
	 * @param userProfileId
	 * @return
	 */
	
	@GetMapping("/{userProfileId}/dashboard")
	public ResponseEntity<DashboardResponseDto> matchList(@PathVariable Long userProfileId) {
		log.info("Entering into userProfile service");
		Optional<List<DashboardResponse>> userProfiles=userProfileService.matchList(userProfileId);
		DashboardResponseDto dashboardResponseDto=new DashboardResponseDto();
		
		if(userProfiles.isPresent()) {
			dashboardResponseDto.setDashboardResponses(userProfiles.get());
			dashboardResponseDto.setMessage(ApplicationConstants.SUCCESS);
			dashboardResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
			return new ResponseEntity<>(dashboardResponseDto, HttpStatus.OK);
			
		}
		log.info("User profile is empty");
		dashboardResponseDto.setMessage(ApplicationConstants.NO_MATCHLIST_FOUND);
		dashboardResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		return new ResponseEntity<>(dashboardResponseDto, HttpStatus.NOT_FOUND);
		
		
	}

}

