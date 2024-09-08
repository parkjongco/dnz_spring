package com.kedu.dto;

import java.sql.Timestamp;

public class EmailVerificationsDTO {
    private int verificationId;
    private String userEmail;
    private String verificationCode;
    private Timestamp expirationTime;
    private Character isVerified;
    private Timestamp createdAt;

    public EmailVerificationsDTO() {}

    public EmailVerificationsDTO(int verificationId, String userEmail, String verificationCode,
                                 Timestamp expirationTime, Character isVerified, Timestamp createdAt) {
        this.verificationId = verificationId;
        this.userEmail = userEmail;
        this.verificationCode = verificationCode;
        this.expirationTime = expirationTime;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
    }

    public int getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(int verificationId) {
        this.verificationId = verificationId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Character getIsVerified() { // boolean 타입 getter는 보통 get으로 시작
        return isVerified;
    }

    public void setIsVerified(Character isVerified) {
        this.isVerified = isVerified;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
