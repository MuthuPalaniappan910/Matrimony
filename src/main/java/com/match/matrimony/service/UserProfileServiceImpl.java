package com.match.matrimony.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
	UserProfileRepository userProfileRepository;

	@Override
	public Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword) {
		return userProfileRepository.findByUserProfileIdAndUserProfilePassword(userProfileId,userProfilePassword);
	}
}
