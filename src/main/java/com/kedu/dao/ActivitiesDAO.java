package com.kedu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kedu.dto.ActivitiesDTO;

@Repository
public class ActivitiesDAO {

    @Autowired
    private SqlSession mybatis;

    // 활동 기록 추가: 새로운 활동 기록을 데이터베이스에 삽입하고 activity_id 반환
    public int insertActivity(ActivitiesDTO activity) {
        mybatis.insert("Activities.insertActivity", activity);  // 새로운 활동 삽입
        return activity.getActivityId();  // 삽입 후 생성된 activity_id 반환
    }

    // 특정 사용자의 활동 기록 조회: 사용자의 시퀀스를 기반으로 모든 활동을 가져옴
    public List<ActivitiesDTO> getActivitiesByUserSeq(int userSeq) {
        return mybatis.selectList("Activities.getActivitiesByUserSeq", userSeq);  // userSeq 기반 활동 조회
    }

    // 특정 사용자의 읽지 않은 활동 기록만 조회: 사용자의 시퀀스와 읽지 않은 상태(is_read = 0)에 맞는 활동만 가져옴
    public List<ActivitiesDTO> getUnreadActivitiesByUserSeq(int userSeq) {
        return mybatis.selectList("Activities.getUnreadActivitiesByUserSeq", userSeq);  // 읽지 않은 활동만 조회
    }

    // 특정 사용자의 모든 활동을 읽음 상태로 업데이트: 사용자가 알림을 확인했을 때 해당 활동을 읽음 상태로 변경
    public void markActivitiesAsReadByUserSeq(int userSeq) {
        mybatis.update("Activities.markActivitiesAsReadByUserSeq", userSeq);  // userSeq 기반 읽음 상태 업데이트
    }

    // **새롭게 추가된 메서드**
    // 특정 활동을 읽음 상태로 업데이트: activityId를 기반으로 해당 활동을 읽음 처리
    public int markActivityAsRead(int userSeq, int activityId) {
        return mybatis.update("Activities.markActivityAsRead", Map.of("userSeq", userSeq, "activityId", activityId));  // userSeq 및 activityId 기반 읽음 상태 업데이트
    }
}
