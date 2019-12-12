package com.match.matrimony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoMatchListFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoMatchListFoundException(String exception) {
		super(exception);
	}
}


	
	
