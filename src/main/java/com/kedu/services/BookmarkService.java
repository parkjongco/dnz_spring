package com.kedu.services;

import com.kedu.dao.BookmarkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkDAO bookmarkDAO;


    public void insertBookmark(int userSeq, int storeSeq) {
        bookmarkDAO.insertBookmark(userSeq,storeSeq);
    }

//    public int getUserSeqByUserId(String userId) {
//        return userRepository.findUserSeqByUserId(userId); // 사용자 ID로 사용자 시퀀스 조회
//    }
//
//    public List<Bookmark> getBookmarksByUserSeq(int userseq) {
//        return bookmarkRepository.findByUserSeq(userseq); // userSeq로 북마크 조회
//    }
}
