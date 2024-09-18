package com.kedu.dto;

import java.sql.Timestamp;

public class ActivitiesDTO {

    private int activityId;
    private int userSeq;
    private String activityType;
    private String activityDescription;
    private Timestamp activityDate;
    private int isRead;  // 읽음 여부를 나타내는 필드 (0: 읽지 않음, 1: 읽음)

    public ActivitiesDTO() {
    }

    public ActivitiesDTO(int activityId, int userSeq, String activityType, String activityDescription,
                         Timestamp activityDate, int isRead) {
        this.activityId = activityId;
        this.userSeq = userSeq;
        this.activityType = activityType;
        this.activityDescription = activityDescription;
        this.activityDate = activityDate;
        this.isRead = isRead;
    }

    // Getter and Setter methods for all fields

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

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
