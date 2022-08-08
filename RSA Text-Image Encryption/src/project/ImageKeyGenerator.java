package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ImageKeyGenerator {
	KeyGenerator keyGenerator=null;
	SecretKey secretKey=null;
	Cipher cipher=null;
	
	public ImageKeyGenerator() throws IOException {
		try {
			keyGenerator=KeyGenerator.getInstance("Blowfish");
			secretKey=keyGenerator.generateKey();
			cipher=Cipher.getInstance("Blowfish");
		}catch(Exception e) {
			System.out.println(e);
		}
		byte[] rawData = secretKey.getEncoded();
	    String encodedKey = Base64.getEncoder().encodeToString(rawData);
	    File f=new File("RSA/ImageKey");
	    FileWriter fw=new FileWriter(f);
		fw.write(encodedKey);
		fw.flush();
		fw.close();
	}
	
	public static void main(String[] args) throws IOException {
		ImageKeyGenerator ob=new ImageKeyGenerator();
	}
}
