package com.kedu.dao;

import com.kedu.dto.MembersDTO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class MembersDAO {
    @Autowired
    private SqlSession mybatis;

    public MembersDTO findByEmail(String user_Email) {
        return mybatis.selectOne("members.findByEmail", user_Email);
    }

    public MembersDTO findByPhoneNumber(String user_Phone_Number) {
        return mybatis.selectOne("members.findByPhoneNumber", user_Phone_Number);
    }

    public MembersDTO findByUserId(String user_Id) {
        return mybatis.selectOne("members.findByUserId", user_Id);
    }

    public MembersDTO findByUserName(String user_Name) {
        return mybatis.selectOne("members.findByUserName", user_Name);
    }

    public void registerUser(MembersDTO dto) {
        mybatis.insert("members.registerUser", dto);
    }


    public MembersDTO selectById(String user_Id) {
        System.out.println("Executing selectById with user_Id: " + user_Id);
        MembersDTO mdto = mybatis.selectOne("members.selectById", user_Id);
        if (mdto != null) {
            System.out.println("User found: " + mdto.getUserId());
        } else {
            System.out.println("No user found with the provided user_Id.");
        }
        return mdto;
    }

//이메일인증
    public MembersDTO selectByUserIdAndEmail(String userEmail,String userId) {
        HashMap<String ,Object> map = new HashMap<>();
        map.put("userEmail", userEmail);
        map.put("userId", userId);
        return mybatis.selectOne("members.selectByUserIdAndEmail", map);
    }
//비밀번호 업데이트
    public void updateUserPassword(MembersDTO dto) {
        mybatis.update("members.updateUserPassword", dto);
    }


//    아이디찾기
    public String findUserId(String userEmail, String userPhoneNumber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userEmail", userEmail);
        map.put("userPhoneNumber", userPhoneNumber);
        return mybatis.selectOne("members.findUserId", map);
    }

    public MembersDTO existEmail(String user_Email) {
        return mybatis.selectOne("members.findByEmail", user_Email);
    }

    public MembersDTO existId(String user_Phone_Number) {
        return mybatis.selectOne("members.findByPhoneNumber", user_Phone_Number);
    }

    public MembersDTO exsitPhoneNumber(String user_Id) {
        return mybatis.selectOne("members.findByUserId", user_Id);
    }

    public MembersDTO existName(String user_Name) {
        return mybatis.selectOne("members.findByUserName", user_Name);
    }

}

