package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.dto.EmailAuth;
import com.ssafy.enjoytrip.model.dto.LoginLog;
import com.ssafy.enjoytrip.model.dto.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MemberService {
    User userInfo(String userId) throws SQLException;

    User login(User user) throws SQLException;

    void signUp(User user, String domain) throws Exception;

    void oAuthSignUp(User user) throws Exception;

    void emailAuth(EmailAuth auth) throws SQLException;

    void findPassword(User user) throws Exception;

    void deleteUser(String userId);

    void updateUser(User user);

    int checkNickName(Map<String, String> map);

    void userLog(LoginLog loginLog);

    List<LoginLog> getLogList();

    List<User> getUserList();

    void removeUser(String userId);
}
