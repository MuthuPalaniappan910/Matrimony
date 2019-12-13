package com.match.matrimony.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.match.matrimony.entity.UserFavourite;
import com.match.matrimony.entity.UserProfile;

@Repository
public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Long> {

	List<UserFavourite> findAllByUserProfileId(UserProfile userProfile);
	
	List<UserFavourite> findAllByUserMatchId(UserProfile userProfile);

	

}
