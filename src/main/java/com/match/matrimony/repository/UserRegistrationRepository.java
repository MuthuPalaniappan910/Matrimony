package com.match.matrimony.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.match.matrimony.entity.UserProfile;

public interface UserRegistrationRepository extends JpaRepository<UserProfile, Long> {

}
