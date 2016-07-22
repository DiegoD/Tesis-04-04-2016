package com.vista;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 
{
	public  String getMD5Hash(String s) throws NoSuchAlgorithmException 
	{

		String result = s;
		if (s != null) {
		    MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
		    md.update(s.getBytes());
		    BigInteger hash = new BigInteger(1, md.digest());
		    result = hash.toString(16);
		    while (result.length() < 32) { // 40 for SHA-1
		        result = "0" + result;
		    }
		}
		return result; 
	}

}
