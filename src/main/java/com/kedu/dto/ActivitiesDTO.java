package com.kedu.dto;

import java.sql.Timestamp;

public class ActivitiesDTO {
	
	private int activityId;
    private int userSeq;
    private String activityType;
    private String activityDescription;
    private Timestamp activityDate;
    
    
	public ActivitiesDTO() {}


	public ActivitiesDTO(int activityId, int userSeq, String activityType, String activityDescription,
			Timestamp activityDate) {
		super();
		this.activityId = activityId;
		this.userSeq = userSeq;
		this.activityType = activityType;
		this.activityDescription = activityDescription;
		this.activityDate = activityDate;
	}


	public int getActivityId() {
		return activityId;
	}


	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}


	public int getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}


	public String getActivityType() {
		return activityType;
	}


	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}


	public String getActivityDescription() {
		return activityDescription;
	}


	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}


	public Timestamp getActivityDate() {
		return activityDate;
	}


	public void setActivityDate(Timestamp activityDate) {
		this.activityDate = activityDate;
	}
	
	

}