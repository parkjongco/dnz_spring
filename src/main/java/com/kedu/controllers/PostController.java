package com.kedu.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.kedu.dto.PostDTO;
import com.kedu.services.PostService;
import com.kedu.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Storage storage; // Google Cloud Storage 객체 주입

    private final String BUCKET_NAME = "cho-attachment"; // GCS 버킷 이름 설정

    // 게시물 생성 요청을 처리하는 API (파일 또는 Base64 이미지 업로드 가능)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createPost(
            @RequestPart("post") String postJson, // 게시물 정보를 JSON 문자열로 받음
            @RequestPart(value = "file", required = false) MultipartFile file, // 파일 (선택적)
            @RequestPart(value = "base64Image", required = false) String base64Image, // Base64 이미지 (선택적)
            HttpServletRequest request) {
        try {
            // JSON 문자열을 PostDTO 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            PostDTO postdto = objectMapper.readValue(postJson, PostDTO.class);

            // JWT 토큰에서 userSeq 추출
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            int userSeq = jwtUtil.getUserSeq(token); // JWT에서 userSeq 추출
            postdto.setUserSeq(userSeq); // userSeq를 postdto에 설정

            // 파일 업로드 처리 (MultipartFile 또는 Base64)
            if (file != null && !file.isEmpty()) {
                // MultipartFile로 업로드하는 경우
                String imageUrl = uploadFileToGCS(file, "application/octet-stream"); // 파일을 GCS에 업로드하고 URL 반환
                postdto.setImageUrl(imageUrl); // 업로드된 파일의 URL을 게시물에 설정
            } else if (base64Image != null && !base64Image.isEmpty()) {
                // Base64로 이미지 업로드하는 경우
                String imageUrl = uploadBase64ToGCS(base64Image); // Base64 데이터를 GCS에 업로드하고 URL 반환
                postdto.setImageUrl(imageUrl);
            } else {
                postdto.setImageUrl(null); // 파일이 없는 경우 null로 설정
            }

            // 게시물 생성
            postService.createPost(postdto);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시물 수정 요청을 처리하는 API (파일 또는 Base64 이미지 수정 가능)
 // 게시물 수정 요청을 처리하는 API (파일 또는 Base64 이미지 수정 가능)
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable int postId,
            @RequestPart("post") String postJson,
            @RequestPart(value = "file", required = false) MultipartFile file, // 파일 (선택적)
            @RequestPart(value = "base64Image", required = false) String base64Image, // Base64 이미지 (선택적)
            HttpServletRequest request) {
        try {
            // JSON 문자열을 PostDTO 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            PostDTO postdto = objectMapper.readValue(postJson, PostDTO.class);

            // JWT 토큰에서 userSeq 추출
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            int userSeq = jwtUtil.getUserSeq(token); // JWT에서 userSeq 추출
            postdto.setUserSeq(userSeq); // 수정하는 사용자 정보 반영

            // 기존 게시물 정보 가져오기 (기존 이미지 URL 포함)
            PostDTO existingPost = postService.getPostById(postId);

            // 새로운 이미지가 업로드된 경우
            if (file != null && !file.isEmpty()) {
                // 기존 이미지가 있을 경우 GCS에서 삭제
                if (existingPost.getImageUrl() != null && !existingPost.getImageUrl().isEmpty()) {
                    String sysname = extractSysnameFromUrl(existingPost.getImageUrl());
                    deleteFile(sysname, "");
                }
                // 새로운 이미지 업로드
                String imageUrl = uploadFileToGCS(file, "application/octet-stream");
                postdto.setImageUrl(imageUrl);
            } else if (base64Image != null && !base64Image.isEmpty()) {
                // Base64 이미지가 업로드된 경우 기존 이미지 삭제 및 새 이미지 업로드
                if (existingPost.getImageUrl() != null && !existingPost.getImageUrl().isEmpty()) {
                    String sysname = extractSysnameFromUrl(existingPost.getImageUrl());
                    deleteFile(sysname, "");
                }
                String imageUrl = uploadBase64ToGCS(base64Image);
                postdto.setImageUrl(imageUrl);
            } else {
                // 새로운 이미지가 없을 경우 기존 이미지 유지 또는 삭제
                if (postdto.getImageUrl() == null && existingPost.getImageUrl() != null) {
                    // 이미지를 제거하려는 경우 기존 이미지 삭제
                    String sysname = extractSysnameFromUrl(existingPost.getImageUrl());
                    deleteFile(sysname, "");
                    postdto.setImageUrl(null); // 이미지 삭제 후 null로 설정
                } else {
                    // 이미지를 수정하지 않는 경우 기존 이미지 유지
                    postdto.setImageUrl(existingPost.getImageUrl());
                }
            }

            // 게시물 수정
            postService.updatePost(postId, postdto);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 파일을 GCS에 업로드하는 메서드
    private String uploadFileToGCS(MultipartFile file, String contentType) throws Exception {
        String sysname = UUID.randomUUID().toString(); // 고유한 파일 이름 생성
        BlobId blobId = BlobId.of(BUCKET_NAME, sysname);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                    .setContentType(contentType) // 주어진 contentType을 사용
                                    .build();
        storage.create(blobInfo, file.getBytes()); // 파일을 GCS에 업로드

        // 업로드된 파일의 URL 반환
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + sysname;
    }

    // Base64 이미지를 GCS에 업로드하는 메서드
    private String uploadBase64ToGCS(String base64Image) throws Exception {
        // 'data:image/png;base64,'와 같은 접두어 제거
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }

        // Base64 데이터를 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        // 고유한 파일 이름 생성
        String sysname = UUID.randomUUID().toString();
        BlobId blobId = BlobId.of(BUCKET_NAME, sysname);

        // MIME 타입을 application/octet-stream으로 설정
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                    .setContentType("application/octet-stream") // MIME 타입 설정
                                    .build();

        // GCS에 파일 업로드
        storage.create(blobInfo, decodedBytes);

        // 업로드된 파일의 URL 반환
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + sysname;
    }

    // 새로운 이미지 업로드 API 추가
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        try {
            // JWT 토큰에서 userSeq 추출 (필요 시 사용)
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            int userSeq = jwtUtil.getUserSeq(token); // JWT에서 userSeq 추출 (필요에 따라 활용 가능)

            // 파일을 GCS에 업로드
            String imageUrl = uploadFileToGCS(file, "application/octet-stream");

            // 성공적으로 업로드된 파일의 URL 반환
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 파일 조회 요청을 처리하는 API
    @GetMapping("/file/{sysname}/{oriname}")
    public ResponseEntity<ByteArrayResource> getBySysname(@PathVariable String sysname, @PathVariable String oriname) {
        try {
            Blob blob = storage.get(BlobId.of(BUCKET_NAME, sysname)); // sysname으로 Blob 조회

            if (blob == null) {
                return ResponseEntity.notFound().build(); // Blob이 없을 경우 404 반환
            }

            ByteArrayResource resource = new ByteArrayResource(blob.getContent()); // Blob의 내용을 ByteArrayResource로 변환
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + oriname);
            headers.add(HttpHeaders.CONTENT_TYPE, blob.getContentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(blob.getSize())
                    .body(resource); // 파일 내용 반환
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 파일 삭제 요청을 처리하는 API
    @DeleteMapping("/file/{sysname}/{oriname}")
    public ResponseEntity<String> deleteFile(@PathVariable String sysname, @PathVariable String oriname) {
        try {
            BlobId blobId = BlobId.of(BUCKET_NAME, sysname);
            boolean deleted = storage.delete(blobId); // Google Cloud Storage에서 파일 삭제

            if (deleted) {
                return ResponseEntity.ok("File deleted successfully");
            } else {
                return ResponseEntity.notFound().build(); // 파일이 없을 경우 404 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    // 게시물 조회 요청을 처리하는 API
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 게시물 삭제 요청을 처리하는 API
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        try {
            // 삭제하려는 게시물 가져오기
            PostDTO post = postService.getPostById(postId);

            // 게시물에 이미지가 있는 경우
            if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
                // 이미지 URL에서 sysname 추출
                String sysname = extractSysnameFromUrl(post.getImageUrl());
                // GCS에서 이미지 삭제
                deleteFile(sysname, "");
            }

            // 게시물 삭제
            postService.deletePost(postId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 이미지 URL에서 sysname을 추출하는 메서드
    private String extractSysnameFromUrl(String imageUrl) {
        // URL에서 파일 이름(sysname)을 추출하는 간단한 로직
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}
