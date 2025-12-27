package com.hashkey.hk.database.dao;

public class StoredMasterPassword {
    private final String passwordHash;
    private final byte[] salt;
    
    public StoredMasterPassword(String passwordHash, byte[] salt) {
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }
}
