package com.match.matrimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.Favourites;
import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.repository.UserFavouriteRepository;
import com.match.matrimony.repository.UserProfileRepository;

@Service
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
		Optional<UserProfile> userProfile = userProfileRepository.findByUserProfileId(userProfileId);
		List<Favourites> favouritesList = new ArrayList();
		if (userProfile.isPresent()) {
			List<UserFavourite> userFavourite = userFavouriteRepository.findAllByUserProfileId(userProfile.get());
			if (!userFavourite.isEmpty()) {
				userFavourite.forEach(favList -> {
					Optional<UserProfile> userProfileResponse = userProfileRepository
							.findByUserProfileId(favList.getUserMatchId().getUserProfileId());
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
