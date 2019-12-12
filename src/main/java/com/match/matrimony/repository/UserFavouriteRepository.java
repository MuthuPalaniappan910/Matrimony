package com.match.matrimony.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;

@Repository
public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Long> {

	Optional<UserFavourite> findByUserProfileIdAndUserMatchId(UserProfile userProfile, UserProfile userMatchProfile);

}
