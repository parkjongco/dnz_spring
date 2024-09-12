package com.kedu.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kedu.dao.ActivitiesDAO;
import com.kedu.dto.ActivitiesDTO;

@Service
public class ActivitiesService {

    @Autowired
    private ActivitiesDAO activitiesDAO;

    // 활동 기록을 DB에 저장하고 activity_id 반환
    public int logActivity(ActivitiesDTO activity) {
        return activitiesDAO.insertActivity(activity);  // 활동 기록 후 ID 반환
    }

    // 특정 사용자의 활동 기록을 조회
    public List<ActivitiesDTO> getUserActivities(int userSeq) {
        return activitiesDAO.getActivitiesByUserSeq(userSeq);  // userSeq 기반으로 활동 조회
    }

    // 특정 사용자의 읽지 않은 활동 기록을 조회
    public List<ActivitiesDTO> getUnreadActivities(int userSeq) {
        return activitiesDAO.getUnreadActivitiesByUserSeq(userSeq);  // userSeq 기반으로 읽지 않은 활동 조회
    }

    // 특정 사용자의 모든 활동을 읽음 상태로 업데이트
    public void markActivitiesAsReadByUserSeq(int userSeq) {
        activitiesDAO.markActivitiesAsReadByUserSeq(userSeq);  // userSeq 기반으로 읽음 처리
    }

    // **새롭게 추가된 메서드**
    // 특정 활동을 읽음 상태로 업데이트
    public boolean markActivityAsRead(int userSeq, int activityId) {
        int updatedRows = activitiesDAO.markActivityAsRead(userSeq, activityId);  // userSeq 및 activityId를 기반으로 활동 읽음 처리
        return updatedRows > 0;  // 업데이트된 행이 있으면 true 반환
    }
}
