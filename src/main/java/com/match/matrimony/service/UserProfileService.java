package com.match.matrimony.service;

import java.util.Optional;

import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.exception.UserProfileException;

public interface UserProfileService {
	Optional<UserProfileResponsedto> viewProfile(Long userProfileId) throws UserProfileException;
}
