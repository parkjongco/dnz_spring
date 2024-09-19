package com.kedu.dto;

import java.sql.Timestamp;

public class RepliesDTO {
	private int replySeq;
	private int reviewId;
	private int storeSeq;
	private String userId;
	private String replyText;
	private Timestamp createdAt;
	public int getReplySeq() {
		return replySeq;
	}
	public void setReplySeq(int replySeq) {
		this.replySeq = replySeq;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getStoreSeq() {
		return storeSeq;
	}
	public void setStoreSeq(int storeSeq) {
		this.storeSeq = storeSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public RepliesDTO(int replySeq, int reviewId, int storeSeq, String userId, String replyText, Timestamp createdAt) {
		super();
		this.replySeq = replySeq;
		this.reviewId = reviewId;
		this.storeSeq = storeSeq;
		this.userId = userId;
		this.replyText = replyText;
		this.createdAt = createdAt;
	}
	
	RepliesDTO(){
		
	}
}
