package com.kedu.services;

import com.kedu.config.CustomException;
import com.kedu.dao.MembersDAO;
import com.kedu.dao.StoreOwnerDAO;
import com.kedu.dto.MembersDTO;
import com.kedu.dto.StoreOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class MembersService implements UserDetailsService {
    @Autowired
    private MembersDAO membersDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private StoreOwnerService storeOwnerService;

    @Autowired
    private StoreOwnerDAO storeOwnerDAO;

    @Autowired
    public MembersService(PasswordEncoder passwordEncoder, MembersDAO membersDAO) {
        this.passwordEncoder = passwordEncoder;
        this.membersDAO = membersDAO;
    }


 // MembersService.java
    public String saveProfileImage(String userId, MultipartFile profileImage) throws IOException {
        // 파일 저장 경로 설정 및 파일 저장
        String fileName = UUID.randomUUID().toString() + "_" + profileImage.getOriginalFilename();
        
        // uploads 폴더 생성 여부 확인
        Path directoryPath = Paths.get("uploads");
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);  // 폴더가 없으면 생성
        }

        Path filePath = Paths.get("uploads/" + fileName);
        Files.write(filePath, profileImage.getBytes());

        // 저장된 파일 URL을 members 테이블의 imageUrl에 업데이트
        MembersDTO user = membersDAO.findByUserId(userId);
        user.setImageUrl("/uploads/" + fileName);  // 이미지 URL 설정
        membersDAO.updateUserProfile(user);  // 업데이트 수행

        return user.getImageUrl();  // 저장된 이미지 URL 반환
    }


    
    public void registerUser(MembersDTO dto) {
        if (membersDAO.findByEmail(dto.getUserEmail()) != null) {
            throw new CustomException("Email is already taken.");
        }

        if (membersDAO.findByPhoneNumber(dto.getUserPhoneNumber()) != null) {
            throw new CustomException("Phone number is already taken.");
        }

        if (membersDAO.findByUserId(dto.getUserId()) != null) {
            throw new CustomException("User ID is already taken.");
        }
        if (membersDAO.findByUserName(dto.getUserName()) != null) {
            throw new CustomException("User name is already taken.");
        }

        dto.setUserPw(passwordEncoder.encode(dto.getUserPw()));

        if (dto.getRoleCode() == null || dto.getRoleCode().isEmpty()) {
            dto.setRoleCode("ROLE_USER");
        }

        membersDAO.registerUser(dto);
    }



    public void registerStoreOwner(StoreOwnerDTO storeOwnerDTO) {

        storeOwnerDTO.setRoleCode("ROLE_OWNER");
        System.out.println("Role code before DAO call: " + storeOwnerDTO.getRoleCode()); // 여기에 출력문 추가
        // 점주 정보 저장 로직
        storeOwnerDAO.registerStoreOwner(storeOwnerDTO);
    }

//    public void registerOwner(StoreOwnerDTO dto) {
//        // 기본 사용자 등록
//        registerUser(dto); // 일반 사용자 등록 로직 호출
//
//        // 점주 정보 저장
//        storeOwnerService.registerStoreOwner(dto); // StoreOwnerService를 통해 점주 정보 저장
//    }

    @Override
    public UserDetails loadUserByUsername(String user_Id) throws UsernameNotFoundException {
        MembersDTO dto = membersDAO.selectById(user_Id);
        if (dto == null) {
            System.out.println("User not found with ID: " + user_Id);
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + dto.getUserId());
        return new User(dto.getUserId(), dto.getUserPw(), AuthorityUtils.createAuthorityList(dto.getRoleCode()));
    }

    // jik
    // selectById 메서드 추가
    public MembersDTO selectById(String userId) {
        return membersDAO.selectById(userId);
    }


    // 회원가입
    public MembersDTO existEmail(String existEmail) {
        return membersDAO.existEmail(existEmail);
    }

    // 회원가입
    public MembersDTO existId(String userId) {
        return membersDAO.existId(userId);
    }

    // 회원가입
    public MembersDTO existPhoneNumber(String userPhoneNumber) {
        return membersDAO.exsitPhoneNumber(userPhoneNumber);
    }

    // 회원가입
    public MembersDTO existName(String userName) {
        return membersDAO.existName(userName);
    }

    public void updateUserProfile(String userId, Map<String, Object> updatedFields) {
        MembersDTO existingUser = membersDAO.findByUserId(userId);

        // updatedFields 맵에 기반하여 existingUser의 필드 업데이트
        if (updatedFields.containsKey("userName")) {
            existingUser.setUserName((String) updatedFields.get("userName"));
        }
        if (updatedFields.containsKey("userEmail")) {
            existingUser.setUserEmail((String) updatedFields.get("userEmail"));
        }
        if (updatedFields.containsKey("userPhoneNumber")) {
            existingUser.setUserPhoneNumber((String) updatedFields.get("userPhoneNumber"));
        }
        if (updatedFields.containsKey("userBirthDate")) {
            existingUser.setUserBirthDate((String) updatedFields.get("userBirthDate"));
        }
        if (updatedFields.containsKey("userPw")) {
            String encodedPassword = passwordEncoder.encode((String) updatedFields.get("userPw"));
            existingUser.setUserPw(encodedPassword);
        }

        // 프로필 및 비밀번호 업데이트
        membersDAO.updateUserProfile(existingUser);
        if (updatedFields.containsKey("userPw")) {
            membersDAO.updateUserPassword(userId, existingUser.getUserPw());
        }
    }
//    탈퇴

    @Transactional
    public void deleteAccount(String userId, String userSeq) {
        // 활동 테이블에서 사용자 시퀀스 삭제
        membersDAO.deleteActivitiesByUserSeq(userSeq);
        // 회원 테이블에서 사용자 삭제
        membersDAO.deleteMemberByUserId(userId);
    }


}
