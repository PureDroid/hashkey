# 🔐 HashKey — Developer Usage Guide

> **Secure Offline Password Vault Core (Java)**  
> This document contains **everything a developer needs** to correctly use HashKey as a module.

If you are looking for:
- a UI → not here  
- cloud sync → not here  
- password recovery → not here  

If you want **a secure vault core you can trust**, read on.

---

## 📦 What is HashKey?

HashKey is a **Java library** that provides:
- master-password authentication
- brute-force protection
- in-memory vault key management
- encrypted secret storage
- audit logging
- zero network dependency

HashKey is **not an app**.  
It is meant to be embedded into another application.

---

## 🧠 Core Guarantees (Read This First)

HashKey guarantees the following **if you use it correctly**:

- Master password is **never stored**
- Vault key exists **only in memory**
- Secrets are encrypted **before hitting disk**
- Lockout is enforced **before hashing**
- No cloud, no sync, no telemetry
- Lost master password = lost data (by design)

If your app violates these rules, **security is void**.

---

## 📥 Clone the Repository

```bash
git clone https://github.com/BiprajitG/hashkey.git
cd hashkey
```

## ⚙️ Build Requirements

- Java 17+
- Maven 3.6+

Build:

```bash
mvn clean compile
```

## 🗂️ Runtime Files (Auto-Generated)

HashKey creates these automatically:

| File | Purpose |
|------|---------|
| `passwords.db` | Encrypted SQLite vault |
| `security.meta` | Failed-attempt & lockout metadata |

Do not edit these manually.

---

## 🧱 Public API Surface (IMPORTANT)

You are allowed to use ONLY these classes:

### 🔐 Authentication & Security

- `MasterPasswordSetupService`
- `MasterPasswordVerificationService`
- `VaultUnlockService`
- `AuthResult`
- `LockoutPolicy`

### 🔑 Vault & Secrets

- `VaultSession`
- `AccountService`

### 🔐 Crypto (Advanced Use Only)

- `VaultKeyDeriver`
- `VaultEncryptor`

Everything else is internal and may change.

---

## 🔐 Correct Usage Flow (MANDATORY)

### 1️⃣ Initialize Database

This sets up the schema.

```java
DatabaseManager.getInstance().initializeDatabase();
```

Do this once on startup.

### 2️⃣ Set Master Password (First Run Only)

```java
MasterPasswordDAO dao = new MasterPasswordDAOImpl();
MasterPasswordSetupService setup =
    new MasterPasswordSetupService(dao);

setup.setup(masterPasswordCharArray);
```

Rules:
- Call only once
- Password must be `char[]`
- HashKey wipes it after use

### 3️⃣ Verify Master Password (Login)

```java
MasterPasswordVerificationService verifier =
    new MasterPasswordVerificationService(dao);

AuthResult result = verifier.verify(inputPassword);
```

Possible outcomes:
- `result.isSuccess()` → password correct
- `result.isLocked()` → lockout active
- otherwise → wrong password

❗ Verification does not unlock the vault.

### 4️⃣ Unlock the Vault (REQUIRED)

```java
VaultSession vaultSession = new VaultSession();

VaultUnlockService unlockService =
    new VaultUnlockService(verifier, dao, vaultSession);

AuthResult unlockResult =
    unlockService.unlock(inputPassword);
```

If successful:
- vault key is derived
- vault is unlocked in memory

If not:
- no key is created
- nothing is leaked

### 5️⃣ Create an Account (Encrypted)

```java
AccountService accountService =
    new AccountService(
        vaultSession,
        new AccountDAOImpl(),
        new AuditLogDAOImpl()
    );

accountService.createAccount(
    orgId,
    "github",
    passwordCharArray,
    "Personal access token",
    "https://github.com"
);
```

Rules:
- Vault must be unlocked
- Plaintext password never hits DB
- Encryption is automatic

### 6️⃣ Read a Password (Decrypted)

```java
char[] secret =
    accountService.getAccountPassword(accountId);

// use it briefly

Arrays.fill(secret, '\0');
```

You are responsible for wiping the returned `char[]`.

### 7️⃣ Lock the Vault on Exit (CRITICAL)

```java
vaultSession.lock();
```

This:
- wipes the vault key
- makes further access impossible

You must call this on:
- app exit
- logout
- crash handling (best effort)

---

## 🚫 Things You MUST NOT Do

Do NOT:
- store plaintext passwords
- cache decrypted secrets
- store vault keys
- call DAO implementations directly
- bypass lockout logic
- auto-unlock on startup
- add password recovery

If you need these features, HashKey is not the right layer.

---

## 🧵 Threading Rules

- HashKey is not thread-safe
- One vault session per logical user
- Synchronize externally if needed

---

## 📱 Using HashKey with Flutter / Mobile

Recommended architecture:

```
Flutter UI
   ↓
Platform Channel / JNI
   ↓
Java Layer (HashKey)
```

Rules:
- Never expose vault keys to Dart
- Treat HashKey as a black box
- Only expose high-level operations

---

## ⚠️ Critical Warning

### 🚨 NO PASSWORD RECOVERY 🚨

If the master password is lost:
- data is unrecoverable
- this is permanent
- this is intentional

HashKey will not help you recover it.

---

## ✅ Summary

HashKey gives you:
- secure authentication
- encrypted local storage
- strong architectural boundaries

You provide:
- UI
- lifecycle management
- responsibility

Together, you get a secure system.

---

**Repository:** https://github.com/BiprajitG/hashkey

**Author:** BiprajitG  
**License:** MIT

🔐 **HashKey — a quiet core for serious vaults.**