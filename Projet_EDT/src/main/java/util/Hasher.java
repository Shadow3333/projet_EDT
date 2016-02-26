package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used for hash a message
 * @author Michael
 *
 */
public class Hasher {	
	public static String SHA256(byte[] bytes){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] mdbytes = md.digest();
        return Hexa.bytesToHex(mdbytes);
	}
	
	public static String SHA256(byte[] bytes, byte[] salt){
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			md.update(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] mdbytes = md.digest();
        return Hexa.bytesToHex(mdbytes);
	}
	
	public static String SHA256(String string) {
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        byte[] mdbytes = md.digest();
        return Hexa.bytesToHex(mdbytes);
    }
	
	public static String SHA256(String passWord, String salt){
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes("UTF-8"));
			md.update(passWord.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        byte[] mdbytes = md.digest();
        return Hexa.bytesToHex(mdbytes);
	}
}