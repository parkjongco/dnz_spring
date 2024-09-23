package com.kedu.dto;

public class BookmarkDTO {
    private int bookmarkSeq;
    private String userSeq;
    private int storeSeq;

    public int getBookmarkSeq() {
        return bookmarkSeq;
    }

    public void setBookmarkSeq(int bookmarkSeq) {
        this.bookmarkSeq = bookmarkSeq;
    }

    public String getUserId() {
        return userSeq;
    }

    public void setUserId(String userId) {
        this.userSeq = userId;
    }

    public int getStoreSeq() {
        return storeSeq;
    }

    public void setStoreSeq(int storeSeq) {
        this.storeSeq = storeSeq;
    }

    public BookmarkDTO(int bookmarkSeq, String userId, int storeSeq) {
        this.bookmarkSeq = bookmarkSeq;
        this.userSeq = userId;
        this.storeSeq = storeSeq;
    }

    public BookmarkDTO() {}


}
