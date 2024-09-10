package com.kedu.dao;

import com.kedu.dto.StoreDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreDAO {

    @Autowired
    private SqlSession mybatis;

    // 모든 가게 데이터를 가져오는 메서드
    public List<StoreDTO> findAllStores() {
        return mybatis.selectList("store.findAllStores");
    }

    // 카테고리로 가게 데이터를 가져오는 메서드
    public List<StoreDTO> findByCategory(String category) {
        return mybatis.selectList("store.findByCategory", category);
    }

    // 새로운 가게 데이터를 추가하는 메서드
    public void insertStore(StoreDTO store) {
        mybatis.insert("store.insertStore", store);
    }
}
