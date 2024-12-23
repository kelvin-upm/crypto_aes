package edu.upm.sse.crypto.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Configuration;

import edu.upm.sse.crypto.model.CryptoModel;

@Configuration
public class EncryptUtil {

	private static String algorithm = "AES/CBC/PKCS5Padding";

	public CryptoModel encrypt(CryptoModel crypto) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);

			// Generate Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			SecretKey key = keyGenerator.generateKey();
			crypto.setAesKey(key.getEncoded());
			
			// Generate IV
			byte[] ivBytes = new byte[16];
			new SecureRandom().nextBytes(ivBytes);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			crypto.setAesIV(ivBytes);

			// Encrypt message
			String message = crypto.getMessage();
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] cipherText = cipher.doFinal(message.getBytes());
			String encryptedMessage = Base64.getEncoder().encodeToString(cipherText);
			crypto.setEncryptMessage(encryptedMessage);
			

			return crypto;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public String decrypt(CryptoModel crypto) {

		try {
			Cipher cipher = Cipher.getInstance(algorithm);

			SecretKey aesKey = new SecretKeySpec(crypto.getAesKey(), "AES");
			IvParameterSpec iv = new IvParameterSpec(crypto.getAesIV());

			cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
			byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(crypto.getEncryptMessage()));
			return new String(plainText);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
