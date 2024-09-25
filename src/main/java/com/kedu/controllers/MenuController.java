package com.kedu.controllers;

import com.kedu.dto.MenuDTO;
import com.kedu.dto.StoreDTO;
import com.kedu.services.MenuService;
import com.kedu.services.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private StoreService storeService;

 // 메뉴 추가
    @PostMapping
    public ResponseEntity<String> addMenu(Authentication authentication, @RequestBody MenuDTO menu) {
        try {
            // 인증된 사용자의 userId 가져오기
            String userId = authentication.getName();

            // userId를 통해 storeId를 조회

            StoreDTO storeDTO = storeService.getStoreByUserId(userId);

            Integer storeId = storeDTO.getStoreSeq();

            System.out.println(storeId);
            // 메뉴 DTO에 storeId 설정
            menu.setStoreId(storeId);

            // 메뉴 추가
            menuService.addMenu(menu);
            return ResponseEntity.ok("Menu added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error adding menu");
        }
    }

    // 메뉴 업데이트
    @PutMapping("/{menuSeq}")
    public ResponseEntity<String> updateMenu(@PathVariable int menuSeq, @RequestBody MenuDTO menu) {
        try {
        	System.out.println(menuSeq + "2" + menu.getDescription() + "3" + menu.getPrice());
        	menu.setMenuSeq(menuSeq);
            menuService.updateMenu(menu);
            return ResponseEntity.ok("Menu updated successfully");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating menu");
        }
    }

    // 메뉴 삭제
    @DeleteMapping("/{menuSeq}")
    public ResponseEntity<String> deleteMenu(@PathVariable int menuSeq) {
        try {
            menuService.deleteMenu(menuSeq);
            return ResponseEntity.ok("Menu deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting menu");
        }
    }

 // 회원의 유저 ID로 메뉴 조회
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getMenusByUserId(Authentication authentication) {
        try {
            String userId = authentication.getName();
            List<MenuDTO> menus = menuService.getMenusByUserId(userId);
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
//----------------------------

    // 가게 ID로 메뉴 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<MenuDTO>> getMenusByStoreId(@PathVariable int storeId) {
        try {
            List<MenuDTO> menus = menuService.getMenusByStoreId(storeId);
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // 메뉴 ID로 메뉴 조회
    @GetMapping("/{menuSeq}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable int menuSeq) {
        try {
            MenuDTO menu = menuService.getMenuById(menuSeq);
            return ResponseEntity.ok(menu);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
