package com.match.matrimony.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SingleTon {
	
	private SingleTon() {
	}
	private static BCryptPasswordEncoder bCryptPasswordEncoder = null;

	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		if (bCryptPasswordEncoder == null) {
			bCryptPasswordEncoder = new BCryptPasswordEncoder();
		}
		return bCryptPasswordEncoder;
	}

}