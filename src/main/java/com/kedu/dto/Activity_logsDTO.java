package com.kedu.dto;

import java.sql.Timestamp;

public class Activity_logsDTO {
	
	private int logId;
	private int userSeq;
	private int roleId;
	private int activityId;
	private String logDescription;
	private Timestamp logDate;
	 
	 
	public Activity_logsDTO() {}


	public Activity_logsDTO(int logId, int userSeq, int roleId, int activityId, String logDescription,
			Timestamp logDate) {
		super();
		this.logId = logId;
		this.userSeq = userSeq;
		this.roleId = roleId;
		this.activityId = activityId;
		this.logDescription = logDescription;
		this.logDate = logDate;
	}


	public int getLogId() {
		return logId;
	}


	public void setLogId(int logId) {
		this.logId = logId;
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


	public int getActivityId() {
		return activityId;
	}


	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}


	public String getLogDescription() {
		return logDescription;
	}


	public void setLogDescription(String logDescription) {
		this.logDescription = logDescription;
	}


	public Timestamp getLogDate() {
		return logDate;
	}


	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}


}
