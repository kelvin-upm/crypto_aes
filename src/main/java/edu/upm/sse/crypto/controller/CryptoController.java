package edu.upm.sse.crypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.upm.sse.crypto.model.CryptoModel;
import edu.upm.sse.crypto.util.DBUtil;
import edu.upm.sse.crypto.util.EncryptUtil;

@RestController
public class CryptoController {
	
	@Autowired
	private DBUtil dbutil;
	
	@Autowired
	private EncryptUtil encryptutil;

	@GetMapping("/crypto/get/{key}")
	CryptoModel getRecord(@PathVariable String key) {
		return dbutil.getRecord(key);
	}
	
	@GetMapping("/crypto/get/decrypt/{key}")
	String getDecryptRecord(@PathVariable String key) {
		CryptoModel crypto = dbutil.getRecord(key);
		return encryptutil.decrypt(crypto);
	}

	@GetMapping("/crypto/get/all")
	List<CryptoModel> getAll() {
		return dbutil.getAll();
	}
	
	@PostMapping("/crypto/insert")
	String insertRecord(@RequestBody CryptoModel crypto) {
		
		crypto = encryptutil.encrypt(crypto);
		
		return dbutil.insertRecord(crypto);
	}
}
