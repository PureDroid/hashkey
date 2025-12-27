package com.puredroid.hk.security;

import java.util.Base64;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.util.Arrays;

public class MasterPasswordHasher {
    private static final int MEMORY_KB = 65536;
    private static final int ITERATIONS = 3;
    private static final int PARALLELISM = 1;
    public static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;

    private MasterPasswordHasher(){

    }

    public static String hash(char[] password, byte[] salt){
        Argon2Parameters parameters = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withMemoryAsKB(MEMORY_KB)
                .withIterations(ITERATIONS)
                .withParallelism(PARALLELISM)
                .withSalt(salt)
                .build();

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(parameters);

        byte[] hash = new byte[HASH_LENGTH];
        generator.generateBytes(password, hash);

        Arrays.fill(password, '\0');

        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verify(char[] password, byte[] salt, String expectedHash){
        String computedHash = hash(password, salt);
        byte[] expectedBytes = Base64.getDecoder().decode(expectedHash);
        byte[] computedBytes = Base64.getDecoder().decode(computedHash);

        return Arrays.areEqual(expectedBytes, computedBytes);
    }
}
