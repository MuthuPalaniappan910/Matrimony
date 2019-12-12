package com.match.matrimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
	UserProfileRepository userProfileRepository;

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

}
