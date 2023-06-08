package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.model.dto.EmailAuth;
import com.ssafy.enjoytrip.model.dto.LoginLog;
import com.ssafy.enjoytrip.model.dto.User;
import com.ssafy.enjoytrip.model.mapper.MemberMapper;
import com.ssafy.enjoytrip.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final MailUtils mailUtils;

    @Override
    public User userInfo(String userId) throws SQLException {
        return memberMapper.selectUserById(userId);
    }

    @Override
    public User login(User user) throws SQLException {
        return memberMapper.selectUser(user);
    }

    @Override
    @Transactional
    public void signUp(User user, String domain) throws Exception {
        try {
            // member 테이블에 회원가입
            memberMapper.insertUser(user);

            // email 인증을 위한 UUID 생성 및 email_auth 테이블에 insert
            String UUID = java.util.UUID.randomUUID().toString();
            EmailAuth emailAuth = EmailAuth.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .uuid(UUID).build();

            memberMapper.insertEmailAuth(emailAuth);

            // 계정 활성화를 위한 인증메일 발송
            String email = user.getEmail();
            String subject = "EnjoyTrip 회원가입 이메일 인증 안내";
            String content = "<h2>아래 링크를 클릭하여 계정을 활성화 하세요</h2>" +
                    "<br>" +
                    "<a href=\"" + domain + "/user/email-auth/" + user.getUserId() + "/" + email + "/" + UUID + "\">계정 활성화</a>";
            mailUtils.sendMail(email, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("회원 가입에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public void oAuthSignUp(User user) throws Exception {
        try {
            // member 테이블에 회원가입
            memberMapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("회원 가입에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public void emailAuth(EmailAuth auth) throws SQLException {
        EmailAuth emailAuth = memberMapper.selectEmailAuth(auth);

        if (auth.getUserId().equals(emailAuth.getUserId())
                && auth.getEmail().equals(emailAuth.getEmail())
                && auth.getUuid().equals(emailAuth.getUuid())) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = LocalDateTime.now().format(dtf);

            if (now.compareTo(emailAuth.getUuid()) > 0) throw new RuntimeException("이메일 인증 기간이 만료되었습니다.");

            memberMapper.deleteEmailAuth(emailAuth.getUserId());
            memberMapper.updateUserAuth(emailAuth.getUserId());
        } else {
            throw new RuntimeException("이메일 인증에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public void findPassword(User user) throws Exception {
        User resultUser = memberMapper.selectUserByIdAndEmail(user);
        if (resultUser == null) {
            throw new UnAuthorizedException("유저 정보를 찾을 수 없습니다.");
        }

        String randomPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(randomPassword);
        memberMapper.updateUserPassword(user);

        // 발급한 임시 비밀번호 전송
        String email = user.getEmail();
        String subject = "EnjoyTrip 비밀번호 재발급 안내";
        String content = "<h2>" + user.getUserId() + " 님의 임시 비밀번호 입니다</h2>" +
                "<br>" + randomPassword + "<br>";

        mailUtils.sendMail(email, subject, content);
    }

    @Override
    public void deleteUser(String userId) {
        memberMapper.deleteUser(userId);
    }

    @Override
    public void updateUser(User user) {
        memberMapper.updateUser(user);
    }

    @Override
    public int checkNickName(Map<String, String> map) {
        return memberMapper.selectUserByNickName(map);
    }

    @Override
    public void userLog(LoginLog loginLog) {
        memberMapper.insertLog(loginLog);
    }

    @Override
    public List<LoginLog> getLogList() {
        return memberMapper.selectUserLog();
    }

    @Override
    public List<User> getUserList() {
        return memberMapper.selectUserList();
    }

    @Override
    public void removeUser(String userId) {
        memberMapper.deleteUser(userId);
    }
}
