package com.kedu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kedu.dto.PostDTO;
import com.kedu.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 게시물 생성 요청을 처리하는 API
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostDTO postdto) {
        postService.createPost(postdto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 게시물 조회 요청을 처리하는 API (추가)
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 게시물 수정 요청을 처리하는 API (수정)
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable int postId, @RequestBody PostDTO postdto) {
        postService.updatePost(postId, postdto);
        return new ResponseEntity<>(HttpStatus.OK); // 수정 완료
    }

    // 게시물 삭제 요청을 처리하는 API (수정)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 완료
    }
}
