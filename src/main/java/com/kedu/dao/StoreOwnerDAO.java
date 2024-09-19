package com.kedu.dao;

import com.kedu.dto.StoreOwnerDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StoreOwnerDAO {
    @Autowired
    private SqlSession mybatis;

        public void registerStoreOwner(StoreOwnerDTO dto) {
            mybatis.insert("storeOwners.registerStoreOwner", dto);
        }

}
