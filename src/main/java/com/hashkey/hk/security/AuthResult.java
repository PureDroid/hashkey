package com.hashkey.hk.security;

public class AuthResult {

    private final boolean success;
    private final boolean locked;
    private final long remainingLockMs;

    private AuthResult(boolean success, boolean locked, long remainingLockMs) {
        this.success = success;
        this.locked = locked;
        this.remainingLockMs = remainingLockMs;
    }

    public static AuthResult success() {
        return new AuthResult(true, false, 0);
    }

    public static AuthResult locked(long remainingMs) {
        return new AuthResult(false, true, remainingMs);
    }

    public static AuthResult failure() {
        return new AuthResult(false, false, 0);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isLocked() {
        return locked;
    }

    public long getRemainingLockMs() {
        return remainingLockMs;
    }
}
