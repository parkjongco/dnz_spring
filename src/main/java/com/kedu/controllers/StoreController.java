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

        // 점주가 이미 가게를 소유하고 있는지 확인
        if (storeService.hasStore(userId)) {
            return ResponseEntity.status(400).body("이미 가게를 소유하고 있습니다.");
        }
    	
        try {
            storeService.registerStore(store);
            return ResponseEntity.ok("Store registered successfully");
        } catch (Exception e) {
        	e.printStackTrace(); 
            return ResponseEntity.status(500).body("Error registering store");
        }
    }
}
