package edu.upm.sse.crypto.model;

public class CryptoModel {
	
	private String key;
	private String message;
	private byte[] aesKey;
	private byte[] aesIV;
	private String encryptMessage;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public byte[] getAesKey() {
		return aesKey;
	}
	public void setAesKey(byte[] aesKey) {
		this.aesKey = aesKey;
	}
	public byte[] getAesIV() {
		return aesIV;
	}
	public void setAesIV(byte[] aesIV) {
		this.aesIV = aesIV;
	}
	public String getEncryptMessage() {
		return encryptMessage;
	}
	public void setEncryptMessage(String encryptMessage) {
		this.encryptMessage = encryptMessage;
	}
	



}
