package pitayaa.nail.msg.business.util.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/* Have not used 
 * 
 */
public class RSAEncrypt {
	private static PublicKey pubKey = null;
	private static PrivateKey priKey = null;
	
	private static RSAEncrypt instance;
	
	private RSAEncrypt() throws Exception {
//		RSAPublicKey publicKey = EncryptionUtils.loadRSAPublicKey(Configuration.getRSAPublicKeyPath());
//		RSAPrivateKey privateKey = EncryptionUtils.loadRSAPrivateKey(Configuration.getRSAPrivateKeyPath());
		
		//pubKey = EncryptionUtils.loadPublicKey_PEM_X509_RSA(Configuration.getRSAPublicKeyPath());
		//priKey = EncryptionUtils.loadPrivateKey_PKCS1_RSA(Configuration.getRSAPrivateKeyPath());
	}
	
	public static RSAEncrypt getInstance() throws Exception {
		if(instance == null){
			instance = new RSAEncrypt();
		} 
		
		return instance;
	}
	
	public String decrypt(String value) throws Exception {
//		return value == null ? null : EncryptionUtils.decryptRSA(value, privateKey);
		return value == null ? null : EncryptionUtils.decryptRSA(value, (RSAPrivateKey) priKey);
		
	}

	public String encrypt(String value) throws Exception {
//		return value == null ? null : EncryptionUtils.encryptRSA(value.getBytes(), publicKey);
		return value == null ? null : EncryptionUtils.encryptRSA(value.getBytes(), (RSAPublicKey)pubKey);
	}
}
