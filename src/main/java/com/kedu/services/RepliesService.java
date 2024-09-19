package com.kedu.services;

import com.kedu.dao.RepliesDAO;
import com.kedu.dto.RepliesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepliesService {

    @Autowired
    RepliesDAO repliesDAO;

    public RepliesDTO addReply(RepliesDTO repliesDTO) {
        repliesDAO.insertReply(repliesDTO);
        return repliesDTO;
    }

   
}
