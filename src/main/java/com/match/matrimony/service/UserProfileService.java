package com.match.matrimony.service;

import java.util.List;
import java.util.Optional;

import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.Favourites;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.ProfileNotFoundException;
import com.match.matrimony.exception.UserProfileException;

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




	
	Optional<List<DashboardResponse>> matchList(Long userProfileId);

	Optional<UserProfileResponsedto> viewProfile(Long userProfileId) throws UserProfileException;

	List<Favourites> viewMatch(Long userMatchId) throws ProfileNotFoundException;
}
