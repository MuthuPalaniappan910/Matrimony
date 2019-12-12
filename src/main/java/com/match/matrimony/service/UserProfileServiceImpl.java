package com.match.matrimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.DashboardResponse;
import com.match.matrimony.dto.UserProfileResponsedto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.UserProfileException;
import com.match.matrimony.repository.UserProfileRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
	UserProfileRepository userProfileRepository;

	/**
	 * @author Bindushree
	 * @param userProfileId
	 * @return
	 */
	@Override
	public Optional<List<DashboardResponse>> matchList(Long userProfileId) {
		log.info("Entering into userProfile service matchList method");
		UserProfile userProfile = userProfileRepository.findByUserProfileId(userProfileId);
		List<UserProfile> profiles = userProfileRepository.findAllByUserProfileIdNot(userProfileId);
		List<DashboardResponse> responseList = new ArrayList<>();
		log.debug("Checking whether user profile list is present");
		if (!profiles.isEmpty()) {
			profiles.forEach(profile -> {
				DashboardResponse dashboardResponse = new DashboardResponse();
				log.debug("Checking gender of  user profile as female  and list of profiles with male");
				if (profile.getGender().equalsIgnoreCase("Male")
						&& userProfile.getGender().equalsIgnoreCase("Female")) {
					log.debug("Checking DateOfBirth of  user profile is before the list of profiles");
					if (profile.getDateOfBirth().isBefore(userProfile.getDateOfBirth())) {
						log.debug("Checking MotherTongue of  user profile  and list of profiles");
						if (profile.getMotherTongue().equalsIgnoreCase(userProfile.getMotherTongue())) {
							BeanUtils.copyProperties(profile, dashboardResponse);
							responseList.add(dashboardResponse);
						}
					}
				} 
				else if (profile.getGender().equalsIgnoreCase("Female")
						&& userProfile.getGender().equalsIgnoreCase("Male")) {
					log.debug("Checking DateOfBirth of  user profile is after the list of profiles");
					if (profile.getDateOfBirth().isAfter(userProfile.getDateOfBirth())) {
						log.debug("Checking gender of  user profile  and list");
						if (profile.getMotherTongue().equalsIgnoreCase(userProfile.getMotherTongue())) {
							BeanUtils.copyProperties(profile, dashboardResponse);
							responseList.add(dashboardResponse);
						}
					}
				}

			});
		}
		

		return Optional.of(responseList);

	}

	
	/**
	 * @author chethana
	 * @param userProfileId
	 * @return
	 * @throws UserProfileException
	 */
	public Optional<UserProfileResponsedto> viewProfile(Long userProfileId) throws UserProfileException{
		Optional<UserProfile> userProfileResponse= userProfileRepository.findById(userProfileId);
		if(!userProfileResponse.isPresent()) {
			throw new UserProfileException(ApplicationConstants.NO_PROFILE);
		}
		UserProfileResponsedto userProfileResponsedto= new UserProfileResponsedto();
		BeanUtils.copyProperties(userProfileResponse.get(), userProfileResponsedto);
		return Optional.of(userProfileResponsedto);		
	}

	@Override
	public Optional<UserProfile> userLogin(Long userProfileId, String userProfilePassword) {
		return userProfileRepository.findByUserProfileIdAndUserProfilePassword(userProfileId,userProfilePassword);

	}
}
