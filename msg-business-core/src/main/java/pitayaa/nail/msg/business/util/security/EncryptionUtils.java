package pitayaa.nail.msg.business.util.security;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class EncryptionUtils {
	  public  static String encodeMD5(String pass, String salt) throws Exception
	    {
		  	String password=salt+pass;
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());

	        byte byteData[] = md.digest();

	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }

	        System.out.println("Digest(in hex format):: " + sb.toString());

	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	System.out.println("Digest(in hex format):: " + hexString.toString());

	    	return hexString.toString();

	    }
	public static final String encryptBlowfish(String data, String key) {
		String rsl = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(),
					"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] rawData = cipher.doFinal(data.getBytes());
			if (rawData != null)
				// Encode bytes to base64 to get a string
				rsl = Base64.encodeBase64String(rawData);
		} catch (Exception e) {
		}
		rsl = rsl.replace("\r", "");
		rsl = rsl.replace("\n", "");
		return rsl;
	}

	public static final String decryptBlowfish(String data, String key) {
		String rsl = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(),
					"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			// Decode base64 to get bytes
			byte[] dec = Base64.decodeBase64(data);
			byte[] rawData = cipher.doFinal(dec);
			if (rawData != null)
				rsl = new String(rawData);
		} catch (Exception e) {
		}
		return rsl;
	}

	public static final String encryptAES(String data, String key) {
		String rsl = null;
		try {
			byte[] keyData = Base64.decodeBase64(key);
			SecretKeySpec skeySpec = new SecretKeySpec(keyData, "AES");
			// Instantiate the cipher
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] rawData = cipher.doFinal(data.getBytes());
			if (rawData != null) {
				// Encode bytes to base64 to get a string
				rsl = Base64.encodeBase64String(rawData);
			}
		} catch (Exception e) {
		}

		return rsl;
	}

	public static final String decryptAES(String data, String key) {
		String rsl = null;
		try {
			byte[] keyData = Base64.decodeBase64(key);
			SecretKeySpec skeySpec = new SecretKeySpec(keyData, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			// Decode base64 to get bytes
			byte[] dec = Base64.decodeBase64(data);
			byte[] rawData = cipher.doFinal(dec);
			if (rawData != null)
				rsl = new String(rawData);
		} catch (Exception e) {
		}
		return rsl;
	}

	/*public static final PublicKey loadPublicKey_PEM_X509_RSA(String filePath)
			throws GeneralSecurityException, IOException {
		PEMReader reader = new PEMReader(filePath);
		byte[] keyBytes = reader.getDerBytes();

		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = factory.generatePublic(publicKeySpec);
		return pubKey;
	}*/

	public static RSAPublicKey loadPublicKey_X509_RSA(String fileName)
			throws GeneralSecurityException, IOException {
		File publicKeyFile = new File(fileName);
		byte[] keyBytes = new byte[(int) publicKeyFile.length()];
		FileInputStream fis = new FileInputStream(publicKeyFile);
		fis.read(keyBytes);

		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey) factory
				.generatePublic(publicKeySpec);
		fis.close();
		return pubKey;
	}

	/*public static PrivateKey loadPrivateKey_PKCS1_RSA(String filePath)
			throws GeneralSecurityException, IOException {
		PEMReader reader = new PEMReader(filePath);
		byte[] keyBytes = reader.getDerBytes();

		PKCS1EncodedKeySpec spec = new PKCS1EncodedKeySpec(keyBytes);
		RSAPrivateCrtKeySpec privSpec = spec.getKeySpec();
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privKey = keyFactory.generatePrivate(privSpec);
		return privKey;
	}*/

	public static PrivateKey loadPrivateKey_PKCS8_RSA(String filePath)
			throws GeneralSecurityException, IOException {
		File privateKeyFile = new File(filePath);
		byte[] keyBytes = new byte[(int) privateKeyFile.length()];
		FileInputStream fis = new FileInputStream(privateKeyFile);
		fis.read(keyBytes);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privKey = keyFactory.generatePrivate(spec);
		fis.close();
		return privKey;
	}

	public static String encryptRSA(byte[] dataToEncrypt, RSAPublicKey key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		cipher.init(1, key);

		int keySize = key.getModulus().bitLength() / 8;
		int maxLength = keySize - 42;

		int dataLength = dataToEncrypt.length;
		int iterations = dataLength / maxLength;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= iterations; i++) {
			byte[] tempBytes = new byte[dataLength - maxLength * i > maxLength ? maxLength
					: dataLength - maxLength * i];
			System.arraycopy(dataToEncrypt, maxLength * i, tempBytes, 0,
					tempBytes.length);
			byte[] encryptedBytes = cipher.doFinal(tempBytes);
			sb.append(Base64.encodeBase64String(encryptedBytes));
		}

		String sEncrypted = sb.toString();
		sEncrypted = sEncrypted.replace("\r", "");
		sEncrypted = sEncrypted.replace("\n", "");
		return sEncrypted;
	}

	public static String decryptRSA(String dataEncrypted, RSAPrivateKey key)
			throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(1000);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		cipher.init(2, key);

		int dwKeySize = key.getModulus().bitLength();
		int base64BlockSize = dwKeySize / 8 % 3 != 0 ? dwKeySize / 8 / 3 * 4
				+ 4 : dwKeySize / 8 / 3 * 4;
		int iterations = dataEncrypted.length() / base64BlockSize;

		for (int i = 0; i < iterations; i++) {
			String sTemp = dataEncrypted.substring(base64BlockSize * i,
					base64BlockSize * i + base64BlockSize);
			byte[] bTemp = Base64.decodeBase64(sTemp);
			byte[] encryptedBytes = cipher.doFinal(bTemp);
			bb.put(encryptedBytes);
		}
		byte[] bDecrypted = bb.array();
		String dataToReturn = new String(bDecrypted, "UTF-8").trim();
		dataToReturn = dataToReturn.replace("\0", "");
		return dataToReturn;
	}

	public static String decrypt(String source, Key key) {
		try {
			// Get our secret key
			// Key key = getKey();

			// Create the cipher
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Encrypt the cleartext
			byte[] ciphertext = getBytes(source);

			// Initialize the same cipher for decryption
			desCipher.init(Cipher.DECRYPT_MODE, key);

			// Decrypt the ciphertext
			byte[] cleartext = desCipher.doFinal(ciphertext);

			// Return the clear text
			return new String(cleartext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encrypt(String source, Key key) {
		try {
			// Get our secret key
			// Key key = getKey();

			// Create the cipher
			Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Initialize the cipher for encryption
			desCipher.init(Cipher.ENCRYPT_MODE, key);

			// Our cleartext as bytes
			byte[] cleartext = source.getBytes();

			// Encrypt the cleartext
			byte[] ciphertext = desCipher.doFinal(cleartext);

			// Return a String representation of the cipher text
			return getString(ciphertext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] getBytes(String str) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StringTokenizer st = new StringTokenizer(str, "-", false);
		while (st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken());
			bos.write((byte) i);
		}
		return bos.toByteArray();
	}

	private static String getString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			sb.append((int) (0x00FF & b));
			if (i + 1 < bytes.length) {
				sb.append("-");
			}
		}
		return sb.toString();
	}

	public static String md5(String s) throws NoSuchAlgorithmException {
		byte[] defaultBytes = s.getBytes();

		try {
			MessageDigest algorithm;
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}

	private static final String ENC_KEY = "191-251-228-45-195-23-211-153";

	public static Key getKey() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			StringTokenizer st = new StringTokenizer(ENC_KEY, "-", false);

			while (st.hasMoreTokens()) {
				int i = Integer.parseInt(st.nextToken());
				bos.write((byte) i);
			}

			byte[] bytes = bos.toByteArray();

			DESKeySpec pass = new DESKeySpec(bytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey s = skf.generateSecret(pass);

			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// --------------------------------------------------------------------------------
	static RSAPrivateKey loadRSAPrivateKey(String filename) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		File file = new File(filename);
		byte[] b = fullyReadFile(file);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);

		KeyFactory factory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) factory.generatePrivate(spec);
	}

	static RSAPublicKey loadRSAPublicKey(String filename) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		File file = new File(filename);
		byte[] b = fullyReadFile(file);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(b);

		KeyFactory factory = KeyFactory.getInstance("RSA");
		return (RSAPublicKey) factory.generatePublic(spec);
	}

	private static byte[] fullyReadFile(File file) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		byte[] bytesOfFile = new byte[(int) file.length()];
		dis.readFully(bytesOfFile);
		dis.close();
		return bytesOfFile;
	}

	static void SaveEncodedKey(String filename, Key key) throws IOException {
		if (null == key) {
			throw new IllegalArgumentException("key is null.");
		}

		FileOutputStream fos = new FileOutputStream(filename);

		// PKCS #8 for Private, X.509 for Public
		// File will contain OID 1.2.840.11359.1.1.1 (RSA)
		// http://java.sun.com/j2se/1.4.2/docs/api/java/security/Key.html
		fos.write(key.getEncoded());

		fos.close();

	}

	public static void genRSAKey(int keySize, String publicKeyFilePath,
			String privateKeyFilePath) throws Exception {
		// http://java.sun.com/j2se/1.4.2/docs/guide/security/CryptoSpec.html
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

		// Initialize
		kpg.initialize(keySize, new SecureRandom());
		KeyPair keys = kpg.generateKeyPair();

		RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();

		// Serialize Keys
		SaveEncodedKey(privateKeyFilePath, privateKey);
		SaveEncodedKey(publicKeyFilePath, publicKey);
	}

