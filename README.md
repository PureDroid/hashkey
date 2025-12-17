# 🔐 HashKey - Secure Offline Password Manager

A robust, fully offline password manager built in Java with SQLite, designed for maximum security and privacy. All your passwords stay on your machine, encrypted with military-grade AES-256 encryption.

## ✨ Features

### 🔒 Security First
- **Master Password Protection** - Single password to access your entire vault
- **AES-256 Encryption** - Military-grade encryption for all stored passwords
- **Offline Only** - No internet connection required, no cloud sync, complete privacy
- **Failed Login Protection** - Account locks after multiple failed attempts
- **Clipboard Auto-Clear** - Automatically clears copied passwords after timeout
- **Comprehensive Audit Log** - Track every change made to your accounts

### 📋 Organization & Management
- **Organization-based Structure** - Group accounts by company, project, or category
- **Complete Account Information** - Store username, email, phone, URL, and notes
- **Full CRUD Operations** - Create, Read, Update, Delete accounts and organizations
- **Advanced Search & Filter** - Quickly find accounts across all organizations
- **Change History** - Track old passwords and account modifications

### 🛠️ Developer Features
- **Pure Java Implementation** - No external frameworks, clean code
- **SQLite Database** - Lightweight, embedded, zero-configuration
- **Maven Build System** - Easy dependency management
- **Modular Architecture** - Clean separation of concerns (Model, Database, Service, Security)
- **Cross-Platform Ready** - Works on Windows, Mac, Linux (Android support planned)

## 🏗️ Architecture

```
com.puredroid.hk/
├── model/          # Data models (Organization, Account, AuditLog, MasterPassword)
├── database/       # Database connection and DAO layer
│   ├── DatabaseManager.java    # Singleton connection manager
│   └── *DAO.java               # Data Access Objects (In Progress)
├── security/       # Encryption, master password, password generator (Planned)
├── service/        # Business logic layer (Planned)
├── util/           # Helper utilities (Planned)
└── Main.java       # Application entry point
```

## 📊 Database Schema

### Tables
- **organizations** - Group your accounts by organization
  - Fields: id, name, description, created_at, updated_at
- **accounts** - Store encrypted account credentials
  - Fields: id, org_id, username, email, phone, password_encrypted, notes, url, created_at, updated_at, last_password_change
- **audit_log** - Track all changes for security
  - Fields: id, account_id, org_id, action_type, old_values, new_values, timestamp
- **master_password** - Secure access control
  - Fields: id, password_hash, salt, failed_attempts, locked_until, created_at
- **settings** - Application configuration (key-value pairs)

### Indexes
- `idx_accounts_org_id` - Fast lookup of accounts by organization
- `idx_audit_account_id` - Fast lookup of audit logs by account

## 🚀 Getting Started

### Prerequisites
- Java JDK 17 or higher
- Maven 3.6+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/hashkey.git
cd hashkey
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn exec:java -Dexec.mainClass="com.puredroid.hk.Main"
```

Or run directly from your IDE (VSCode, IntelliJ, Eclipse)

### First Time Setup
On first launch, the application will:
1. Create `passwords.db` in your project directory
2. Initialize all database tables automatically
3. Prompt you to create a master password (coming soon!)

## 🔧 Configuration

**Database location:** `passwords.db` (in project root directory)

Settings will be configurable in the application:
- Clipboard timeout duration
- Auto-lock timer
- Password generator defaults

## 📦 Dependencies

```xml
<dependencies>
    <!-- SQLite JDBC Driver -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.44.1.0</version>
    </dependency>
    
    <!-- Bouncy Castle for Encryption -->
    <dependency>
        <groupId>org.bouncycastle</groupId>
        <artifactId>bcprov-jdk15on</artifactId>
        <version>1.70</version>
    </dependency>
    
    <!-- SLF4J for Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.9</version>
    </dependency>
</dependencies>
```

## 🔐 Security Features Explained

### Encryption (Planned)
- All passwords encrypted with AES-256-GCM
- Unique salt per password
- Master password hashed with Argon2id

### Master Password (Planned)
- Never stored in plain text
- Salted and hashed using Argon2id
- Failed attempt tracking with automatic lockout

### Audit Trail
- Every create, update, delete operation logged
- Old values preserved for rollback capability
- Timestamps for all changes

## 🎯 Development Roadmap

### ✅ Completed
- [x] Project structure and Maven setup
- [x] Complete database schema design
- [x] Model classes (Organization, Account, AuditLog, MasterPassword)
- [x] DatabaseManager with Singleton pattern
- [x] Database initialization and testing

### 🚧 In Progress
- [ ] DAO layer (OrganizationDAO, AccountDAO, AuditLogDAO, MasterPasswordDAO)

### 📋 Planned
- [ ] Encryption module (AES-256 for passwords)
- [ ] Master password authentication (Argon2id hashing)
- [ ] Password generator utility
- [ ] Password strength indicator
- [ ] Service layer (business logic)
- [ ] Search and filter functionality
- [ ] CLI interface
- [ ] GUI (JavaFX/Swing)
- [ ] Import/Export functionality (encrypted backup)
- [ ] Clipboard auto-clear feature
- [ ] Database encryption (SQLCipher)

## 📁 Project Structure

```
hashkey/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/puredroid/hk/
│   │   │       ├── model/
│   │   │       │   ├── Organization.java       ✅
│   │   │       │   ├── Account.java            ✅
│   │   │       │   ├── AuditLog.java           ✅
│   │   │       │   └── MasterPassword.java     ✅
│   │   │       ├── database/
│   │   │       │   └── DatabaseManager.java    ✅
│   │   │       ├── security/                   ⏳
│   │   │       ├── service/                    ⏳
│   │   │       ├── util/                       ⏳
│   │   │       └── Main.java                   ✅
│   │   └── resources/
│   │       └── schema.sql                      ✅
│   └── test/
├── pom.xml                                     ✅
├── README.md                                   ✅
└── passwords.db                                ✅ (auto-generated)
```

## 🤝 Contributing

This is a learning project, but contributions are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## ⚠️ Security Notice

**IMPORTANT:** 
- This is an educational project currently under development
- Core security features (encryption, master password) are not yet implemented
- Always backup your database file
- Once master password is implemented: if you forget it, your data is UNRECOVERABLE
- For production use, consider audited, battle-tested solutions like Bitwarden or KeePass

## 🧪 Testing

To test the current implementation:

```bash
# Compile and run
mvn clean compile
mvn exec:java

# You should see:
# Database initialized successfully.
# ✅ Database setup complete!
# Database connection closed.
```

Check that `passwords.db` file is created in your project root!

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Built as a learning project to understand encryption, database design, and secure software development
- Inspired by KeePass, Bitwarden, and other open-source password managers
- Special focus on clean architecture and best practices

## 📧 Contact

Project Link: [https://github.com/PureDroid/hashkey](https://github.com/PureDroid/hashkey)

---

**⚡ HashKey - Your passwords, your machine, your control.**

*Currently in active development - Star ⭐ to follow progress!*