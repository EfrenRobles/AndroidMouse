package com.chucuaz.android.androidinterface;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import android.util.Base64;
import android.util.Log;


/**
 * Created by efren.robles on 10/13/2015.
 */
public class myAESPool {
    private final String encryptionKey = "MZygpewJsCpRrfOr";

    //-------------
    public static final String TAG = "AES";

    private static Cipher aesCipher;
    private static SecretKey secretKey;
    private static IvParameterSpec ivParameterSpec;

    private static String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static String CIPHER_ALGORITHM = "AES";
    // Replace me with a 16-byte key, share between Java and C++
    private static byte[] rawSecretKey = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    private static String MESSAGEDIGEST_ALGORITHM = "MD5";

    public myAESPool(String passphrase) {
        byte[] passwordKey = encodeDigest(passphrase);

        try {
            aesCipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "No such algorithm " + CIPHER_ALGORITHM, e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "No such padding PKCS5", e);
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
            Log.e(TAG, "--- There is an error with encryptAsBAse64 --- ");
        }
        return encryptAsBase64(encryptedData);
    }


    public byte[] encrypt(byte[] clearData) {
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Invalid algorithm " + CIPHER_ALGORITHM, e);
            return null;
        }

        byte[] encryptedData;
        try {
            encryptedData = aesCipher.doFinal(clearData);
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, "Illegal block size", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(TAG, "Bad padding", e);
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
            Log.e(TAG, "No such algorithm " + MESSAGEDIGEST_ALGORITHM, e);
        }

        return null;
    }    //------------

}
