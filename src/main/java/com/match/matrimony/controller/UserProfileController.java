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
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.DashboardResponseDto;
import com.match.matrimony.dto.FavouriteProfileRequestDto;
import com.match.matrimony.dto.FavouriteProfileResponsedto;
import com.match.matrimony.dto.Favourites;
import com.match.matrimony.dto.FavouritesResponseDto;
import com.match.matrimony.dto.LoginRequestDto;
import com.match.matrimony.dto.LoginResponseDto;
import com.match.matrimony.dto.MatchProfileRequestDto;
import com.match.matrimony.dto.MatchProfileResponsedto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.service.UserProfileService;
import com.match.matrimony.service.UserRegistrationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Spark
 *
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/users")
@Slf4j
public class UserProfileController {
	@Autowired
	UserProfileService userProfileService;

	@Autowired
	UserRegistrationService userRegistrationService;

	@PostMapping("")
	public ResponseEntity<Optional<UserRegistrationResponseDto>> matrimonyRegistration(
			@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) throws GeneralException {
		Optional<UserRegistrationResponseDto> userRegistrationResponseDto = userRegistrationService
				.matrimonyRegistration(userRegistrationRequestDto);
		if (userRegistrationResponseDto.isPresent()) {
			log.info("registration method successfully executed");
			userRegistrationResponseDto.get().setStatusCode(ApplicationConstants.REGISTRATION_SUCCESS_CODE);
			userRegistrationResponseDto.get().setMessage(ApplicationConstants.REGISTRATION_SUCCESS_MESSAGE);
			return new ResponseEntity<>(userRegistrationResponseDto, HttpStatus.OK);
		}
		log.error("enterd into registration method");
		UserRegistrationResponseDto userRegistrationResponse = new UserRegistrationResponseDto();
		userRegistrationResponse.setStatusCode(ApplicationConstants.REGISTRATION_FAILURE_CODE);
		userRegistrationResponse.setMessage(ApplicationConstants.REGISTRATION_FAILURE_MESSAGE);
		return new ResponseEntity<>(Optional.of(userRegistrationResponse), HttpStatus.NOT_FOUND);
	}

	/**
	 * The matchList method is used fetch all records based on pre defined criteria
	 * 
	 * @param userProfileId
	 * @return
	 */

