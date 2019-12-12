package com.match.matrimony.service;

import java.util.List;
import java.util.Optional;

import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.FavouriteProfileRequestDto;
import com.match.matrimony.dto.FavouriteProfileResponsedto;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.UserProfileException;


public interface UserProfileService {
	
	Optional<List<DashboardResponse>> matchList(Long userProfileId);

	Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword);
	Optional<UserProfileResponsedto> viewProfile(Long userProfileId) throws UserProfileException;

	Optional<FavouriteProfileResponsedto> addFavourite(FavouriteProfileRequestDto favouriteProfileRequestDto) throws UserProfileException;
}
