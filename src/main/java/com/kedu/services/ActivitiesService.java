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

    // 활동 기록을 데이터베이스에 저장하는 메서드
    public void logActivity(ActivitiesDTO activity) {
        activitiesDAO.insertActivity(activity); // DAO를 통해 데이터베이스에 저장
    }

    // 특정 사용자의 활동 기록을 조회하는 메서드
    public List<ActivitiesDTO> getUserActivities(int userSeq) {
        return activitiesDAO.getActivitiesByUserSeq(userSeq); // DAO를 통해 활동 로그 조회
    }
}
