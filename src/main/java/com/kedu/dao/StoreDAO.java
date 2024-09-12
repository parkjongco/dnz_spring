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

	
	public void insertStore(StoreDTO store) {
		
		mybatis.insert("store.insertStore", store);
	}
	
	// 특정 사용자가 등록한 가게 수를 확인하는 메소드
    public int countStoresByUserId(String userId) {
        return mybatis.selectOne("store.countStoresByUserId", userId);
    }
    
 // 가게 ID로 가게 정보를 조회하는 메소드
    public StoreDTO getStoreById(int storeId) {
    	return mybatis.selectOne("store.getStoreById", storeId);
    }
    
 // storeSeq로 음식점 이름 조회
    public String getStoreNameBySeq(int storeSeq) {
        return mybatis.selectOne("store.getStoreNameBySeq", storeSeq);
    }
}


