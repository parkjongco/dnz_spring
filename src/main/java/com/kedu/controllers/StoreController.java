package com.kedu.controllers;

import com.kedu.dto.StoreDTO;
import com.kedu.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<String> registerStore(@RequestBody StoreDTO store) {
    	System.out.println("엔드포인트진입");
        try {
            storeService.registerStore(store);
            return ResponseEntity.ok("Store registered successfully");
        } catch (Exception e) {
        	e.printStackTrace(); 
            return ResponseEntity.status(500).body("Error registering store");
        }
    }
}
