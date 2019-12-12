package com.match.matrimony.service;

import java.util.List;
import java.util.Optional;

import com.match.matrimony.dto.Favourites;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.ProfileNotFoundException;

public interface UserProfileService {
	/**
	 * @author Muthu
	 * 
	 * @param userProfileId
	 * @param userProfilePassword
	 * @return
	 */

	Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword);

	/**
	 * @author Muthu
	 * 
	 * @param userProfileId
	 * @return
	 * @throws ProfileNotFoundException
	 */

	List<Favourites> viewFavourites(Long userProfileId) throws ProfileNotFoundException;

}
