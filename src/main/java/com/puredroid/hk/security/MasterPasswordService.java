package com.puredroid.hk.security;

import java.io.IOException;
import java.security.SecureRandom;

import com.puredroid.hk.database.dao.MasterPasswordDAO;
import com.puredroid.hk.database.dao.StoredMasterPassword;
import com.puredroid.hk.database.dao.impl.MasterPasswordDAOImpl;

public class MasterPasswordService {

    private final MasterPasswordDAO masterPasswordDAO = new MasterPasswordDAOImpl();

    public boolean isMasterPasswordSet(){
        return masterPasswordDAO.exists();
    }

    public void setupMasterPassword(char[] password){
        if (isMasterPasswordSet()) {
            throw new IllegalStateException("Master Password Already Exists");
        }
        if (password == null||password.length==0) {
            throw new IllegalStateException("Master Password Cannot Be Empty");
        }

        byte[] salt = new byte[MasterPasswordHasher.SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        String hash = MasterPasswordHasher.hash(password, salt);

        masterPasswordDAO.save(hash, salt);

        try{
            new SecurityMetadata().reset();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean verifyMasterPassword(char[] password){
        if (password == null || password.length==0) {
            return false;
        }

        try{
            SecurityMetadata securityMetadata = new SecurityMetadata();

            if (LockoutPolicy.isLocked(securityMetadata.getFailedAttempts(), securityMetadata.getLastFailedEpoch())) {
                throw new IllegalStateException("Account is Temporarily Blocked");
            }

            StoredMasterPassword storedMasterPassword = masterPasswordDAO.load(); 

            String storedHash = storedMasterPassword.getPasswordHash();

            byte[] storedSalt = storedMasterPassword.getSalt();

            boolean verified = MasterPasswordHasher.verify(password, storedSalt, storedHash);

            if (verified) {
                securityMetadata.reset();
                return true;
            }
            else{
                securityMetadata.recordFailure();
                return false;
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
