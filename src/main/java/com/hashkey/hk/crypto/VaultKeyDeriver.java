package com.hashkey.hk.crypto;

import java.security.GeneralSecurityException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class VaultKeyDeriver {
    private static final int ITERATIONS = 100000;
    private static final int KEY_LENGTH_BITS = 256;
    private static final String KEY = "PBKDF2WithHmacSHA256";

    private VaultKeyDeriver(){
        //utility class
    }

    public static byte[] deriveKey(char[] password, byte[] salt) {
        if (password==null || password.length==0) {
            throw new IllegalStateException("Password Required");
        }
        if (salt==null || salt.length==0) {
            throw new IllegalStateException("Salt Required");
        }

        try{
            PBEKeySpec spec = new PBEKeySpec(
                password,
                salt,
                ITERATIONS,
                KEY_LENGTH_BITS);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY);

            return secretKeyFactory.generateSecret(spec).getEncoded();
        }
        catch(GeneralSecurityException e){
            throw new RuntimeException("Key Derivation Failed", e);
        }
    }
}
