package edu.upm.sse.crypto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import edu.upm.sse.crypto.model.CryptoModel;

@Configuration
public class DBUtil {

	private static String urlConnection = "jdbc:derby:cryptodb;create=true";

	public DBUtil() throws SQLException {
		Connection con = DriverManager.getConnection(urlConnection);
		Statement statement = con.createStatement();
		String sql = "CREATE TABLE crypto (cryptokey VARCHAR(255),message VARCHAR(255), aeskey BLOB , aesiv BLOB )";
		statement.execute(sql);
		System.out.println("Table created");
	}

	public List<CryptoModel> getAll() {

		List<CryptoModel> crytoList = new ArrayList<>();
		
		try (Connection con = DriverManager.getConnection(urlConnection)) {
			Statement statement = con.createStatement();
			String sql = " SELECT * FROM crypto ";
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				CryptoModel crypto =  new CryptoModel();
				String cryptoKey = result.getString("cryptokey");
				String message = result.getString("message");
				byte[] aeskey = result.getBytes("aeskey");
				byte[] aesiv = result.getBytes("aesiv");
				
				crypto.setKey(cryptoKey);
				crypto.setEncryptMessage(message);
				crypto.setAesKey(aeskey);
				crypto.setAesIV(aesiv);
				
				crytoList.add(crypto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return crytoList;
	}
	
	
	public CryptoModel getRecord(String cryptoKey) {

		CryptoModel crypto = new CryptoModel();
		
		try (Connection con = DriverManager.getConnection(urlConnection)) {
			String sql = "SELECT * FROM crypto where cryptokey = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, cryptoKey);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String message = result.getString("message");
				byte[] aeskey = result.getBytes("aeskey");
				byte[] aesiv = result.getBytes("aesiv");
				crypto.setKey(cryptoKey);
				crypto.setEncryptMessage(message);
				crypto.setAesKey(aeskey);
				crypto.setAesIV(aesiv);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return crypto;
	}
	
	public String insertRecord(CryptoModel crypto) {

		try (Connection con = DriverManager.getConnection(urlConnection)) {
			String sql = "insert into crypto (cryptokey,message,aeskey,aesiv)values (?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, crypto.getKey());
			statement.setString(2, crypto.getEncryptMessage());
			statement.setBytes(3, crypto.getAesKey());
			statement.setBytes(4, crypto.getAesIV());
			
			statement.executeUpdate();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return "Error";
		}
		return crypto.getEncryptMessage();
	}
}
