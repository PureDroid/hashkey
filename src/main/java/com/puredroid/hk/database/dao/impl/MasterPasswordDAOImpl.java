package com.puredroid.hk.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.puredroid.hk.database.DatabaseManager;
import com.puredroid.hk.database.dao.MasterPasswordDAO;
import com.puredroid.hk.database.dao.StoredMasterPassword;

public class MasterPasswordDAOImpl implements MasterPasswordDAO {

    private static final int MASTER_PASSWORD_ID = 1;
    private static final String SQL_EXISTS = "SELECT 1 FROM master_password WHERE id = ?";

    private static final String SQL_SAVE = "INSERT OR REPLACE INTO master_password (id, password_hash, salt) VALUES (?, ?, ?)";
    private static final String SQL_LOAD = "SELECT password_hash, salt FROM master_password WHERE id = ?";
    
    @Override
    public boolean exists(){
        try(Connection connection = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_EXISTS)){
                statement.setInt(1, MASTER_PASSWORD_ID);

                try(ResultSet resultSet = statement.executeQuery()){
                    return resultSet.next();
                }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(String passwordHash, byte[] salt){
        try(Connection connection = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE)){

                statement.setInt(1, MASTER_PASSWORD_ID);
                statement.setString(2, passwordHash);
                statement.setBytes(3, salt);

                statement.executeUpdate();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public StoredMasterPassword load(){
        try(Connection connection = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LOAD)){
                statement.setInt(1, MASTER_PASSWORD_ID);

                try(ResultSet resultSet = statement.executeQuery()){
                    if (!resultSet.next()) {
                        throw new IllegalStateException("Master password not set");
                    }
                    return new StoredMasterPassword(
                        resultSet.getString("password_hash"),
                        resultSet.getBytes("salt")
                    );
                }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
