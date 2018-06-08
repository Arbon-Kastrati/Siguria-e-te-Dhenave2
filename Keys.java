package projekti2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class Keys  {

	public PublicKey getPublicKey(String fileName) throws IOException{
		PublicKey  pubKey = null;
		ObjectInputStream oos= new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
		try {
			BigInteger modulus = (BigInteger)oos.readObject();
			BigInteger publicExponent = (BigInteger)oos.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus,publicExponent);
			KeyFactory keyFac = KeyFactory.getInstance("RSA");
			pubKey = keyFac.generatePublic(keySpec);
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			oos.close();
		}
		return pubKey;
	}
	
	public PrivateKey getPrivateKey(String fileName) throws IOException{
		PrivateKey  prKey = null;
		ObjectInputStream oos= new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
		try {
			BigInteger modulus = (BigInteger)oos.readObject();
			BigInteger privateExponent = (BigInteger)oos.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus,privateExponent);
			KeyFactory keyFac = KeyFactory.getInstance("RSA");
			prKey = keyFac.generatePrivate(keySpec);
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			oos.close();
		}
		return prKey;
	}

}
