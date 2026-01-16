package com.hashkey.hk.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

public final class VaultEncryptor {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;

    private VaultEncryptor() {
        // utility class
    }

    public static byte[] encrypt(byte[] key, byte[] plaintext) {
        if (key == null || key.length != 32) {
            throw new IllegalArgumentException("256-bit key required");
        }
        if (plaintext == null) {
            throw new IllegalArgumentException("Plaintext required");
        }

        try {
            byte[] iv = new byte[IV_LENGTH_BYTES];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            GCMParameterSpec gcmSpec =
                new GCMParameterSpec(TAG_LENGTH_BITS, iv);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            byte[] ciphertext = cipher.doFinal(plaintext);

            byte[] output = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, output, 0, iv.length);
            System.arraycopy(ciphertext, 0, output, iv.length, ciphertext.length);

            return output;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public static byte[] decrypt(byte[] key, byte[] input) {
        if (key == null || key.length != 32) {
            throw new IllegalArgumentException("256-bit key required");
        }
        if (input == null || input.length <= IV_LENGTH_BYTES) {
            throw new IllegalArgumentException("Invalid ciphertext");
        }

        try {
            byte[] iv = Arrays.copyOfRange(input, 0, IV_LENGTH_BYTES);
            byte[] ciphertext =
                Arrays.copyOfRange(input, IV_LENGTH_BYTES, input.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            GCMParameterSpec gcmSpec =
                new GCMParameterSpec(TAG_LENGTH_BITS, iv);

            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

            return cipher.doFinal(ciphertext);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
