package com.match.matrimony.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileException extends Exception{
	
	private static final long serialVersionUID = 2762077326060496312L;

	public UserProfileException(String exception) {
		super();
	}

}
