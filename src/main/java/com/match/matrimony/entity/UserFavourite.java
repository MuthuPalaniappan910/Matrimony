package com.match.matrimony.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserFavourite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userFavouriteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userProfileId", nullable = false)
	private UserProfile userProfileId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userMatchId", nullable = false)
	private UserProfile userMatchId;
}
