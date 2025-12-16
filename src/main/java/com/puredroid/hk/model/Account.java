package com.puredroid.hk.model;

import java.time.LocalDateTime;

public class Account {

    private int id;
    private int orgId;
    private String username;
    private String email;
    private String phone;
    private byte[] passwordEncrypted;
    private String notes;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;    
    private LocalDateTime lastPasswordChanged;

    public Account(int id, int orgId, String username, String email, String phone, byte[] passwordEncrypted, String notes, String url, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastPasswordChanged) {
        this.id = id;
        this.orgId = orgId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.passwordEncrypted = passwordEncrypted;
        this.notes = notes;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastPasswordChanged = lastPasswordChanged;
    }

    public Account(int orgId, String username, String email, String phone, byte[] passwordEncrypted, String notes, String url) {
        this.id = 0;
        this.orgId = orgId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.passwordEncrypted = passwordEncrypted;
        this.notes = notes;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(byte[] passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastPasswordChanged() {
        return lastPasswordChanged;
    }

    public void setLastPasswordChanged(LocalDateTime lastPasswordChanged) {
        this.lastPasswordChanged = lastPasswordChanged;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", orgId=" + orgId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", notes='" + notes + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastPasswordChanged=" + lastPasswordChanged +
                '}';
    }   
}
