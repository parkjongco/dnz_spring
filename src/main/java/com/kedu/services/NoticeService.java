package com.kedu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.dao.NoticeDAO;
import com.kedu.dto.NoticeDTO;

@Service
@Transactional
public class NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    public NoticeDTO getNoticeByUserId(String userId) {
        return noticeDAO.findNoticeByUserId(userId);
    }

   
    public void insertNotice(NoticeDTO noticeDTO) {
        noticeDAO.insertNotice(noticeDTO);
    }

    public void updateNotice(NoticeDTO noticeDTO) {
        noticeDAO.updateNotice(noticeDTO);
    }
    
    
    public NoticeDTO getNoticeByStoreId(Long storeSeq) {
    	return noticeDAO.findNoticeByStoreId(storeSeq);
    }
}
