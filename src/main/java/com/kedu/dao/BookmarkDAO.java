package com.kedu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class BookmarkDAO {

    @Autowired
    private SqlSession mybatis;


    public void insertBookmark(int userSeq, int storeSeq) {
        HashMap map = new HashMap();
        map.put("userSeq", userSeq);
        map.put("storeSeq", storeSeq);
        mybatis.insert("Bookmark.insertBookmark", map);
    }
}
