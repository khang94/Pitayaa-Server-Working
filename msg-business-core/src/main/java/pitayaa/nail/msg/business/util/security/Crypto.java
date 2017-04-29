package pitayaa.nail.msg.business.util.security;

/**
 * Class Crypto
 * Securing Your Website Payments
 * @author ngocnt17
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Chua dung
 * 
 * @author thuattq
 *
 */
public class Crypto {

	private static byte[] decodeBase64(String dataToDecode) {
		BASE64Decoder b64d = new BASE64Decoder();
		byte[] bDecoded = null;
		try {
			bDecoded = b64d.decodeBuffer(dataToDecode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bDecoded;
	}

	/**
	 * decrypt Data Using Private Key File or PEM String Private Key
	 *
	 * @param dataEncrypted
	 *            Encrypted Data
	 * @param privateKey
	 *            The file path to the Private Key or PEM String Key
	 * @param isFile
	 *            Private Key is a file or PEM String?
	 * @return Decrypted String
	 */
	public static String decrypt(String dataEncrypted, String privateKey,
			Boolean isFile) throws Exception {
		RSAPrivateKey _privateKey = loadPrivateKey(privateKey, isFile);
		// Cipher cipher =
		// Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, _privateKey);

		dataEncrypted = dataEncrypted.replace("\r", "");
		dataEncrypted = dataEncrypted.replace("\n", "");
		int dwKeySize = _privateKey.getModulus().bitLength();
		int base64BlockSize = ((dwKeySize / 8) % 3 != 0) ? (((dwKeySize / 8) / 3) * 4) + 4
				: ((dwKeySize / 8) / 3) * 4;
		int iterations = dataEncrypted.length() / base64BlockSize;
		ByteBuffer bb = ByteBuffer.allocate(1000);
		for (int i = 0; i < iterations; i++) {
			String sTemp = dataEncrypted.substring(base64BlockSize * i,
					base64BlockSize * i + base64BlockSize);
			byte[] bTemp = decodeBase64(sTemp);

			// Be aware the RSACryptoServiceProvider reverses the order of
			// encrypted bytes after encryption and before decryption.
			// If you do not require compatibility with Microsoft Cryptographic
			// API (CAPI) and/or other vendors.
			// Comment out the next line and the corresponding one in the
			// EncryptString function.
			bTemp = reverse(bTemp);
			byte[] encryptedBytes = cipher.doFinal(bTemp);
			bb.put(encryptedBytes);
		}
		byte[] bDecrypted = bb.array();
		return new String(bDecrypted).trim();
	}

	private static byte[] DERtoPEM(byte[] bytes, String headfoot) {
		ByteArrayOutputStream pemStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(pemStream);

		byte[] stringBytes = encodeBase64(bytes).getBytes();
		String encoded = new String(stringBytes);
		encoded = encoded.replace("\r", "");
		encoded = encoded.replace("\n", "");

		if (headfoot != null) {
			writer.print("-----BEGIN " + headfoot + "-----\n");
		}

		// write 64 chars per line till done
		int i = 0;
		while ((i + 1) * 64 <= encoded.length()) {
			writer.print(encoded.substring(i * 64, (i + 1) * 64));
			writer.print("\n");
			i++;
		}
		if (encoded.length() % 64 != 0) {
			writer.print(encoded.substring(i * 64)); // write remainder
			writer.print("\n");
		}
		if (headfoot != null) {
			writer.print("-----END " + headfoot + "-----\n");
		}
		writer.flush();
		return pemStream.toByteArray();
	} // DERtoPEM

	private static String encodeBase64(byte[] dataToEncode) {
		BASE64Encoder b64e = new BASE64Encoder();
		String strEncoded = "";
		try {
			strEncoded = b64e.encode(dataToEncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strEncoded;
	}

	// public static boolean verify(String dataToVerify, String signedData)
	// throws Exception {
	// RSAPublicKey _publicKey = loadPublicKey("rsa-kk-public.pem", true);
	// Signature signature = Signature.getInstance("SHA1withRSA");
	// signature.initVerify(_publicKey);
	// signature.update(dataToVerify.getBytes(), 0,
	// dataToVerify.getBytes().length);
	// byte[] bSign = decodeBase64(signedData);
	// boolean pass = signature.verify(bSign);
	//
	// return pass;
	//
	// }

	/**
	 * encrypt data using RSA Public Key
	 *
	 * @param dataToEncrypt
	 *            The data to encrypt
	 * @param pubCer
	 *            The file path to the Public Certificate or PEM String
	 *            Certificate
	 * @param isFile
	 *            Private Key is a file or PEM String?
	 * @return Encrypted String (Base64 Encoded)
	 */

	static PublicKey readPubKeyFromFile(String keyFileName) throws IOException {
		PublicKey publicKey = null;
		try {
			InputStream in = new FileInputStream(keyFileName);
			PEMReader rd = new PEMReader(in);
			byte[] encodedPublicKey = rd.getDerBytes();
			in.close();
			KeyFactory fact = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					encodedPublicKey);
			publicKey = fact.generatePublic(publicKeySpec);
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		}
		return publicKey;
	}

	public static String encrypt(String dataToEncrypt, String pubCer,
			Boolean isFile) throws Exception {
		// RSAPublicKey _publicKey = loadPublicKey(pubCer, isFile);
		RSAPublicKey _publicKey = (RSAPublicKey) readPubKeyFromFile(pubCer);
		Cipher cipher = Cipher
				.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");

		cipher.init(Cipher.ENCRYPT_MODE, _publicKey);

		int keySize = _publicKey.getModulus().bitLength() / 8;
		int maxLength = keySize - 42;

		byte[] bytes = dataToEncrypt.getBytes();

		int dataLength = bytes.length;
		int iterations = dataLength / maxLength;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= iterations; i++) {
			byte[] tempBytes = new byte[(dataLength - maxLength * i > maxLength) ? maxLength
					: dataLength - maxLength * i];
			System.arraycopy(bytes, maxLength * i, tempBytes, 0,
					tempBytes.length);
			byte[] encryptedBytes = cipher.doFinal(tempBytes);
			// Be aware the RSACryptoServiceProvider reverses the order of
			// encrypted bytes after encryption and before decryption.
			// If you do not require compatibility with Microsoft Cryptographic
			// API (CAPI) and/or other vendors.
			// Comment out the next line and the corresponding one in the
			// DecryptString function.
			encryptedBytes = reverse(encryptedBytes);
			sb.append(encodeBase64(encryptedBytes));
		}

		String sEncrypted = sb.toString();
		sEncrypted = sEncrypted.replace("\r", "");
		sEncrypted = sEncrypted.replace("\n", "");
		return sEncrypted;
	}

	private static String fullyReadFile(File file) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		byte[] bytesOfFile = new byte[(int) file.length()];
		dis.readFully(bytesOfFile);
		dis.close();
		String sRead = new String(bytesOfFile);
		return sRead.trim();
	}

	public static void generateRSAKey(int keySize, String publicKeyFilePath,
			String privateKeyFilePath) throws NoSuchAlgorithmException,
			IOException {
		// http://java.sun.com/j2se/1.4.2/docs/guide/security/CryptoSpec.html
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		// Initialize
		kpg.initialize(keySize, new SecureRandom());
		KeyPair keys = kpg.generateKeyPair();

		RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();
		saveKey(publicKeyFilePath, publicKey, "PUBLIC KEY");
		saveKey(privateKeyFilePath, privateKey, "PRIVATE KEY");
	}

	public static String getMD5(String data) throws NoSuchAlgorithmException {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(data.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load PrilicKey Using Private Key File or PEM String Private Key
	 *
	 * @param key
	 *            The file path to the Private Key or PEM String Private Key
	 * @param isFile
	 *            Private Key is a file or PEM String?
	 * @return RSAPublicKey
	 * @throws CertificateException
	 */
	public static RSAPrivateKey loadPrivateKey(String key, Boolean isFile)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		String sReadFile = "";
		if (isFile) {
			File file = new File(key);
			sReadFile = fullyReadFile(file);
		} else {
			sReadFile = key;
		}
		if (sReadFile.startsWith("-----BEGIN PRIVATE KEY-----")
				&& sReadFile.endsWith("-----END PRIVATE KEY-----")) {
			sReadFile = sReadFile.replace("-----BEGIN PRIVATE KEY-----", "");
			sReadFile = sReadFile.replace("-----END PRIVATE KEY-----", "");
			sReadFile = sReadFile.replace("\n", "");
			sReadFile = sReadFile.replace("\r", "");
		} else {
			return null;
		}
		byte[] b = decodeBase64(sReadFile);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);

		KeyFactory factory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) factory.generatePrivate(spec);
	}

