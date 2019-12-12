package com.match.matrimony.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
	UserProfileRepository userProfileRepository;
}
