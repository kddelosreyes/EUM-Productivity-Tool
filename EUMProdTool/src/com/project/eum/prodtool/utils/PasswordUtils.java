package com.project.eum.prodtool.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author khdelos
 *
 */
public class PasswordUtils {

	private static final Random random = new SecureRandom();
	private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int iterations = 10000;
	private static final int keylength = 256;

	public static String getSaltvalue(int length) {
		StringBuilder finalValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			finalValue.append(characters.charAt(random.nextInt(characters.length())));
		}

		return new String(finalValue);
	}

	public static byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keylength);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}

	public static String generateSecurePassword(String password, String salt) {
		String finalValue = null;

		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

		finalValue = Base64.getEncoder().encodeToString(securePassword);

		return finalValue;
	}

	public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
		String newSecurePassword = generateSecurePassword(providedPassword, salt);

		return newSecurePassword.equalsIgnoreCase(securedPassword);
	}

}
