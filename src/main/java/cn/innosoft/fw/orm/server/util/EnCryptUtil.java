package cn.innosoft.fw.orm.server.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EnCryptUtil {

	public static String desMd5Encrypt(String pwd){
		try {
			return string2MD5( encrypt(pwd) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
    
	public static void main(String args[]) throws NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		String pwd = EnCryptUtil.decrypt("427A9EED3D693ACE14193F5EE5F7C954");
		String pwd2 = EnCryptUtil.encrypt("123456");
		System.out.println(pwd);
		System.out.println(pwd2);
	}

	/**
	 * DES加密
	 */
	public static String encrypt(String str) throws InvalidKeyException,
			NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException, NoSuchPaddingException,
			InvalidKeySpecException {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(1, key, sr);
		byte data[] = str.getBytes();
		byte encryptedData[] = cipher.doFinal(data);
		return byte2hex(encryptedData);
	}
	
	public static String byte2hex(byte b[]) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1)
				hs = (new StringBuilder(String.valueOf(hs))).append("0")
						.append(stmp).toString();
			else
				hs = (new StringBuilder(String.valueOf(hs))).append(stmp)
						.toString();
		}

		return hs.toUpperCase();
	}

	/**
	 * DES解密
	 */
	public static String decrypt(String str) throws IllegalBlockSizeException,
			BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(2, key, sr);
		byte encryptedData[] = hex2byte(str.getBytes());
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return new String(decryptedData);
	}

	

	public static byte[] hex2byte(byte b[]) {
		if (b.length % 2 != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte b2[] = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

	private static byte rawKeyData[] = "42811111".getBytes(Charset.forName("utf-8"));

}
