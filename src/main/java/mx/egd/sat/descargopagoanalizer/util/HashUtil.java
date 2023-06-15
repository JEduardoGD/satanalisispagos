package mx.egd.sat.descargopagoanalizer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HashUtil {
	private static MessageDigest md;

	static {
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
	}

	public static String getHashOf(String s) {
		byte[] b = md.digest(s.getBytes());
		return new String(b);
	}
}
