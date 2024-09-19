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
	private boolean isStoreOwner;
	private StoreOwnerDTO storeData;

	public MembersDTO() {}
	public MembersDTO(int userSeq, String userId, String userPw, String userName, String userPhoneNumber, String userEmail, String userBirthDate, Timestamp writeDate, boolean isStoreOwner, StoreOwnerDTO storeData) {
		this.userSeq = userSeq;
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userBirthDate = userBirthDate;
		this.writeDate = writeDate;
		this.isStoreOwner = isStoreOwner;
		this.storeData = storeData;
	}

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

	public boolean isStoreOwner() {
		return isStoreOwner;
	}

	public void setStoreOwner(boolean storeOwner) {
		isStoreOwner = storeOwner;
	}

	public StoreOwnerDTO getStoreData() {
		return storeData;
	}

	public void setStoreData(StoreOwnerDTO storeData) {
		this.storeData = storeData;
	}
}