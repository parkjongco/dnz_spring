package com.kedu.services;

import com.kedu.dao.MenuDAO;
import com.kedu.dto.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDAO menuDAO;

    // 메뉴 추가
    public void addMenu(MenuDTO menu) {
        menuDAO.insertMenu(menu);
    }

    // 가게 ID로 메뉴 조회
    public List<MenuDTO> getMenusByStoreId(int storeId) {
        return menuDAO.findByStoreId(storeId);
    }

    // 메뉴 ID로 메뉴 조회
    public MenuDTO getMenuById(int menuSeq) {
        return menuDAO.findById(menuSeq);
    }

    // 메뉴 업데이트
    public void updateMenu(MenuDTO menu) {
        menuDAO.updateMenu(menu);
    }

    // 메뉴 삭제
    public void deleteMenu(int menuSeq) {
        menuDAO.deleteMenu(menuSeq);
    }
}
