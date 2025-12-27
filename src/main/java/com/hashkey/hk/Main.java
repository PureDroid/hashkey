package com.hashkey.hk;

import com.hashkey.hk.database.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Get database manager instance
            DatabaseManager dbManager = DatabaseManager.getInstance();
            
            // Initialize database (create tables)
            dbManager.initializeDatabase();
            
            System.out.println("✅ Database setup complete!");
            
            // Close connection
            dbManager.close();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}