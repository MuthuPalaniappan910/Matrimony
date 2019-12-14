package com.match.matrimony.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchProfileRequestDto {
	private Long userProfileId;
	private Long userMatchId;
	private String action;
}
