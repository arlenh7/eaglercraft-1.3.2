package net.minecraft.src;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptManager {

	public static SecretKey func_75890_a() {
		try {
			KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
			keygenerator.init(128);
			return keygenerator.generateKey();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static KeyPair createNewKeyPair() {
		try {
			KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
			keypairgenerator.initialize(1024);
			return keypairgenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static byte[] func_75894_a(PublicKey par0PublicKey, byte[] par1ArrayOfByte) {
		return func_75893_a(1, par0PublicKey, par1ArrayOfByte);
	}

	public static SecretKey func_75887_a(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte) {
		byte[] decrypted = func_75893_a(2, par0PrivateKey, par1ArrayOfByte);
		return new SecretKeySpec(decrypted, "AES");
	}

	public static byte[] func_75889_b(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte) {
		return func_75893_a(2, par0PrivateKey, par1ArrayOfByte);
	}

	public static PublicKey func_75896_a(byte[] par0ArrayOfByte) {
		try {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(par0ArrayOfByte);
			KeyFactory keyfactory = KeyFactory.getInstance("RSA");
			return keyfactory.generatePublic(spec);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static byte[] func_75895_a(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey) {
		try {
			return func_75892_a(par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static byte[] func_75892_a(byte[] ... par0ArrayOfByte) {
		try {
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
			for (int i = 0; i < par0ArrayOfByte.length; ++i) {
				messagedigest.update(par0ArrayOfByte[i]);
			}
			return messagedigest.digest();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static byte[] func_75893_a(int par0, Key par1Key, byte[] par2ArrayOfByte) {
		try {
			Cipher cipher = Cipher.getInstance(par1Key.getAlgorithm());
			cipher.init(par0, par1Key);
			return cipher.doFinal(par2ArrayOfByte);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static Cipher func_75885_a(int par0, Key par1Key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
			cipher.init(par0, par1Key, new IvParameterSpec(par1Key.getEncoded()));
			return cipher;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static InputStream decryptInputStream(SecretKey par0SecretKey, InputStream par1InputStream) {
		return new CipherInputStream(par1InputStream, func_75885_a(2, par0SecretKey));
	}

	public static OutputStream encryptOuputStream(SecretKey par0SecretKey, OutputStream par1OutputStream) {
		return new CipherOutputStream(par1OutputStream, func_75885_a(1, par0SecretKey));
	}
}
