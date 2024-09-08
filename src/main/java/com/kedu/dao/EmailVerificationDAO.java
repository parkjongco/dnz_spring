package com.kedu.dao;

import com.kedu.dto.EmailVerificationsDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class EmailVerificationDAO {

    @Autowired
    private SqlSession mybatis;


    public void save(EmailVerificationsDTO emailVerificationsDTO) {
        mybatis.insert("email.save", emailVerificationsDTO);
    }

    public void update(EmailVerificationsDTO emailVerificationsDTO) {
        System.out.println("Sequnce : " + emailVerificationsDTO.getVerificationId());
        mybatis.update("email.update", emailVerificationsDTO);
    }

    public EmailVerificationsDTO findByVerificationCode(String verificationCode ,String userEmail ) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("verificationCode", verificationCode);
        hashMap.put("userEmail", userEmail);
        return mybatis.selectOne("email.findByVerificationCode", hashMap);
    }
//
//    public EmailVerificationsDTO findByUserEmail(String userEmail) {
//
//        EmailVerificationsDTO dto =  mybatis.selectOne("email.findByUserEmail", userEmail);
//        System.out.println(dto.getUserEmail());
//        return dto;
//    }
}
