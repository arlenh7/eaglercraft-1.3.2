package net.minecraft.src;

import java.io.InputStream;
import java.io.OutputStream;

public class CryptManager {

    public static Object func_75890_a() {
        return null;
    }

    public static Object createNewKeyPair() {
        return null;
    }

    public static byte[] func_75894_a(Object par0PublicKey, byte[] par1ArrayOfByte) {
        return par1ArrayOfByte;
    }

    public static Object func_75887_a(Object par0PrivateKey, byte[] par1ArrayOfByte) {
        return null;
    }

    public static byte[] func_75889_b(Object par0PrivateKey, byte[] par1ArrayOfByte) {
        return par1ArrayOfByte;
    }

    public static Object func_75896_a(byte[] par0ArrayOfByte) {
        return null;
    }

    public static byte[] func_75895_a(String par0Str, Object par1PublicKey, Object par2SecretKey) {
        return new byte[0];
    }

    public static InputStream decryptInputStream(Object par0SecretKey, InputStream par1InputStream) {
        return par1InputStream;
    }

    public static OutputStream encryptOuputStream(Object par0SecretKey, OutputStream par1OutputStream) {
        return par1OutputStream;
    }
}
