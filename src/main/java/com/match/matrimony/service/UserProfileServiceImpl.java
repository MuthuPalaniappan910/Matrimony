package com.match.matrimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.DashboardResponse;
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
	public Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword) {
		return userProfileRepository.findByUserProfileIdAndUserProfilePassword(userProfileId, userProfilePassword);
	}

	@Override
	public List<Favourites> viewFavourites(Long userProfileId) throws ProfileNotFoundException {
		Optional<UserProfile> userProfile = userProfileRepository.findById(userProfileId);
		List<Favourites> favouritesList = new ArrayList();
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
		UserProfile userProfile = userProfileRepository.findByUserProfileId(userProfileId);
		List<UserProfile> profiles = userProfileRepository.findAllByUserProfileIdNot(userProfileId);
		List<DashboardResponse> responseList = new ArrayList<>();
		if (!profiles.isEmpty()) {
			for (UserProfile profile : profiles) {
				DashboardResponse dashboardResponse = new DashboardResponse();
				
				if (profile.getGender().equalsIgnoreCase("Male")
						&& userProfile.getGender().equalsIgnoreCase("Female")) {
					if (profile.getDateOfBirth().isBefore(userProfile.getDateOfBirth())) {
						if (profile.getMotherTongue().equalsIgnoreCase(userProfile.getMotherTongue())) {
							BeanUtils.copyProperties(profile, dashboardResponse);
							responseList.add(dashboardResponse);
						}
					}
				} else if (profile.getGender().equalsIgnoreCase("Female")
						&& userProfile.getGender().equalsIgnoreCase("Male")) {
					if (profile.getDateOfBirth().isAfter(userProfile.getDateOfBirth())) {
						if (profile.getMotherTongue().equalsIgnoreCase(userProfile.getMotherTongue())) {
							BeanUtils.copyProperties(profile, dashboardResponse);
							responseList.add(dashboardResponse);
						}
					}
				}

			}
		}

		return Optional.of(responseList);

	}

	
	/**
	 * Method to view the detailed profile view of the match that interests the user.
	 * @author chethana
	 * @param userProfileId
	 * @return
	 * @throws UserProfileException
	 */
	public Optional<UserProfileResponsedto> viewProfile(Long userProfileId) throws UserProfileException{
		log.info("Entering into viewProfile() of UserProfileServiceImpl");
		Optional<UserProfile> userProfileResponse= userProfileRepository.findById(userProfileId);
		if(!userProfileResponse.isPresent()) {
			log.info("Exception Occured in viewProfile() of UserProfileServiceImpl");
			throw new UserProfileException(ApplicationConstants.NO_PROFILE);
		}
		UserProfileResponsedto userProfileResponsedto= new UserProfileResponsedto();
		BeanUtils.copyProperties(userProfileResponse.get(), userProfileResponsedto);
		return Optional.of(userProfileResponsedto);		
	}

	@Override
	public List<Favourites> viewMatch(Long userMatchId) throws ProfileNotFoundException {
		Optional<UserProfile> userProfile = userProfileRepository.findById(userMatchId);
		List<Favourites> favouritesList = new ArrayList();
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

	
}