	/**
	 * Load PublicKey Using Public Certificate File or PEM String Public
	 * Certificate
	 *
	 * @param pubCer
	 *            The file path to the Public Certificate or PEM String Public
	 *            Certificate
	 * @param isFile
	 *            Private Key is a file or PEM String?
	 * @return RSAPublicKey
	 * @throws CertificateException
	 */
	public static RSAPublicKey loadPublicKey(String pubCer, Boolean isFile)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException, CertificateException {
		InputStream fis;
		if (isFile) {
			fis = new FileInputStream(pubCer);
		} else {
			pubCer = pubCer.replace("-----BEGIN CERTIFICATE-----", "");
			pubCer = pubCer.replace("-----END CERTIFICATE-----", "");
			pubCer = pubCer.replace("\n", "");
			pubCer = pubCer.replace("\r", "");
			fis = new ByteArrayInputStream(decodeBase64(pubCer));
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate cert = null;
		while (bis.available() > 0) {
			cert = cf.generateCertificate(bis);
		}
		return (RSAPublicKey) cert.getPublicKey();
	}

	private static byte[] reverse(byte[] b) {
		int left = 0; // index of leftmost element
		int right = b.length - 1; // index of rightmost element

		while (left < right) {
			// exchange the left and right elements
			byte temp = b[left];
			b[left] = b[right];
			b[right] = temp;

			// move the bounds toward the center
			left++;
			right--;
		}
		return b;
	}// end method reverse

	static void saveKey(String filename, Key key, String headfoot)
			throws IOException {
		if (null == key) {
			throw new IllegalArgumentException("key is null.");
		}

		DataOutputStream dos = new DataOutputStream(new FileOutputStream(
				filename));

		// PKCS #8 for Private, X.509 for Public
		// File will contain OID 1.2.840.11359.1.1.1 (RSA)
		// http://java.sun.com/j2se/1.4.2/docs/api/java/security/Key.html
		// byte[] bKeyEncoded = encodeBase64(key.getEncoded()).getBytes();
		byte[] bKeyEncoded = key.getEncoded();
		byte[] b = DERtoPEM(bKeyEncoded, headfoot);
		dos.write(b);

		dos.close();
	}

	/**
	 * sign Data Using Private Key File or PEM String Private Key
	 *
	 * @param dataToSign
	 *            The data to sign
	 * @param privateKey
	 *            The file path to the Private Key or PEM String Key
	 * @param isFile
	 *            Private Key is a file or PEM String
	 * @return Signature String (Base64 encoded)
	 */
	public static String sign(String dataToSign, String privateKey,
			Boolean isFile) throws Exception {
		RSAPrivateKey _privateKey = loadPrivateKey(privateKey, isFile);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(_privateKey);
		signature.update(dataToSign.getBytes());

		byte[] bSigned = signature.sign();

		String sResult = encodeBase64(bSigned);

		return sResult;

	}

	/**
	 * verify data with signature
	 *
	 * @param dataToVerify
	 *            The data to verify
	 * @param signedData
	 *            Your Signature
	 * @param pubCer
	 *            The file path to the Public Certificate or PEM String
	 *            Certificate
	 * @param isFile
	 *            Private Key is a file or PEM String
	 * @return True or False - True: Correct Data False: Data has been changed
	 */
	public static boolean verify(String dataToVerify, String signedData,
			String pubCer, Boolean isFile) throws Exception {
		RSAPublicKey _publicKey = loadPublicKey(pubCer, isFile);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(_publicKey);
		signature.update(dataToVerify.getBytes(), 0,
				dataToVerify.getBytes().length);
		byte[] bSign = decodeBase64(signedData);
		boolean pass = signature.verify(bSign);

		return pass;

	}

	/*
	 * public static void main(String[] args) throws Exception { String
	 * plainText = "a";
	 * 
	 * try { String ma = encrypt(plainText,
	 * "C:\\Users\\xample\\SourceCode\\SailonPos\\mobile.server\\src\\conf\\public.key"
	 * , true); System.out.println(ma);
	 * 
	 * // String ma =
	 * "hXH1qXTaFIjFqfkPov2Z23GSWMvqZ2tvZvJtNcr/R05POntZimmXcEJVRl4xRasQCwdZlSWE+EZuuZ058PZv8da5HGq00C1X4mIk55vw8lLRKYTdwQghj71bNfjhluLG1Tf79DfX+5vfq2DgWmOBHKp+Gj6FdtvEM0K93vcNS3g="
	 * ;
	 * 
	 * String giaima = rsaDecrypt(ma,
	 * "C:\\Users\\xample\\SourceCode\\SailonPos\\mobile.server\\src\\conf\\private.key"
	 * , true);
	 * 
	 * System.out.println(giaima);
	 * 
	 * // generateRSAKey(1024,
	 * "C:\\Users\\xample\\SourceCode\\SailonPos\\mobile.server\\src\\conf\\public.key"
	 * ,
	 * "C:\\Users\\xample\\SourceCode\\SailonPos\\mobile.server\\src\\conf\\private.key"
	 * ); } catch (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */

	/**
	 * @author toantm May 24, 2013 3:03:06 PM
	 * @description Descrypt voi private.key
	 */
	public static String rsaDecrypt(String data, String privateKey,
			boolean isFile) throws Exception {
		RSAPrivateKey _privateKey = loadPrivateKey(privateKey, isFile);
		return decryptRSA(data, _privateKey);
		// cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		// cipher.init(Cipher.DECRYPT_MODE, _privateKey);
		// byte[] bts =(data.getBytes("UTF8"));
		// return blockCipher(bts,Cipher.DECRYPT_MODE);
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
			byte[] bTemp = new sun.misc.BASE64Decoder().decodeBuffer(sTemp);
			byte[] encryptedBytes = cipher.doFinal(bTemp);
			bb.put(encryptedBytes);
		}
		byte[] bDecrypted = bb.array();
		String dataToReturn = new String(bDecrypted, "UTF-8").trim();
		dataToReturn = dataToReturn.replace("\0", "");
		return dataToReturn;
	}

