package com.kedu.controllers;

import com.kedu.dto.StoreDTO;
import com.kedu.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;
   
    
    

    @PostMapping("/register")
    public ResponseEntity<String> registerStore(
    		Authentication authentication,
    		@RequestBody StoreDTO store) {
    	
    	String userId = authentication.getName();

    	System.out.println(userId);
        // 점주가 이미 가게를 소유하고 있는지 확인
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
            System.out.println("진입했어요오");
            StoreDTO store = storeService.getStoreDetails(storeId);

            if (store != null) {
                return ResponseEntity.ok(store);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 예외 발생 시 스택 트레이스를 출력
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error 반환
        }
    }

    
}
