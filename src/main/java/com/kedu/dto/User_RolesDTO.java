package com.kedu.dto;

public class User_RolesDTO {

	private int userSeq;
	private int roleId;


	public User_RolesDTO() {}


	public User_RolesDTO(int userSeq, int roleId) {
		super();
		this.userSeq = userSeq;
		this.roleId = roleId;
	}


	public int getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}