	@GetMapping("/{userProfileId}/dashboard")
	public ResponseEntity<DashboardResponseDto> matchList(@PathVariable Long userProfileId) {
		log.info("Entering into userProfile service");
		Optional<List<DashboardResponse>> userProfiles = userProfileService.matchList(userProfileId);
		DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
		log.debug("Checking whether user profile list is present");
		if (userProfiles.isPresent()) {
			log.info("User profile list is present");
			dashboardResponseDto.setDashboardResponses(userProfiles.get());
			dashboardResponseDto.setMessage(ApplicationConstants.SUCCESS);
			dashboardResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
			return new ResponseEntity<>(dashboardResponseDto, HttpStatus.OK);

		}
		log.error("User profile is empty");
		dashboardResponseDto.setMessage(ApplicationConstants.NO_MATCHLIST_FOUND);
		dashboardResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		return new ResponseEntity<>(dashboardResponseDto, HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * Method to view the detailed profile view of the match that interests the
	 * user.
	 * 
	 * @author Chethana
	 * @param userProfileId
	 * @return
	 * @throws UserProfileException throws if there is no record found for my
	 *                              interested match
	 */
	@GetMapping("/{userProfileId}")
	public ResponseEntity<Optional<UserProfileResponsedto>> viewProfile(@PathVariable Long userProfileId)
			throws UserProfileException {
		log.info("Entering into viewProfile() method of UserProfileController");
		Optional<UserProfileResponsedto> userProfileResponsedto = userProfileService.viewProfile(userProfileId);
		if (!userProfileResponsedto.isPresent()) {
			log.error("Exception occured in viewProfile of UserProfileController");
			UserProfileResponsedto userProfileResponse = new UserProfileResponsedto();
			userProfileResponse.setMessage(ApplicationConstants.NO_PROFILE);
			userProfileResponse.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
			return new ResponseEntity<>(userProfileResponsedto, HttpStatus.NOT_FOUND);
		}
		userProfileResponsedto.get().setMessage(ApplicationConstants.SUCCESS);
		userProfileResponsedto.get().setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		return new ResponseEntity<>(userProfileResponsedto, HttpStatus.OK);
	}

	/**
	 * Method enables the user to login by entering the credentials to enjoy the
	 * service
	 * 
	 * @author Muthu
	 * @param loginRequestDto contains the userProfileId and userProfilePassword
	 * @return
	 */

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequestDto loginRequestDto) {
		log.info("Inside login method");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		Long userProfileId = loginRequestDto.getUserProfileId();
		String userProfilePassword = loginRequestDto.getUserProfilePassword();
		Optional<UserProfile> userProfile = userProfileService.userLogin(userProfileId, userProfilePassword);
		if (userProfile.isPresent()) {
			log.info("Login success");
			loginResponseDto.setUserProfileId(userProfile.get().getUserProfileId());
			loginResponseDto.setGender(userProfile.get().getGender());
			loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
			loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_SUCCESS_MESSAGE);
			return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
		}
		log.info("Login failed");
		loginResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
		loginResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.NOT_FOUND);
	}

	/**
	 * Method to add the match as his/her favourite profile
	 * 
	 * @author Chethana
	 * @param favouriteProfileRequestDto accepts userprofileId and Interested
	 *                                   matchId
	 * @return
	 * @throws UserProfileException throws when failed to add the interested match
	 *                              to favourites
	 */
	@PostMapping("/favourites")
	public ResponseEntity<Optional<FavouriteProfileResponsedto>> addFavourite(
			@RequestBody FavouriteProfileRequestDto favouriteProfileRequestDto) throws UserProfileException {

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
	
	/**
	 * @author Bindhu
	 * 
	 * @param matchProfileRequestDto
	 * @return
	 * @throws UserProfileException
	 * @throws ProfileNotFoundException
	 */

	@PostMapping("/matches")
	public ResponseEntity<Optional<MatchProfileResponsedto>> addMatch(
			@RequestBody MatchProfileRequestDto matchProfileRequestDto) throws UserProfileException, ProfileNotFoundException {

		Optional<MatchProfileResponsedto> matchProfileResponsedto = userProfileService.addMatch(matchProfileRequestDto);
		if (!matchProfileResponsedto.isPresent()) {
			MatchProfileResponsedto matchProfileResponse = new MatchProfileResponsedto();
			matchProfileResponse.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
			matchProfileResponse.setMessage(ApplicationConstants.USERPROFILE_FAILURE_MESSAGE);
			return new ResponseEntity<>(Optional.of(matchProfileResponse), HttpStatus.NOT_FOUND);
		}
		matchProfileResponsedto.get().setMessage(ApplicationConstants.SUCCESS);
		matchProfileResponsedto.get().setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		return new ResponseEntity<>(matchProfileResponsedto, HttpStatus.OK);
	}

	/**
	 * Method enables the user to view all the profiles that he/she had added in
	 * his/her favourites
	 * 
	 * @author Muthu
	 * 
	 * @param userProfileId
	 * @return
	 * @throws ProfileNotFoundException
	 */

	@GetMapping("{userProfileId}/favourites")
	public ResponseEntity<FavouritesResponseDto> viewFavourites(@PathVariable("userProfileId") Long userProfileId)
			throws ProfileNotFoundException {
		List<Favourites> favouritesList = userProfileService.viewFavourites(userProfileId);
		FavouritesResponseDto favouritesResponseDto = new FavouritesResponseDto();
		if (favouritesList.isEmpty()) {
			favouritesResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
			favouritesResponseDto.setMessage(ApplicationConstants.USERPROFILE_EMPTY_FAVOURITES_LIST);
			return new ResponseEntity<>(favouritesResponseDto, HttpStatus.NOT_FOUND);
		}
		favouritesResponseDto.setFavouritesList(favouritesList);
		favouritesResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		favouritesResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAVOURITES_LIST);
		return new ResponseEntity<>(favouritesResponseDto, HttpStatus.OK);
	}

	/**
	 * Method returns the list of persons who had added his/her as favourites
	 * 
	 * @author Muthu
	 * 
	 * @param userMatchId
	 * @return
	 * @throws ProfileNotFoundException
	 */

	@GetMapping("{userMatchId}/match")
	public ResponseEntity<FavouritesResponseDto> viewMatch(@PathVariable("userMatchId") Long userMatchId)
			throws ProfileNotFoundException {
		List<Favourites> matchList = userProfileService.viewMatch(userMatchId);
		FavouritesResponseDto favouritesResponseDto = new FavouritesResponseDto();
		if (matchList.isEmpty()) {
			favouritesResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_FAILURE_CODE);
			favouritesResponseDto.setMessage(ApplicationConstants.USERPROFILE_EMPTY_FAVOURITES_LIST);
			return new ResponseEntity<>(favouritesResponseDto, HttpStatus.NOT_FOUND);
		}
		favouritesResponseDto.setFavouritesList(matchList);
		favouritesResponseDto.setStatusCode(ApplicationConstants.USERPROFILE_SUCCESS_CODE);
		favouritesResponseDto.setMessage(ApplicationConstants.USERPROFILE_FAVOURITES_LIST);
		return new ResponseEntity<>(favouritesResponseDto, HttpStatus.OK);
	}

}
