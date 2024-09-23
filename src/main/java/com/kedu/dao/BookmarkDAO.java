package com.kedu.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookmarkDAO {

    @Autowired
    private SqlSession mybatis;

    public void insertBookmark(int userSeq, int storeSeq) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("storeSeq", storeSeq);
        mybatis.insert("Bookmark.insertBookmark", map);
    }

    public void deleteBookmark(int userSeq, int storeSeq) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("storeSeq", storeSeq);
        mybatis.delete("Bookmark.deleteBookmark", map);
    }

    public int isBookmarked(int userSeq, int storeSeq) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("storeSeq", storeSeq);
        return mybatis.selectOne("Bookmark.isBookmarked", map);
    }
}

