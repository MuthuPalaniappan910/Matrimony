package com.match.matrimony.service;

import java.util.Optional;

import com.match.matrimony.entity.UserProfile;

public interface UserProfileService {

	Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword);

}
