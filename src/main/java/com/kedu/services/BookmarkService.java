package com.kedu.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedu.dao.BookmarkDAO;

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
}

