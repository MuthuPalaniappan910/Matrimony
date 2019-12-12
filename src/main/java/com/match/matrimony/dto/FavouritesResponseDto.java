package com.match.matrimony.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouritesResponseDto {
	private List<Favourites> favouritesList;
	private Integer statusCode;
	private String message;
}
