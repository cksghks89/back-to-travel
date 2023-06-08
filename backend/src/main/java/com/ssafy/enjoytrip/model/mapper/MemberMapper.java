package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.dto.EmailAuth;
import com.ssafy.enjoytrip.model.dto.LoginLog;
import com.ssafy.enjoytrip.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    User selectUser(User user) throws SQLException;

    User selectUserById(String userId) throws SQLException;

    void insertUser(User user) throws SQLException;

    void insertEmailAuth(EmailAuth emailAuth) throws SQLException;

    EmailAuth selectEmailAuth(EmailAuth auth) throws SQLException;

    void deleteEmailAuth(String userId) throws SQLException;

    void updateUserAuth(String userId) throws SQLException;

    User selectUserByIdAndEmail(User user);

    void updateUserPassword(User user);

    void deleteUser(String userId);

    void updateUser(User user);

    int selectUserByNickName(Map<String, String> map);

    void insertLog(LoginLog loginLog);

    List<LoginLog> selectUserLog();

    List<User> selectUserList();
}
