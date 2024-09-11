package com.kedu.services;

import com.kedu.dao.EmailVerificationDAO;
import com.kedu.dao.MembersDAO;
import com.kedu.dto.EmailVerificationsDTO;
import com.kedu.dto.MembersDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationDAO emailVerificationDAO;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MembersDAO membersDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 이메일 인증 정보 저장
    public void saveVerification(EmailVerificationsDTO emailVerificationsDTO) {
        // 현재 시간과 만료 시간을 설정
        Timestamp now = new Timestamp(new Date().getTime());
        Timestamp expirationTime = new Timestamp(now.getTime() + (60 * 60 * 1000)); // 15분 후 만료

        emailVerificationsDTO.setCreatedAt(now);
        emailVerificationsDTO.setExpirationTime(expirationTime);
        emailVerificationsDTO.setIsVerified('N'); // 초기 상태는 미인증

        emailVerificationDAO.save(emailVerificationsDTO);
    }

    // 인증 이메일 전송
    public void sendVerificationEmail(String toEmail, String verificationCode) {
        try {
            String subject = "이메일 인증 코드";
            String body = String.format(
                    "<p>안녕하세요!</p>" +
                            "<p>아래의 인증 코드를 사용하여 이메일 인증을 완료하세요:</p>" +
                            "<p><b>%s</b></p>" +
                            "<p>이메일 인증은 %d분 내에 완료해 주세요.</p>" +
                            "<p>감사합니다.</p>",
                    verificationCode, 15);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ehdus232323@naver.com"); // application.properties에서 설정된 이메일
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true로 설정하면 HTML 형식으로 전송

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    // 인증 코드 검증
    public boolean verifyCode(String userEmail, String verificationCode) {
        EmailVerificationsDTO dto = emailVerificationDAO.findByVerificationCode(verificationCode,userEmail);
        if (dto != null && dto.getVerificationCode().equals(verificationCode) && dto.getExpirationTime().after(new Timestamp(new Date().getTime()))) {
            dto.setIsVerified('Y');
            System.out.println("UserEmail: " + dto.getUserEmail());
            System.out.println("Verification Code: " + dto.getVerificationCode());
            emailVerificationDAO.update(dto);
            return true;
        }
        return false;
    }

    //회원가입 인증
    public boolean isEmailVerified(String userEmail) {
        EmailVerificationsDTO dto = emailVerificationDAO.findByUserEmail(userEmail);
        return dto != null && dto.getIsVerified() == 'Y';
    }

//비밀번호 찾기
    public boolean resetPassword(String userId,String userEmail) {
        // 1. 사용자 정보를 가져옵니다.
        MembersDTO membersDTO = membersDAO.selectByUserIdAndEmail( userEmail,userId);

        // 2. membersDTO가 null인지 확인
        if (membersDTO == null) {
            System.out.println("사용자 정보가 없습니다. userEmail: " + userEmail + ", userId: " + userId);
            return false;
        } else {
            System.out.println("사용자 정보: " + membersDTO.toString());
        }

        // 3. 임시 비밀번호 생성
        String tempPassword = generateTempPassword();
        System.out.println("임시 비밀번호: " + tempPassword);

        // 4. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(tempPassword);
        System.out.println("암호화된 비밀번호: " + encodedPassword);

        // 5. 사용자 정보 업데이트
        membersDTO.setUserPw(encodedPassword);
        try {
            membersDAO.updateUserPassword(membersDTO);
            System.out.println("비밀번호 업데이트 성공");
        } catch (Exception e) {
            System.out.println("비밀번호 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        }

        // 6. 이메일 전송
        try {
            sendResetPasswordEmail(userEmail, tempPassword);
            System.out.println("비밀번호 재설정 이메일 전송 성공");
        } catch (Exception e) {
            System.out.println("이메일 전송 중 오류 발생: " + e.getMessage());
            return false;
        }

        return true;
    }

    private String generateTempPassword() {
        // 임시 비밀번호 생성 로직 (8자리, 숫자 및 영문 포함)
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    private void sendResetPasswordEmail(String toEmail, String tempPassword) {
        try {
            String subject = "임시 비밀번호";
            String body = String.format(
                    "<p>안녕하세요!</p>" +
                            "<p>아래의 임시 비밀번호를 사용하여 로그인해 주세요:</p>" +
                            "<p><b>%s</b></p>" +
                            "<p>로그인 후, 비밀번호를 변경해 주세요.</p>" +
                            "<p>감사합니다.</p>",
                    tempPassword);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ehdus232323@naver.com"); // 실제 이메일 주소로 변경
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // HTML 형식으로 전송

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send reset password email", e);
        }
    }

    public String findAndSendUserId(String userEmail, String userPhoneNumber) {
        String userId = membersDAO.findUserId(userEmail, userPhoneNumber);

        if (userId != null) {
            sendUserIdEmail(userEmail, userId); // 사용자 ID 전송
            return userId;
        } else {
            return null;
        }
    }


    // 이메일로 사용자 ID를 보내는 메소드
    private void sendUserIdEmail(String toEmail, String userId) {
        try {
            String subject = "귀하의 사용자 ID";
            String body = String.format(
                    "<p>안녕하세요!</p>" +
                            "<p>귀하의 사용자 ID는 다음과 같습니다:</p>" +
                            "<p><b>%s</b></p>" +
                            "<p>감사합니다.</p>",
                    userId);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ehdus232323@naver.com"); // 실제 이메일 주소로 변경
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // HTML 형식으로 전송

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send user ID email", e);
        }
    }


}
