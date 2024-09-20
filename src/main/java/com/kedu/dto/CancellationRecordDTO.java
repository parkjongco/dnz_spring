package com.kedu.dto;

import java.sql.Timestamp;

public class CancellationRecordDTO {
	
	private int cancellationId;
	private String userId;
	private Timestamp cancellationTime;
	
	public int getCancellationId() {
		return cancellationId;
	}
	public void setCancellationId(int cancellationId) {
		this.cancellationId = cancellationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getCancellationTime() {
		return cancellationTime;
	}
	public void setCancellationTime(Timestamp cancellationTime) {
		this.cancellationTime = cancellationTime;
	}
	
	public CancellationRecordDTO() {}
	
	public CancellationRecordDTO(int cancellationId, String userId, Timestamp cancellationTime) {
		super();
		this.cancellationId = cancellationId;
		this.userId = userId;
		this.cancellationTime = cancellationTime;
	}
	
}
