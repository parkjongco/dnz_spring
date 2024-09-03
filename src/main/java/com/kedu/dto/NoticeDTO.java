package com.kedu.dto;

import java.sql.Timestamp;

public class NoticeDTO {
	
	private int noticeSeq;
    private int memberId;
    private String title;
    private String content;
    private int isDeleted;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    
    
	public NoticeDTO() {}


	public NoticeDTO(int noticeSeq, int memberId, String title, String content, int isDeleted, Timestamp createdAt,
			Timestamp modifiedAt) {
		super();
		this.noticeSeq = noticeSeq;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}


	public int getNoticeSeq() {
		return noticeSeq;
	}


	public void setNoticeSeq(int noticeSeq) {
		this.noticeSeq = noticeSeq;
	}


	public int getMemberId() {
		return memberId;
	}


	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public Timestamp getModifiedAt() {
		return modifiedAt;
	}


	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	

}
