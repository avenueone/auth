package com.avenueone.security.util;

import org.apache.commons.codec.binary.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Encryption {

    private String encryptionSalt;

    private String encryptionKey;

    private int KEY_SIZE = 256;

    private String ENC_SEC_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";

    private int ENCRYPTION_ITERATIONS = 65536;

    private String ENCRYPTION_SECRET_KEY_ALGORITHM = "AES";

    private String ENC_ALGORITHM = "AES/CBC/PKCS5Padding";

    private String ENCRYPTION_DATA_FORMAT = "UTF-8";

    private byte initialVector[] = new byte[16];

    private SecretKey keySpec;

    public String getEncryptionSalt() {
        return encryptionSalt;
    }

    public void setEncryptionSalt(String encryptionSalt) {
        this.encryptionSalt = encryptionSalt;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    private Cipher decryptCipher;

    private Cipher encryptCipher;

    public Encryption(String encryptionKey, String encryptionSalt) {
        this.encryptionKey = encryptionKey;
        this.encryptionSalt = encryptionSalt;
    }

    @PostConstruct
    public void initialize() {
        this.keySpec = getKeySpec();
        this.decryptCipher = getCipher(Cipher.DECRYPT_MODE);
        this.encryptCipher = getCipher(Cipher.ENCRYPT_MODE);
    }

    public String decrypt(String encryptedText) {
        String decrypted = encryptedText;
        try {
            while (decrypted.contains("%")) {
                decrypted = URLDecoder.decode(decrypted, "UTF-8");
            }
            decrypted = new String(decryptCipher.doFinal(Base64.decodeBase64(decrypted)));
        } catch (Exception e) {

        }
        return decrypted;
    }

    public String encrypt(String plainText) {
        String encrypted = plainText;
        try {
            encrypted = Base64.encodeBase64String(this.encryptCipher.doFinal(plainText.getBytes(ENCRYPTION_DATA_FORMAT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    private SecretKey getKeySpec() {
        SecretKey secretKey = null;
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENC_SEC_KEY_ALGORITHM);
            KeySpec encSpec = new PBEKeySpec(getEncryptionKey().toCharArray(), getEncryptionSalt().getBytes(), ENCRYPTION_ITERATIONS, KEY_SIZE);
            SecretKey priSecretKey = keyFactory.generateSecret(encSpec);
            secretKey = new SecretKeySpec(priSecretKey.getEncoded(), ENCRYPTION_SECRET_KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException nse) {
            nse.printStackTrace();
        } catch (InvalidKeySpecException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;

    }

    private Cipher getCipher(int mode) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ENC_ALGORITHM);
            cipher.init(mode, keySpec, new IvParameterSpec(initialVector));
        } catch (NoSuchAlgorithmException nse) {
            nse.printStackTrace();
        } catch (NoSuchPaddingException nspe) {
            nspe.printStackTrace();
        } catch (InvalidKeyException ike) {
            ike.printStackTrace();
        } catch (InvalidAlgorithmParameterException iape) {

        } catch (Exception e) {

        }
        return cipher;
    }
}
