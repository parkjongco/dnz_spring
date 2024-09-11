package com.kedu.dao;

import com.kedu.dto.PhotosDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotosDAO {

    @Autowired
    private SqlSession mybatis;

    // storeSeq로 사진 데이터를 조회하는 메서드
    public List<PhotosDTO> findPhotosByStoreSeq(int storeSeq) {
        return mybatis.selectList("photos.findPhotosByStoreSeq", storeSeq);
    }
}
