package com.match.matrimony.utils;

public class ApplicationPropertyEncripter {
	public static String passwordEncripter(String password) {
		String encriptedPassword = SingleTon.bCryptPasswordEncoder().encode(password);
		return encriptedPassword;
	}
}
