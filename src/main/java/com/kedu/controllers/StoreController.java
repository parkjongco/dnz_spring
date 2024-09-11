package com.kedu.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kedu.dto.StoreDTO;
import com.kedu.services.StoreService;

@RestController
@RequestMapping("/store")
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

    // 가게 등록 API
    @PostMapping("/register")
    public ResponseEntity<String> registerStore(Authentication authentication, @RequestBody StoreDTO store) {
        String userId = authentication.getName();
        
        if (storeService.hasStore(userId)) {
            return ResponseEntity.status(400).body("이미 가게를 소유하고 있습니다.");
        }

        try {
            store.setMemberId(userId);
            storeService.registerStore(store);
            return ResponseEntity.ok("Store registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error registering store");
        }
    }

    // 가게 상세 정보 조회 API
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDTO> getStoreDetails(@PathVariable int storeId) {
        try {
            StoreDTO store = storeService.getStoreDetails(storeId);
            if (store != null) {
                return ResponseEntity.ok(store);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    // 가게 ID로 음식점 이름 조회 API
    @GetMapping("/{storeSeq}/name")
    public ResponseEntity<String> getStoreNameBySeq(@PathVariable int storeSeq) {
        try {
            String storeName = storeService.getStoreNameBySeq(storeSeq);
            if (storeName != null) {
                return ResponseEntity.ok(storeName);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error retrieving store name");
        }
    }
}
