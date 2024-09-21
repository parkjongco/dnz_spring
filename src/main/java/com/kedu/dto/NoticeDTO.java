package com.kedu.dto;

import java.sql.Timestamp;

public class NoticeDTO {
    private int noticeSeq;
    private String userId;
    private String content;
    private Timestamp createdAt;
	private Timestamp modifiedAt;
    
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public int getNoticeSeq() {
		return noticeSeq;
	}

	public void setNoticeSeq(int noticeSeq) {
		this.noticeSeq = noticeSeq;
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

	public NoticeDTO(int noticeSeq, String userId, String content, Timestamp createdAt, Timestamp modifiedAt) {
		super();
		this.noticeSeq = noticeSeq;
		this.userId = userId;
		this.content = content;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	
	
	
}
