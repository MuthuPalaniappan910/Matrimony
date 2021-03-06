package com.match.matrimony.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.FavouriteProfileRequestDto;
import com.match.matrimony.dto.FavouriteProfileResponsedto;
import com.match.matrimony.dto.Favourites;
import com.match.matrimony.dto.MatchProfileRequestDto;
import com.match.matrimony.dto.MatchProfileResponsedto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.dto.UserProfileSearchResponseDto;
import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.repository.UserFavouriteRepository;
import com.match.matrimony.repository.UserProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	UserFavouriteRepository userFavouriteRepository;

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

	@Override
	public List<Favourites> viewFavourites(Long userProfileId) throws ProfileNotFoundException {
		Optional<UserProfile> userProfile = userProfileRepository.findById(userProfileId);
		List<Favourites> favouritesList = new ArrayList<>();
		if (userProfile.isPresent()) {
			List<UserFavourite> userFavourite = userFavouriteRepository.findAllByUserProfileId(userProfile.get());
			if (!userFavourite.isEmpty()) {
				userFavourite.forEach(favList -> {
					Optional<UserProfile> userProfileResponse = userProfileRepository
							.findById(favList.getUserMatchId().getUserProfileId());
					if (userProfileResponse.isPresent()) {
						Favourites favourites = new Favourites();
						BeanUtils.copyProperties(userProfileResponse.get(), favourites);
						favouritesList.add(favourites);
					}
				});
				return favouritesList;
			}
			throw new ProfileNotFoundException(ApplicationConstants.PROFILE_NOT_FOUND);
		}
		throw new ProfileNotFoundException(ApplicationConstants.PROFILE_NOT_FOUND);
	}

	/**
	 * @author Bindushree
	 * @param userProfileId
	 * @return
	 */
	@Override
	public Optional<List<DashboardResponse>> matchList(Long userProfileId) {
		log.info("Entering into userProfile service matchList method");
		UserProfile userProfile = userProfileRepository.findByUserProfileId(userProfileId);
		List<UserProfile> profiles = userProfileRepository.findAllByUserProfileIdNot(userProfileId);
		List<DashboardResponse> responseList = new ArrayList<>();
		log.debug("Checking whether user profile list is present");
		if (!profiles.isEmpty()) {
			profiles.forEach(profile -> {
				DashboardResponse dashboardResponse = new DashboardResponse();

				log.debug("Checking gender of  user profile as female  and list of profiles with male");

				if (profile.getGender().equalsIgnoreCase("Male")
						&& userProfile.getGender().equalsIgnoreCase("Female")) {
					log.debug("Checking DateOfBirth of  user profile is before the list of profiles");
					if (profile.getDateOfBirth().isBefore(userProfile.getDateOfBirth())) {
						log.debug("Checking MotherTongue of  user profile  and list of profiles");
						BeanUtils.copyProperties(profile, dashboardResponse);
						responseList.add(dashboardResponse);
					}
				} else if (profile.getGender().equalsIgnoreCase("Female")
						&& userProfile.getGender().equalsIgnoreCase("Male")) {
					log.debug("Checking DateOfBirth of  user profile is after the list of profiles");
					if (profile.getDateOfBirth().isAfter(userProfile.getDateOfBirth())) {
						log.debug("Checking gender of  user profile  and list");
						BeanUtils.copyProperties(profile, dashboardResponse);
						responseList.add(dashboardResponse);
					}
				}

			});
		}

		return Optional.of(responseList);

	}

	/**
	 * Method to view the detailed profile view of the match that interests the
	 * user.
	 * 
	 * @author chethana
	 * @param userProfileId
	 * @return UserProfileResponsedto returns details of matching profile details if
	 *         present
	 * @throws UserProfileException throws if there is no record found for my
	 *                              interested match
	 */
	public Optional<UserProfileResponsedto> viewProfile(Long userProfileId) throws UserProfileException {
		log.info("Entering into viewProfile() of UserProfileServiceImpl");
		Optional<UserProfile> userProfileResponse = userProfileRepository.findById(userProfileId);
		if (!userProfileResponse.isPresent()) {
			log.error("Exception Occured in viewProfile() of UserProfileServiceImpl");
			throw new UserProfileException(ApplicationConstants.NO_PROFILE);
		}
		UserProfileResponsedto userProfileResponsedto = new UserProfileResponsedto();
		BeanUtils.copyProperties(userProfileResponse.get(), userProfileResponsedto);
		return Optional.of(userProfileResponsedto);
	}

	/**
	 * Method enables the user to login by entering the credentials to enjoy the
	 * service
	 * 
	 * @author Muthu
	 * @param loginRequestDto contains the userProfileId and userProfilePassword
	 * @return
	 */
	@Override
	public Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword) {
		UserProfile user = userProfileRepository.findByUserProfileId(userProfileId);
		String encriptedpassword = user.getUserProfilePassword();
		String decriptedPassword = encriptedpassword.toString();
		return userProfileRepository.findByUserProfileIdAndUserProfilePassword(userProfileId, decriptedPassword);
	}

	/**
	 * Method to add the match as his/her favourite profile.
	 * 
	 * @author chethana
	 * @param favouriteProfileRequestDto contains userprofileId and
	 *                                   InterestedMatchprofileId
	 * @return FavouriteProfileResponsedto
	 * @throws UserProfileException throws when failed to add the interested match
	 *                              to favourites
	 * 
	 */
	public Optional<FavouriteProfileResponsedto> addFavourite(FavouriteProfileRequestDto favouriteProfileRequestDto)
			throws UserProfileException {
		log.info("Entering into addFavourite() of UserProfileServiceImpl");
		UserProfile userProfile = userProfileRepository
				.findByUserProfileId(favouriteProfileRequestDto.getUserProfileId());

		if (!Objects.isNull(userProfile)) {
			UserProfile userMatchProfile = userProfileRepository
					.findByUserProfileId(favouriteProfileRequestDto.getUserMatchId());
			if (!Objects.isNull(userMatchProfile)) {

				Optional<UserFavourite> userFavouriteResponse = userFavouriteRepository
						.findByUserProfileIdAndUserMatchId(userProfile, userMatchProfile);

				if (userFavouriteResponse.isPresent()) {
					log.error("Exception occured in addFavourite of UserProfileServiceImpl: "
							+ ApplicationConstants.ALREADY_ADDED);
					throw new UserProfileException(ApplicationConstants.ALREADY_ADDED);
				}
				UserFavourite userFavourite = new UserFavourite();
				userFavourite.setUserMatchId(userMatchProfile);
				userFavourite.setUserProfileId(userProfile);
				userFavouriteRepository.save(userFavourite);
			} else {
				log.error("Exception occured in addFavourite of UserProfileServiceImpl: "
						+ ApplicationConstants.INVALID_MATCH);
				throw new UserProfileException(ApplicationConstants.INVALID_MATCH);
			}
		} else {
			log.error("Exception occured in addFavourite of UserProfileServiceImpl: "
					+ ApplicationConstants.INVALID_PROFILE);
			throw new UserProfileException(ApplicationConstants.INVALID_PROFILE);
		}
		return Optional.of(new FavouriteProfileResponsedto());
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
	@Override
	public List<Favourites> viewMatch(Long userMatchId) throws ProfileNotFoundException {
		Optional<UserProfile> userProfile = userProfileRepository.findById(userMatchId);
		List<Favourites> favouritesList = new ArrayList<>();
		if (userProfile.isPresent()) {
			List<UserFavourite> userFavourite = userFavouriteRepository.findAllByUserMatchId(userProfile.get());
			if (!userFavourite.isEmpty()) {
				userFavourite.forEach(favList -> {
					Optional<UserProfile> userProfileResponse = userProfileRepository
							.findById(favList.getUserProfileId().getUserProfileId());
					if (userProfileResponse.isPresent()) {
						Favourites favourites = new Favourites();
						BeanUtils.copyProperties(userProfileResponse.get(), favourites);
						favouritesList.add(favourites);
					}
				});
				return favouritesList;
			}
			throw new ProfileNotFoundException(ApplicationConstants.PROFILE_NOT_FOUND);
		}
		throw new ProfileNotFoundException(ApplicationConstants.PROFILE_NOT_FOUND);
	}

	/**
	 * @author Bindushree
	 * 
	 *         This method allows the user to accept his/her match requests
	 * 
	 * @param matchProfileRequestDto
	 * @return
	 * @throws UserProfileException
	 * @throws ProfileNotFoundException
	 */
	@Override
	public Optional<MatchProfileResponsedto> addMatch(MatchProfileRequestDto matchProfileRequestDto)
			throws ProfileNotFoundException {
		Optional<UserProfile> userMatchResponse = userProfileRepository
				.findById(matchProfileRequestDto.getUserMatchId());
		Optional<UserProfile> userProfileResponse = userProfileRepository
				.findById(matchProfileRequestDto.getUserProfileId());
		if (userMatchResponse.isPresent() && userMatchResponse.isPresent()) {
			List<UserFavourite> userMatch = userFavouriteRepository.findByUserMatchId(userMatchResponse.get());
			if (userMatch.isEmpty()) {
				throw new ProfileNotFoundException(ApplicationConstants.PROFILE_NOT_FOUND);
			}
			userMatch.forEach(matchList -> {
				Optional<UserFavourite> userProfile = userFavouriteRepository.findByUserMatchIdAndUserProfileId(
						matchList.getUserMatchId().getUserProfileId(), userProfileResponse.get());
				if (userProfile.isPresent()) {
					if (matchProfileRequestDto.getAction().equalsIgnoreCase("Reject")) {
						userFavouriteRepository.deleteByUserProfileIdAndUserMatchId(userProfile.get(),
								userProfile.get());
					}
					UserFavourite userFavourite = new UserFavourite();
					userFavourite.setUserProfileId(userMatchResponse.get());
					userFavouriteRepository.save(userFavourite);
				}
			});
		}
		return Optional.of(new MatchProfileResponsedto());
	}
	
	/**
	 * 
	 * @param salary
	 * @return
	 * @throws GeneralException
	 */
	public Optional<List<UserProfileSearchResponseDto>> searchBySalary(Double salary) throws GeneralException {

	List<UserProfile> userprofileList = userProfileRepository.findBySalaryGreaterThanEqual(salary).get();
	List<UserProfileSearchResponseDto> userProfileSearchResponseDtoList = new ArrayList<>();
	for (UserProfile userprofile : userprofileList) {
	UserProfileSearchResponseDto userProfileSearchResponseDto = new UserProfileSearchResponseDto();
	Integer age = Period.between(userprofile.getDateOfBirth(), LocalDate.now()).getYears();
	userProfileSearchResponseDto.setUserProfileId(userprofile.getUserProfileId());
	userProfileSearchResponseDto.setUserProfileName(userprofile.getUserProfileName());
	userProfileSearchResponseDto.setReligion(userprofile.getReligion());
	userProfileSearchResponseDto.setProfession(userprofile.getProfession());
	userProfileSearchResponseDto.setSalary(userprofile.getSalary());
	userProfileSearchResponseDto.setAge(age);
	userProfileSearchResponseDtoList.add(userProfileSearchResponseDto);
	}

	return Optional.of(userProfileSearchResponseDtoList);
	}
}
