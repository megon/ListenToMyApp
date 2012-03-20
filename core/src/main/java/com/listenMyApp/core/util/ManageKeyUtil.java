package com.listenMyApp.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class ManageKeyUtil {

	
	public static String createKey(final String id, final String internalKey){
		
		final StringBuffer hexString = new StringBuffer();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.reset();
			md.update(id.getBytes());
			md.update(internalKey.getBytes());
			byte[] messageDigest = md.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
		} catch (NoSuchAlgorithmException e) {
			//This catch does not thrown/handle this exception because the algorithm SHA1 exist on the java security API.
		}
		return hexString.toString();
	}
}
