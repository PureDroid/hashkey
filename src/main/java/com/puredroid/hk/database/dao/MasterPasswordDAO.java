package com.puredroid.hk.database.dao;

public interface MasterPasswordDAO {
    boolean exists();
    void save(String passwordHash, byte[] salt);
    StoredMasterPassword load();
}
