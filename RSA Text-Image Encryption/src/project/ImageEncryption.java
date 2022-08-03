package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ImageEncryption {
	KeyGenerator keyGenerator=null;
	SecretKey secretKey=null;
	Cipher cipher=null;
	
	public ImageEncryption() {
		try {
			keyGenerator=KeyGenerator.getInstance("Blowfish");
			secretKey=keyGenerator.generateKey();
			cipher=Cipher.getInstance("Blowfish");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void encrypt(String srcPath,String destPath) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
		File rawFile=new File(srcPath);
		File encryptedFile=new File(destPath);
		InputStream inStream=null;
		OutputStream outStream=null;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
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
}
