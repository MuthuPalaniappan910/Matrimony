package com.match.matrimony.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.match.matrimony.entity.UserFavourite;

@Repository
public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Long> {

}
