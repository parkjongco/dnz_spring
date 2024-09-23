package com.kedu.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.PostDAO;
import com.kedu.dto.ActivitiesDTO;
import com.kedu.dto.PostDTO;
import com.kedu.handlers.AlarmHandler;

@Service
public class PostService {

    @Autowired
    private PostDAO postdao;

    @Autowired
    private AlarmHandler alarmHandler;  // AlarmHandler 주입

    @Autowired
    private ActivitiesService activitiesService; // ActivitiesService 주입

    // 게시물 생성 로직
    public void createPost(PostDTO postDto) {
        postdao.insertPost(postDto);

        // (추가) 공지사항 등록 시 모든 사용자에게 WebSocket 알림 전송
        String notificationMessage = "새로운 공지사항이 등록되었습니다: " + postDto.getTitle();
        alarmHandler.sendNotification(notificationMessage);  // 알림 전송

        // (추가) 활동 내역 기록
        logActivity(postDto.getUserSeq(), "공지 등록", notificationMessage);
    }

    // 게시물 수정 로직
    public void updatePost(int postId, PostDTO postDto) {
        postdao.updatePost(postId, postDto);

        // (추가) 공지사항 수정 시 모든 사용자에게 WebSocket 알림 전송
        String notificationMessage = "공지사항이 수정되었습니다: " + postDto.getTitle();
        alarmHandler.sendNotification(notificationMessage);  // 수정 알림 전송

        // (추가) 활동 내역 기록
        logActivity(postDto.getUserSeq(), "공지 수정", notificationMessage);
    }

    // 게시물 삭제 로직
    public void deletePost(int postId) {
        PostDTO post = postdao.getPostById(postId); // 삭제할 게시물 정보 조회
        postdao.deletePost(postId);

        // (추가) 공지사항 삭제 시 WebSocket 알림 전송
        String notificationMessage = "공지사항이 삭제되었습니다: " + post.getTitle();
        alarmHandler.sendNotification(notificationMessage);  // 삭제 알림 전송

        // (추가) 활동 내역 기록
        logActivity(post.getUserSeq(), "공지 삭제", notificationMessage);
    }

    // (추가) 게시물 목록 조회 로직
    public List<PostDTO> getAllPosts() {
        return postdao.getAllPosts(); // DAO에서 모든 게시물 가져오기
    }

    // (추가) 단일 게시물 조회 로직
    public PostDTO getPostById(int postId) {
        return postdao.getPostById(postId);
    }

    // 활동 기록을 위한 헬퍼 메서드 (추가)
    private void logActivity(int userSeq, String activityType, String description) {
        ActivitiesDTO activity = new ActivitiesDTO();
        activity.setUserSeq(userSeq);
        activity.setActivityType(activityType);
        activity.setActivityDescription(description);
        activitiesService.logActivity(activity);  // 활동 기록 서비스 호출
    }
}
