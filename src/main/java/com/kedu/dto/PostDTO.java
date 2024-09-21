package com.kedu.dto;

import java.sql.Timestamp;

//PostDto.java
public class PostDTO {

 private int postId;           // 게시물 ID
 private String title;         // 게시물 제목
 private String content;       // 게시물 내용
 private String imageUrl;      // 이미지 파일 경로 또는 URL
 private Timestamp createdAt;  // 생성일자
 private Timestamp updatedAt;  // 수정일자

 public PostDTO() {}
 
 public PostDTO(int postId, String title, String content, String imageUrl, Timestamp createdAt, Timestamp updatedAt) {
	super();
	this.postId = postId;
	this.title = title;
	this.content = content;
	this.imageUrl = imageUrl;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
 }



// Getters and Setters
 public int getPostId() {
     return postId;
 }

 public void setPostId(int postId) {
     this.postId = postId;
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

 public String getImageUrl() {
     return imageUrl;
 }

 public void setImageUrl(String imageUrl) {
     this.imageUrl = imageUrl;
 }

 public Timestamp getCreatedAt() {
     return createdAt;
 }

 public void setCreatedAt(Timestamp createdAt) {
     this.createdAt = createdAt;
 }

 public Timestamp getUpdatedAt() {
     return updatedAt;
 }

 public void setUpdatedAt(Timestamp updatedAt) {
     this.updatedAt = updatedAt;
 }
}
