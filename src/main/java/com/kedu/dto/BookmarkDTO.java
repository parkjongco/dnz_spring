package com.kedu.dto;

public class BookmarkDTO {
    private int bookmarkSeq;
    private int userSeq;
    private int storeSeq;

    public int getBookmarkSeq() {
        return bookmarkSeq;
    }

    public void setBookmarkSeq(int bookmarkSeq) {
        this.bookmarkSeq = bookmarkSeq;
    }

    public int getUserSeq() { // 수정: getter 이름 통일
        return userSeq;
    }

    public void setUserSeq(int userSeq) { // 수정: setter 이름 통일
        this.userSeq = userSeq;
    }

    public int getStoreSeq() {
        return storeSeq;
    }

    public void setStoreSeq(int storeSeq) {
        this.storeSeq = storeSeq;
    }

    public BookmarkDTO(int bookmarkSeq, int userSeq, int storeSeq) {
        this.bookmarkSeq = bookmarkSeq;
        this.userSeq = userSeq;
        this.storeSeq = storeSeq;
    }

    public BookmarkDTO() {}
}
