package projekti2;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;

import javax.crypto.Cipher;
import javax.swing.JOptionPane;
public class GenerateKeys {
	
	public PublicKey publicKey;
	public PrivateKey privateKey;
	public RSAPublicKeySpec rsaPublicKey;
	public RSAPrivateKeySpec rsaPrivateKey;
	
	public void generate() {
		try {
	
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();
			KeyFactory factory = KeyFactory.getInstance("RSA");
			rsaPublicKey = factory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			rsaPrivateKey = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);			
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Gabimi:"+e.getMessage());
		}
	}
	
	public void saveToFile(String file,BigInteger modulus, BigInteger exponent ) throws IOException {
		ObjectOutputStream oos = 
				new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		try {
			oos.writeObject(modulus);
			oos.writeObject(exponent);
		}
		
		catch(Exception e) {
			throw new IOException(e.getMessage());
		}
		
		finally {
			oos.close();
		}
		
	}
}
