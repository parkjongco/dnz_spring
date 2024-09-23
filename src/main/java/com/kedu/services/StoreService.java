package com.kedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kedu.dao.PhotosDAO;
import com.kedu.dao.StoreDAO;
import com.kedu.dto.PhotosDTO;
import com.kedu.dto.StoreDTO;
import com.kedu.handlers.AlarmHandler;
import com.kedu.dto.ActivitiesDTO;
import com.kedu.services.ActivitiesService;

@Service
public class StoreService {

    @Autowired
    private StoreDAO storeDAO;
    
    @Autowired
    private PhotosDAO photosDAO;

    @Autowired
    private AlarmHandler alarmHandler; // AlarmHandler 주입 (jik)

    @Autowired
    private ActivitiesService activitiesService; // ActivitiesService 주입 (jik)

    // 모든 가게 데이터를 가져오는 메서드
    public List<StoreDTO> getAllStores() {
        return storeDAO.findAllStores();
    }

    // 카테고리로 가게 데이터를 가져오는 메서드
    public List<StoreDTO> getStoresByCategory(String category) {
        return storeDAO.findByCategory(category);
    }

    // 가게 등록 메서드
    public void registerStore(StoreDTO store) {
        storeDAO.insertStore(store);
        
        // (추가) 알림 전송 (jik)
        String notificationMessage = "새로운 가게가 등록되었습니다: " + store.getName();
        alarmHandler.sendNotification(notificationMessage);

        // (추가) userSeq 조회 후 활동 내역 기록 (jik)
        int userSeq = storeDAO.getUserSeqByMemberId(store.getMemberId()); // 서브쿼리로 userSeq 조회
        logActivity(userSeq, "가게 등록", notificationMessage);
    }

    // 특정 사용자의 가게 수 확인
    public boolean hasStore(String userId) {
        return storeDAO.countStoresByUserId(userId) > 0;
    }	

    // 가게 상세 정보 조회
    public StoreDTO getStoreDetails(int storeId) {
        return storeDAO.getStoreById(storeId);
    }
    
    // storeSeq로 음식점 이름 조회
    public String getStoreNameBySeq(int storeId) {
        return storeDAO.getStoreNameBySeq(storeId);
    }
    
    // storeSeq로 사진 가져오기
    public List<PhotosDTO> getStorePhotos(int storeSeq) {
        return photosDAO.findPhotosByStoreSeq(storeSeq);
    }
    
    public StoreDTO getStoreByUserId(String userId) {
        return storeDAO.findStoreByUserId(userId);
    }

    public void updateStore(StoreDTO storeDTO) {
        storeDAO.updateStore(storeDTO);
        
        // (추가) 알림 전송 (jik)
        String notificationMessage = "가게 정보가 수정되었습니다: " + storeDTO.getName();
        alarmHandler.sendNotification(notificationMessage);

        // (추가) userSeq 조회 후 활동 내역 기록 (jik)
        int userSeq = storeDAO.getUserSeqByMemberId(storeDTO.getMemberId()); // 서브쿼리로 userSeq 조회
        logActivity(userSeq, "가게 수정", notificationMessage);
    }

    // 활동 기록을 위한 헬퍼 메서드 (jik)
    private void logActivity(int userSeq, String activityType, String description) {
        ActivitiesDTO activity = new ActivitiesDTO();
        activity.setUserSeq(userSeq);
        activity.setActivityType(activityType);
        activity.setActivityDescription(description);
        activitiesService.logActivity(activity);  // 활동 기록 서비스 호출
    }
}
