package com.kedu.services;

import com.kedu.dao.StoreOwnerDAO;
import com.kedu.dto.StoreOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreOwnerService {
    @Autowired
    private StoreOwnerDAO storeOwnerDAO;

    public void registerStoreOwner(StoreOwnerDTO storeOwnerDTO) {
        // 점주 정보 저장 로직
        storeOwnerDAO.registerStoreOwner(storeOwnerDTO);
    }
}