/*	public static void main(String[] args) throws Exception {
		final String PRIVATE_KEY_FILE = "private.rsa.java.key";
		final String PUBLIC_KEY_FILE = "public.rsa.java.key";

		// http://java.sun.com/j2se/1.4.2/docs/guide/security/CryptoSpec.html
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

		// Initialize
		kpg.initialize(1024, new SecureRandom());
		KeyPair keys = kpg.generateKeyPair();

		RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();

		// Print Parameters
		PrintPrivateKey(privateKey);
		PrintPublicKey(publicKey);

		// Serialize Keys
		SaveEncodedKey(PRIVATE_KEY_FILE, privateKey);
		SaveEncodedKey(PUBLIC_KEY_FILE, publicKey);

		// PrivateKey privateKey = LoadPrivateKey("private.java.key");
		privateKey = loadRSAPrivateKey(PRIVATE_KEY_FILE);
		PrintPrivateKey(privateKey);

		// PublicKey publicKey = LoadPublicKey("public.java.key");
		publicKey = loadRSAPublicKey(PUBLIC_KEY_FILE);
		PrintPublicKey(publicKey);
	}*/

	static void PrintPrivateKey(RSAPrivateKey key) {
		if (null == key) {
			throw new IllegalArgumentException("key is null.");
		}

		System.out.print("Private Key ");
		System.out.println("(" + key.getFormat() + ")");
		System.out.println(" d: " + key.getPrivateExponent());
		System.out.println(" n: " + key.getModulus());

		System.out.println();
	}

	static void PrintPublicKey(RSAPublicKey key) {
		if (null == key) {
			throw new IllegalArgumentException("key is null.");
		}

		System.out.print("Public Key ");
		System.out.println("(" + key.getFormat() + ")");
		System.out.println(" e: " + key.getPublicExponent());
		System.out.println(" n: " + key.getModulus());

		System.out.println();
	}
}