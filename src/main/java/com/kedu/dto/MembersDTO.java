package com.kedu.dto;

import java.sql.Timestamp;

public class MembersDTO {

    private int userSeq;
    private String userId;
    private String userPw;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private String userBirthDate;
    private Timestamp writeDate;
    private String imageUrl;  // 프로필 이미지 URL 필드 추가
    private boolean isStoreOwner;
    private String userGender;

    public int getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public Timestamp getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Timestamp writeDate) {
        this.writeDate = writeDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isStoreOwner() {
        return isStoreOwner;
    }

    public void setStoreOwner(boolean storeOwner) {
        isStoreOwner = storeOwner;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public MembersDTO(int userSeq, String userId, String userPw, String userName, String userPhoneNumber, String userEmail, String userBirthDate, Timestamp writeDate, String imageUrl, boolean isStoreOwner, String userGender) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userBirthDate = userBirthDate;
        this.writeDate = writeDate;
        this.imageUrl = imageUrl;
        this.isStoreOwner = isStoreOwner;
        this.userGender = userGender;
    }

    // 기본 생성자
    public MembersDTO() {}


}
