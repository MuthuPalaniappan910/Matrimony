package com.match.matrimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.DashboardResponseDto;
import com.match.matrimony.dto.FavouriteProfileRequestDto;
import com.match.matrimony.dto.FavouriteProfileResponsedto;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Spark
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


	/**
	 * Method to view the detailed profile view of the match that interests the user.
	 * @author Chethana
	 * @param userProfileId
	 * @return
	 * @throws UserProfileException throws if there is no record found for my interested match
	 */
	@GetMapping("/{userProfileId}")
	public ResponseEntity<Optional<UserProfileResponsedto>> viewProfile(@PathVariable Long userProfileId) throws UserProfileException{
		log.info("Entering into viewProfile() method of UserProfileController");
		Optional<UserProfileResponsedto> userProfileResponsedto=userProfileService.viewProfile(userProfileId);
		if(!userProfileResponsedto.isPresent()) {
			log.error("Exception occured in viewProfile of UserProfileController");
			UserProfileResponsedto UserProfileResponsedto= new UserProfileResponsedto();
			UserProfileResponsedto.setMessage(ApplicationConstants.NO_PROFILE);
			UserProfileResponsedto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
			return new ResponseEntity<>(userProfileResponsedto,HttpStatus.NOT_FOUND);
		}
		userProfileResponsedto.get().setMessage(ApplicationConstants.SUCCESS);
		userProfileResponsedto.get().setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		return new ResponseEntity<>(userProfileResponsedto,HttpStatus.OK);		
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequestDto loginRequestDto) {
		log.info("Inside login method");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		Long userProfileId = loginRequestDto.getUserProfileId();
		String userProfilePassword = loginRequestDto.getUserProfilePassword();
		Optional<UserProfile> userProfile = userProfileService.userLogin(userProfileId, userProfilePassword);
		if (userProfile.isPresent()) {
			loginResponseDto.setUserProfileId(userProfile.get().getUserProfileId());
			loginResponseDto.setGender(userProfile.get().getGender());
			loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
			loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_SUCCESS_MESSAGE);
			return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
		}
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Method to add the match as his/her favourite profile
	 * @author Chethana
	 * @param favouriteProfileRequestDto accepts userprofileId and Interested matchId
	 * @return
	 * @throws UserProfileException throws when failed to add the interested match to favourites
	 */
	@PostMapping("/favourites")
	public ResponseEntity<Optional<FavouriteProfileResponsedto>> addFavourite(@RequestBody FavouriteProfileRequestDto favouriteProfileRequestDto) throws UserProfileException {

		Optional<FavouriteProfileResponsedto> favouriteProfileResponsedto = userProfileService
				.addFavourite(favouriteProfileRequestDto);
		if (!favouriteProfileResponsedto.isPresent()) {
			FavouriteProfileResponsedto favouriteProfileResponse = new FavouriteProfileResponsedto();
			favouriteProfileResponse.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
			favouriteProfileResponse.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);
			return new ResponseEntity<>(Optional.of(favouriteProfileResponse), HttpStatus.NOT_FOUND);
		}
		favouriteProfileResponsedto.get().setMessage(ApplicationConstants.SUCCESS);
		favouriteProfileResponsedto.get().setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		return new ResponseEntity<>(favouriteProfileResponsedto, HttpStatus.OK);
	}
	 
	 

}

