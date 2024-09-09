package com.kedu.services;

import com.kedu.dao.StoreDAO;
import com.kedu.dto.StoreDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreDAO storeDAO;

    public void registerStore(StoreDTO store) {
        storeDAO.insertStore(store);
    }
}
