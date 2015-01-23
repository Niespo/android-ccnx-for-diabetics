package pl.androidland.studia.tirt.diabetichelper.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncryptionUtils {
    public static String generateMd5Hash(String encrypted) {
        byte[] decryptedString = new byte[0];
        try {
            byte[] bytesOfMessage = encrypted.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            decryptedString = md.digest(bytesOfMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(decryptedString);
    }
}
