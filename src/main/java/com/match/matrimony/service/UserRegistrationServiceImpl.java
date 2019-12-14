package com.match.matrimony.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.match.matrimony.constants.ApplicationConstants;
import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.entity.UserProfile;
import com.match.matrimony.exception.GeneralException;
import com.match.matrimony.repository.UserProfileRepository;
import com.match.matrimony.utils.ApplicationPropertyEncripter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {
	@Autowired
	UserProfileRepository userProfileRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public Optional<UserRegistrationResponseDto> matrimonyRegistration(
			UserRegistrationRequestDto userRegistrationRequestDto) throws GeneralException {

		Optional<UserProfile> userProfilenumber = userProfileRepository
				.findByMobile(userRegistrationRequestDto.getMobile());

		UserProfile userProfile = new UserProfile();
		log.info("In registration service method");
		Integer age = Period.between(userRegistrationRequestDto.getDateOfBirth(), LocalDate.now()).getYears();
		String gender = userRegistrationRequestDto.getGender();
		String password = userRegistrationRequestDto.getUserProfilePassword();

		String userProfilName = userRegistrationRequestDto.getUserProfileName();
		String userProfilePassword = userRegistrationRequestDto.getUserProfilePassword();

		userRegistrationRequestDto.setUserProfilePassword(ApplicationPropertyEncripter.passwordEncripter(password));
		BeanUtils.copyProperties(userRegistrationRequestDto, userProfile);

		if (!userProfilenumber.isPresent()) {
			if ((gender.equalsIgnoreCase("male") && (age <= ApplicationConstants.USER_MALE_AGE))
					|| (gender.equalsIgnoreCase("female") && (age <= ApplicationConstants.USER_FEMALE_AGE))) {
				log.info("registration service method validating age ");
				throw new GeneralException(ApplicationConstants.USER_REGISTRATION_MESSAGE);
			} else {
				log.info("registration service method validating age success");
				userProfile=userProfileRepository.save(userProfile);
				SimpleMailMessage simple = new SimpleMailMessage();
				simple.setTo(userRegistrationRequestDto.getEmail());
				simple.setFrom(ApplicationConstants.SKETCH_LIVES_FROM_GMAILID);
				simple.setSubject(ApplicationConstants.SKETCH_LIVES_GMAIL_SUBJECT);
				simple.setText(ApplicationConstants.SKETCH_LIVES_TEXT_ONE + ApplicationConstants.NEXT_LINE
						+ ApplicationConstants.SKETCH_LIVES_TEXT_TWO + ApplicationConstants.WHITE_SPACE + userProfilName
						+ ApplicationConstants.NEXT_LINE + ApplicationConstants.SKETCH_LIVES_TEXT_THREE
						+ userProfile.getUserProfileId() + ApplicationConstants.NEXT_LINE
						+ ApplicationConstants.WHITE_SPACE + ApplicationConstants.NEXT_LINE
						+ ApplicationConstants.SKETCH_LIVES_TEXT_FOUR + ApplicationConstants.WHITE_SPACE
						+ userProfilePassword);
				javaMailSender.send(simple);

			}

			UserRegistrationResponseDto userRegistrationResponseDto = new UserRegistrationResponseDto();
			userRegistrationResponseDto.setUserProfileId(userProfile.getUserProfileId());

			return Optional.of(userRegistrationResponseDto);
		} else {
			throw new GeneralException(ApplicationConstants.USER_REGISTRATION_MOBILE_MESSAGE);
		}
	}
}