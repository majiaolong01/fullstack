package com.lanxin.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


public class AccessToken {
	
	public static String createToken(String user_id,long timeStamp) {
		System.out.println(user_id);
		System.out.println(timeStamp);
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String originstr=user_id+':'+timeStamp;
		byte[] results = md5.digest(originstr.getBytes());
		String assess_token = Base64.encodeBase64String(results);
		return assess_token;
	}
	
}
