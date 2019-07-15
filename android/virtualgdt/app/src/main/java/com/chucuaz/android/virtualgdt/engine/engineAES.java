package com.chucuaz.android.virtualgdt.engine;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import android.util.Base64;


/**
 * Created by efren.robles on 10/13/2015.
 */
public class engineAES extends engineDebug{
    private static final String encryptionKey = "MZygpewJsCpRrfOr";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String CIPHER_ALGORITHM = "AES";
    private static final String MESSAGEDIGEST_ALGORITHM = "MD5";
    private static final String TAG = "AES";

    private static Cipher aesCipher;
    private static SecretKey secretKey;
    private static IvParameterSpec ivParameterSpec;

    // Replace me with a 16-byte key, share between Java and C#
    private static byte[] rawSecretKey = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


    public engineAES(String passphrase) {
        byte[] passwordKey = encodeDigest(passphrase);

        try {
            aesCipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException e) {
            ERR(TAG, "No such algorithm " + CIPHER_ALGORITHM, e);
        } catch (NoSuchPaddingException e) {
            ERR(TAG, "No such padding PKCS5", e);
        }

        secretKey = new SecretKeySpec(passwordKey, CIPHER_ALGORITHM);
        ivParameterSpec = new IvParameterSpec(rawSecretKey);
    }

    public String encryptAsBase64(byte[] clearData) {
        byte[] encryptedData = encrypt(clearData);
        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    public String encryptAsBase64(String clearData) {
        byte[] encryptedData = {0};
        try {
            encryptedData = clearData.getBytes("UTF-8");
        } catch (Exception e) {
            ERR(TAG, "--- There is an error with encryptAsBAse64 --- ");
        }
        return encryptAsBase64(encryptedData);
    }


    public byte[] encrypt(byte[] clearData) {
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } catch (InvalidKeyException e) {
            ERR(TAG, "Invalid key", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            ERR(TAG, "Invalid algorithm " + CIPHER_ALGORITHM, e);
            return null;
        }

        byte[] encryptedData;
        try {
            encryptedData = aesCipher.doFinal(clearData);
        } catch (IllegalBlockSizeException e) {
            ERR(TAG, "Illegal block size", e);
            return null;
        } catch (BadPaddingException e) {
            ERR(TAG, "Bad padding", e);
            return null;
        }
        return encryptedData;
    }

    private byte[] encodeDigest(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(MESSAGEDIGEST_ALGORITHM);
            return digest.digest(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            ERR(TAG, "No such algorithm " + MESSAGEDIGEST_ALGORITHM, e);
        }
        return null;
    }

}
