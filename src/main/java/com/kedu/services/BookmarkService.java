package com.kedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.dao.BookmarkDAO;
import com.kedu.dto.BookmarkDTO;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkDAO bookmarkDAO;

    @Transactional
    public void insertBookmark(int userSeq, int storeSeq) {
        bookmarkDAO.insertBookmark(userSeq, storeSeq);
    }

    @Transactional
    public void deleteBookmark(int userSeq, int storeSeq) {
        bookmarkDAO.deleteBookmark(userSeq, storeSeq);
    }

    public boolean isBookmarked(int userSeq, int storeSeq) {
        return bookmarkDAO.isBookmarked(userSeq, storeSeq) > 0;
    }

    // 수정: 북마크된 storeSeq와 storeName을 가져오는 메서드
    public List<BookmarkDTO> getBookmarkedStoresWithDetails(int userSeq) {
        return bookmarkDAO.findBookmarkedStoresWithDetailsByUserSeq(userSeq);
    }
}