	/**
	 * 
	 * Doc cac blog ma hoa/ giai ma
	 * 
	 * @author: DoanDM
	 * @param bytes
	 * @param mode
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @return: String
	 * @throws:
	 */
	public static Cipher cipher;
	/*
	 * private static String blockCipher(byte[] bytes, int mode) throws
	 * IllegalBlockSizeException, BadPaddingException{ // string initialize 2
	 * buffers. // scrambled will hold intermediate results byte[] scrambled =
	 * new byte[0];
	 * 
	 * // toReturn will hold the total result // byte[] toReturn = new byte[0];
	 * String strReturn=""; // if we encrypt we use 100 byte long blocks.
	 * Decryption requires 172 byte long blocks (because of RSA encrypt 64) int
	 * length = (mode == Cipher.ENCRYPT_MODE)? cipher.getBlockSize() : 172;
	 * 
	 * // another buffer. this one will hold the bytes that have to be modified
	 * in this step byte[] buffer = new byte[length];
	 * 
	 * for (int i=0; i< bytes.length; i++){
	 * 
	 * // if we filled our buffer array we have our block ready for de- or
	 * encryption if ((i > 0) && (i % length == 0)){ if(mode !=
	 * Cipher.ENCRYPT_MODE){ buffer = Base64.decodeBase64(buffer); } //execute
	 * the operation scrambled = cipher.doFinal(buffer); // add the result to
	 * our total result. // toReturn = append(toReturn, mode ==
	 * Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
	 * strReturn+= new String(mode ==
	 * Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled); // here we
	 * calculate the length of the next buffer required int newlength = length;
	 * 
	 * // if newlength would be longer than remaining bytes in the bytes array
	 * we shorten it. if (i + length > bytes.length) { newlength = bytes.length
	 * - i; } // clean the buffer array buffer = new byte[newlength]; } // copy
	 * byte into our buffer. buffer[i%length] = bytes[i]; }
	 * 
	 * // this step is needed if we had a trailing buffer. should only happen
	 * when encrypting. // example: we encrypt 110 bytes. 100 bytes per run
	 * means we "forgot" the last 10 bytes. they are in the buffer array if(mode
	 * != Cipher.ENCRYPT_MODE){ buffer = Base64.decodeBase64(buffer); }
	 * scrambled = cipher.doFinal(buffer);
	 * 
	 * // final step before we can return the modified data. // toReturn =
	 * append(toReturn,mode ==
	 * Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled);
	 * strReturn+= new String(mode ==
	 * Cipher.ENCRYPT_MODE?Base64.encodeBase64(scrambled):scrambled); strReturn
	 * = strReturn.replace("\r", ""); strReturn = strReturn.replace("\n", "");
	 * return strReturn; }
	 */
}
