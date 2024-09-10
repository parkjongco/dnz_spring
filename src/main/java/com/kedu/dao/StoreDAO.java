package com.kedu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.StoreDTO;

@Repository
public class StoreDAO {

	@Autowired
    private SqlSession mybatis;
	
	public void insertStore(StoreDTO store) {
		
		mybatis.insert("Store.insertStore", store);
	}
	
	// 특정 사용자가 등록한 가게 수를 확인하는 메소드
    public int countStoresByUserId(String userId) {
        return mybatis.selectOne("Store.countStoresByUserId", userId);
    }
	
}
