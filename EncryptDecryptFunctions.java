package projekti2;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

public class EncryptFunctions {
	
	public byte[] encrypt(byte[] data,int startIndex,int endIndex,boolean oldKeys,GenerateKeys keys){
		byte[] encryptedData = null;
		try {
			PublicKey pbk = null;
			if(oldKeys) {
				Keys objPublicKey = new Keys();
				pbk = objPublicKey.getPublicKey("public.key");
			}
			else {
				pbk = keys.publicKey;
			}
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE,pbk);
			encryptedData = cipher.doFinal(data,startIndex,endIndex);
		}
		catch(IOException|NoSuchPaddingException| NoSuchAlgorithmException | InvalidKeyException 
				| BadPaddingException |IllegalBlockSizeException e) {
			JOptionPane.showMessageDialog(null, "Encryption "+e.getMessage());
		}
		return encryptedData;
	}
	
	public byte[] decrypt(byte[] data,int startIndex,int endIndex,boolean oldKeys,GenerateKeys keys){
		byte[] decryptedData = null;
		try {
			PrivateKey prk = null;
			if(oldKeys) {
				Keys objPrivateKey = new Keys();
				prk = objPrivateKey.getPrivateKey("private.key");
			}
			else {
				prk = keys.privateKey;
				
			}
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE,prk);
			decryptedData = cipher.doFinal(data,startIndex,endIndex);
		}
		catch(IOException|NoSuchPaddingException| NoSuchAlgorithmException | InvalidKeyException 
				| BadPaddingException |IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return decryptedData;
	}
	
	
}
