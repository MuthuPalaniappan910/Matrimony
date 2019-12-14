package com.match.matrimony.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.repository.UserProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {
	@Autowired
	UserProfileRepository userProfileRepository;

	@Override
	public Optional<UserRegistrationResponseDto> matrimonyRegistration(
			UserRegistrationRequestDto userRegistrationRequestDto) throws GeneralException {

		Optional<UserProfile> userProfilenumber = userProfileRepository
				.findByMobile(userRegistrationRequestDto.getMobile());

		UserProfile userProfile = new UserProfile();
		log.info("In registration service method");
		Integer age = Period.between(userRegistrationRequestDto.getDateOfBirth(), LocalDate.now()).getYears();
		BeanUtils.copyProperties(userRegistrationRequestDto, userProfile);
		if (!userProfilenumber.isPresent()) {
			if (age <= ApplicationConstants.USER_AGE) {
				log.info("registration service method validating age ");
				throw new GeneralException(ApplicationConstants.USER_REGISTRATION_MESSAGE);
			} else {
				log.info("registration service method validating age success");
				userProfileRepository.save(userProfile);

			}
			UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
			userRegistrationResponseDto.setUserProfileId(userProfile.getUserProfileId());
			return Optional.of(userRegistrationResponseDto);
		} else {
			throw new GeneralException(ApplicationConstants.USER_REGISTRATION_MOBILE_MESSAGE);
		}
	}
}
