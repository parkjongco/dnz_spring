package com.kedu.dto;

public class PhotosDTO {

    private int photoId;
    private int storeSeq;
    private String imageUrl;

    public PhotosDTO() {}

    public PhotosDTO(int photoId, int storeSeq, String imageUrl) {
        this.photoId = photoId;
        this.storeSeq = storeSeq;
        this.imageUrl = imageUrl;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getStoreSeq() {
        return storeSeq;
    }

    public void setStoreSeq(int storeSeq) {
        this.storeSeq = storeSeq;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
