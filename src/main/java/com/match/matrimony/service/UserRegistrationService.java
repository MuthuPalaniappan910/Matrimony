package com.match.matrimony.service;

import java.util.Optional;

import com.match.matrimony.dto.UserRegistrationRequestDto;
import com.match.matrimony.dto.UserRegistrationResponseDto;
import com.match.matrimony.exception.GeneralException;

public interface UserRegistrationService {
	Optional<UserRegistrationResponseDto> matrimonyRegistration(UserRegistrationRequestDto raiseClaimRequestDto) throws GeneralException;
}
