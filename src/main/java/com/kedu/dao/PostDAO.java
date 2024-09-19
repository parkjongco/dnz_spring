package com.kedu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.PostDTO;

@Repository
public class PostDAO {

    @Autowired
    private SqlSession mybatis;

    // 게시물 삽입 메서드
    public void insertPost(PostDTO postdto) {
        mybatis.insert("Posts.insertPost", postdto);
    }

    // 게시물 조회 메서드 (추가)
    public List<PostDTO> getAllPosts() {
        return mybatis.selectList("Posts.getAllPosts");
    }

    // 게시물 수정 메서드 (수정)
    public void updatePost(int postId, PostDTO postdto) {
        postdto.setPostId(postId); // postId 설정
        mybatis.update("Posts.updatePost", postdto);
    }

    // 게시물 삭제 메서드 (수정)
    public void deletePost(int postId) {
        mybatis.delete("Posts.deletePost", postId);
    }
}
