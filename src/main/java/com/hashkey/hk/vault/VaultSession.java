package com.hashkey.hk.vault;

import java.util.Arrays;

public final class VaultSession {

    private byte[] vaultKey;

    public boolean isUnlocked() {
        return vaultKey != null;
    }

    public void unlock(byte[] derivedKey) {
        if (derivedKey == null || derivedKey.length != 32) {
            throw new IllegalArgumentException("Valid 256-bit vault key required");
        }

        if (this.vaultKey != null) {
            throw new IllegalStateException("Vault already unlocked");
        }

        this.vaultKey = Arrays.copyOf(derivedKey, derivedKey.length);
    }

    public byte[] getVaultKey() {
        if (vaultKey == null) {
            throw new IllegalStateException("Vault is locked");
        }
        return Arrays.copyOf(vaultKey, vaultKey.length);
    }

    public void lock() {
        if (vaultKey != null) {
            Arrays.fill(vaultKey, (byte) 0);
            vaultKey = null;
        }
    }
}
