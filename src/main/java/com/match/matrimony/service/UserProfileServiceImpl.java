package com.match.matrimony.service;

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
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;
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
	 * @return
	 * @throws UserProfileException
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

	@Override
	public Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword) {
		return userProfileRepository.findByUserProfileIdAndUserProfilePassword(userProfileId, userProfilePassword);

	}

	/**
	 * Method to add the match as his/her favourite profile.
	 * 
	 * @author chethana
	 * @param favouriteProfileRequestDto contains userprofileId and
	 *                                   InterestedMatchprofileId
	 * @return FavouriteProfileResponsedto
	 * @throws UserProfileException
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
					log.error("Exception occured in addFavourite of UserProfileServiceImpl: "+ApplicationConstants.ALREADY_ADDED);
					throw new UserProfileException(ApplicationConstants.ALREADY_ADDED);
				}
				UserFavourite userFavourite = new UserFavourite();
				userFavourite.setUserMatchId(userMatchProfile);
				userFavourite.setUserProfileId(userProfile);
				userFavouriteRepository.save(userFavourite);
			} else {
				log.error("Exception occured in addFavourite of UserProfileServiceImpl: "+ApplicationConstants.INVALID_MATCH);
				throw new UserProfileException(ApplicationConstants.INVALID_MATCH);
			}
		} else {
			log.error("Exception occured in addFavourite of UserProfileServiceImpl: "+ApplicationConstants.INVALID_PROFILE);
			throw new UserProfileException(ApplicationConstants.INVALID_PROFILE);
		}
		return Optional.of(new FavouriteProfileResponsedto());
	}

}
