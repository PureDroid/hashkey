package com.hashkey.hk.security;

import java.util.Base64;

public final class CredentialEncoding {

    private CredentialEncoding() {}

    public static String toBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] fromBase64(String data) {
        return Base64.getDecoder().decode(data);
    }
}
