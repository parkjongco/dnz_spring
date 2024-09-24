package com.kedu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.NoticeDTO;

@Repository
public class NoticeDAO {

    @Autowired
    private SqlSession mybatis;

    public NoticeDTO findNoticeByUserId(String userId) {
        return mybatis.selectOne("Notice.findNoticeByUserId", userId);
    }

    public void insertNotice(NoticeDTO noticeDTO) {
    	mybatis.insert("Notice.insertNotice", noticeDTO);
    }

    public void updateNotice(NoticeDTO noticeDTO) {
    	mybatis.update("Notice.updateNotice", noticeDTO);
    }
    
    public NoticeDTO findNoticeByStoreId(Long storeSeq) {
        return mybatis.selectOne("Notice.findNoticeByStoreId", storeSeq);
    }
}
