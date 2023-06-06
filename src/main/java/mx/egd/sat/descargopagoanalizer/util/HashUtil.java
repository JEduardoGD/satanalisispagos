package mx.egd.sat.descargopagoanalizer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class HashUtil {
	private static MessageDigest md;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String getHashOf(String s) {
		byte[] b = md.digest(s.getBytes());
		return new String(b);
	}
}
