package com.kedu.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kedu.dao.PhotosDAO;
import com.kedu.dto.PhotosDTO;

@Service
public class PhotosService {

    @Autowired
    private PhotosDAO photosDAO;

    // storeSeq로 사진 데이터를 가져오는 메서드
    public List<PhotosDTO> getPhotosByStoreSeq(int storeSeq) {
        return photosDAO.findPhotosByStoreSeq(storeSeq);
    }
}
