package com.kedu.controllers;

import com.kedu.dto.MenuDTO;
import com.kedu.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 메뉴 추가
    @PostMapping("/add")
    public ResponseEntity<String> addMenu(@RequestBody MenuDTO menu) {
        try {
            menuService.addMenu(menu);
            return ResponseEntity.ok("Menu added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding menu");
        }
    }

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

    // 메뉴 업데이트
    @PutMapping("/update")
    public ResponseEntity<String> updateMenu(@RequestBody MenuDTO menu) {
        try {
            menuService.updateMenu(menu);
            return ResponseEntity.ok("Menu updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating menu");
        }
    }

    // 메뉴 삭제
    @DeleteMapping("/delete/{menuSeq}")
    public ResponseEntity<String> deleteMenu(@PathVariable int menuSeq) {
        try {
            menuService.deleteMenu(menuSeq);
            return ResponseEntity.ok("Menu deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting menu");
        }
    }
}
