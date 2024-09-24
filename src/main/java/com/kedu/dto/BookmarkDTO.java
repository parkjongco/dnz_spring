package com.kedu.dto;

public class BookmarkDTO {
    private int storeSeq;
    private String storeName;

    public BookmarkDTO(int storeSeq, String storeName) {
        this.storeSeq = storeSeq;
        this.storeName = storeName;
    }

    public int getStoreSeq() {
        return storeSeq;
    }

    public void setStoreSeq(int storeSeq) {
        this.storeSeq = storeSeq;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
