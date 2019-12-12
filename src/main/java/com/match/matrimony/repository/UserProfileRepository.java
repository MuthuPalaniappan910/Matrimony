package com.match.matrimony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.match.matrimony.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	UserProfile findByUserProfileId(Long userProfileId);
	
	List<UserProfile> findAllByUserProfileIdNot(Long userProfileId);

	Optional<UserProfile> findByUserProfileIdAndUserProfilePassword(Long userProfileId, String userProfilePassword);

}
