package com.match.matrimony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;

@Repository
public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Long> {

	List<UserFavourite> findAllByUserProfileId(UserProfile userProfile);

	List<UserFavourite> findAllByUserMatchId(UserProfile userProfile);

	Optional<UserFavourite> findByUserMatchIdAndUserProfileId(UserProfile userMatch, UserProfile userProfile);

	List<UserFavourite> findByUserMatchId(Long userMatchId);

	List<UserFavourite> findByUserMatchId(UserProfile userProfile);

	void deleteByUserProfileIdAndUserMatchId(UserFavourite userFavourite, UserFavourite userFavourite2);

	Optional<UserFavourite> findByUserProfileIdAndUserMatchId(UserProfile userProfileId, Long userProfileId2);

	Optional<UserFavourite> findByUserProfileIdAndUserMatchId(UserProfile userProfile, UserProfile userMatchProfile);

	Optional<UserFavourite> findByUserMatchIdAndUserProfileId(Long userProfileId, UserProfile userProfile);



}
