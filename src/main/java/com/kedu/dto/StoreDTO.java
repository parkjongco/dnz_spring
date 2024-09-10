package com.kedu.dto;

import java.sql.Timestamp;

public class StoreDTO {

    private int storeSeq;
    private int memberId;
    private String category;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private String address; // 추가된 address 필드

    public StoreDTO() {}

    public StoreDTO(int storeSeq, int memberId, String category, String name, String description,
                    Timestamp createdAt, Timestamp modifiedAt, String address) {
        super();
        this.storeSeq = storeSeq;
        this.memberId = memberId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.address = address;
    }

    public int getStoreSeq() {
        return storeSeq;
    }

    public void setStoreSeq(int storeSeq) {
        this.storeSeq = storeSeq;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAddress() {
        return address; // 추가된 address 필드
    }

    public void setAddress(String address) {
        this.address = address; // 추가된 address 필드
    }
}
