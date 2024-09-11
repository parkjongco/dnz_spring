package com.kedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.StoreDAO;
import com.kedu.dto.StoreDTO;




@Service
public class StoreService {

    @Autowired
    private StoreDAO storeDAO;


    // 모든 가게 데이터를 가져오는 메서드
    public List<StoreDTO> getAllStores() {
        return storeDAO.findAllStores();
    }

    // 카테고리로 가게 데이터를 가져오는 메서드
    public List<StoreDTO> getStoresByCategory(String category) {
        return storeDAO.findByCategory(category);
    }

    public void registerStore(StoreDTO store) {
        storeDAO.insertStore(store);
    }
    
    public boolean hasStore(String userId) {
        // 가게가 있는지 확인하는 DAO 메소드 호출
        return storeDAO.countStoresByUserId(userId) > 0;
    }
    
 // 가게 상세 정보 조회
    public StoreDTO getStoreDetails(int storeId) {
        return storeDAO.getStoreById(storeId);
    }
    
}
