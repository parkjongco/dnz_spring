package com.kedu.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kedu.dto.ActivitiesDTO;
import org.apache.ibatis.session.SqlSession;

@Repository
public class ActivitiesDAO {

    @Autowired
    private SqlSession mybatis;

    // 활동 기록 추가
    public void insertActivity(ActivitiesDTO activity) {
        mybatis.insert("Activities.insertActivity", activity);
    }

    // 특정 사용자의 활동 기록 조회
    public List<ActivitiesDTO> getActivitiesByUserSeq(int userSeq) {
        return mybatis.selectList("Activities.getActivitiesByUserSeq", userSeq);
    }
}
