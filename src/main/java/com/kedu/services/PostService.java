package com.kedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.PostDAO;
import com.kedu.dto.PostDTO;

@Service
public class PostService {

    @Autowired
    private PostDAO postdao;

    // 게시물 생성 로직
    public void createPost(PostDTO postDto) {
        postdao.insertPost(postDto);
    }

    // 게시물 조회 로직 (추가)
    public List<PostDTO> getAllPosts() {
        return postdao.getAllPosts();
    }

    // 게시물 수정 로직 (수정)
    public void updatePost(int postId, PostDTO postDto) {
        postdao.updatePost(postId, postDto);
    }

    // 게시물 삭제 로직 (수정)
    public void deletePost(int postId) {
        postdao.deletePost(postId);
    }
}
