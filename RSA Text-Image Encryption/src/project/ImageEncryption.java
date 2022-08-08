package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ImageEncryption {
	//KeyGenerator keyGenerator=null;
	//SecretKey secretKey=null;
	Cipher cipher=null;
	private String encodedKey="sg0SElprtqlRIykOvWC7zQ==";
	byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
    SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "Blowfish");
	
	
	public void encrypt(String srcPath,String destPath) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		cipher=Cipher.getInstance("Blowfish");
		File rawFile=new File(srcPath);
		File encryptedFile=new File(destPath);
		InputStream inStream=null;
		OutputStream outStream=null;
		cipher.init(Cipher.ENCRYPT_MODE,secretKey);
		inStream=new FileInputStream(rawFile);
		outStream=new FileOutputStream(encryptedFile);
		byte[] buffer=new byte[1024];
		int len;
		while((len=inStream.read(buffer))>0) {
			outStream.write(cipher.update(buffer,0,len));
			outStream.flush();
		}
		outStream.write(cipher.doFinal());
		inStream.close();
		outStream.close();
	}
	
	public void decrypt(String srcPath,String destPath) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchPaddingException {
		cipher=Cipher.getInstance("Blowfish");
		File encryptedFile=new File(srcPath);
		File decryptedFile=new File(destPath);
		InputStream inStream=null;
		OutputStream outStream=null;
		inStream=new FileInputStream(encryptedFile);
		outStream=new FileOutputStream(decryptedFile);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] buffer=new byte[1024];
		int len;
		while((len=inStream.read(buffer))>0) {
			outStream.write(cipher.update(buffer,0,len));
			outStream.flush();
		}
		outStream.write(cipher.doFinal());
		inStream.close();
		outStream.close();
		
	}
}
