package com.kedu.controllers;

import com.kedu.dto.StoreDTO;
import com.kedu.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // 모든 가게 데이터를 가져오는 API
    @GetMapping("/all")
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    // 카테고리로 가게 데이터를 가져오는 API
    @GetMapping("/category/{category}")
    public ResponseEntity<List<StoreDTO>> getStoresByCategory(@PathVariable String category) {
        List<StoreDTO> stores = storeService.getStoresByCategory(category);
        return ResponseEntity.ok(stores);
    }

    // 새로운 가게 데이터를 추가하는 API
    @PostMapping("/add")
    public ResponseEntity<String> addStore(@RequestBody StoreDTO store) {
        storeService.addStore(store);
        return ResponseEntity.ok("Store added successfully!");
    }
}


