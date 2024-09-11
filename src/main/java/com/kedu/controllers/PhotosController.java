package com.kedu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kedu.dto.PhotosDTO;
import com.kedu.services.PhotosService;

@RestController
@RequestMapping("/photos")
public class PhotosController {

    @Autowired
    private PhotosService photosService;

    // 가게의 사진 데이터를 가져오는 API
    @GetMapping("/{storeSeq}")
    public ResponseEntity<List<PhotosDTO>> getPhotosByStoreSeq(@PathVariable int storeSeq) {
        List<PhotosDTO> photos = photosService.getPhotosByStoreSeq(storeSeq);
        return ResponseEntity.ok(photos);
    }
}
