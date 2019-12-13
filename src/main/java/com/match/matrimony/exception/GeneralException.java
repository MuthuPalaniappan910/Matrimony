package com.match.matrimony.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralException extends Exception{

	private static final long serialVersionUID = 1L;

	public GeneralException(String s) {
		super(s);
	}
}
