package com.kedu.dao;

import com.kedu.dto.MenuDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDAO {

    @Autowired
    private SqlSession mybatis;  // SqlSession 인스턴스명을 mybatis로 변경

    // 메뉴 추가
    public void insertMenu(MenuDTO menu) {
        mybatis.insert("menu.insertMenu", menu);
    }

    // 가게 ID로 메뉴 조회
    public List<MenuDTO> findByStoreId(int storeId) {
        return mybatis.selectList("menu.findByStoreId", storeId);
    }

    // 메뉴 ID로 메뉴 조회
    public MenuDTO findById(int menuSeq) {
        return mybatis.selectOne("menu.findById", menuSeq);
    }

    // 메뉴 업데이트
    public void updateMenu(MenuDTO menu) {
        mybatis.update("menu.updateMenu", menu);
    }

    // 메뉴 삭제
    public void deleteMenu(int menuSeq) {
        mybatis.delete("menu.deleteMenu", menuSeq);
    }
    
    public List<MenuDTO> findMenusByUserId(String userId) {
        return mybatis.selectList("menu.findMenusByUserId", userId);
    }
}
