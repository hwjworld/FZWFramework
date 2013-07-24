package com.fzw.utils;

import java.io.UnsupportedEncodingException;

import org.terracotta.license.util.Base64;

public class CodeUtil {

	public static String decodeBase64(String encodedString) {
		try {
			return new String(Base64.decode(encodedString), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return new String(Base64.decode(encodedString));
		}
	}

	public static void main(String[] args) {

	}
}
