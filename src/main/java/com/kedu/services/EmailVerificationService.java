package com.kedu.services;

import com.kedu.dao.EmailVerificationDAO;
import com.kedu.dto.EmailVerificationsDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationDAO emailVerificationDAO;

    @Autowired
    private JavaMailSender mailSender;

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

    public boolean isEmailVerified(String userEmail) {
        EmailVerificationsDTO dto = emailVerificationDAO.findByUserEmail(userEmail);
        return dto != null && dto.getIsVerified() == 'Y';
    }

}
