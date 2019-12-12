package com.match.matrimony.service;

import java.util.List;
import java.util.Optional;

import com.match.matrimony.dto.DashboardResponse;

public interface UserProfileService {
	
	Optional<List<DashboardResponse>> matchList(Long userProfileId);

}
