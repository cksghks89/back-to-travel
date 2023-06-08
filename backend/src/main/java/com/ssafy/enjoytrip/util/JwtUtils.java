package com.ssafy.enjoytrip.util;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.model.dto.User;
import com.ssafy.enjoytrip.model.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    public static final String SALT = "enjoyTripYK_CH";
    private final MemberService memberService;

    public boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);
            Date expiration = claims.getBody().getExpiration();
            return new Date(System.currentTimeMillis()).before(expiration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnAuthorizedException("token 검증 과정에서 에러가 발생했습니다");
        }
    }

    public String generateAccessToken(String userId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(10, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return generateToken(userId, "access-token", expirationDate);
    }

    public String generateRefreshToken(String userId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(30, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return generateToken(userId, "refresh-token", expirationDate);
    }

    public String generateToken(String userId, String subject, Date date) {
        return Jwts.builder()
                // Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis()) // 생성 시간
                // Payload 설정 : 유효기간(Expiration), 토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
                .setExpiration(date) // 토큰 유효기간
                .setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
                .claim("userId", userId) // 저장할 데이터
                // Signature 설정 : secret key를 활용한 암호화.
                .signWith(SignatureAlgorithm.HS256, SALT)
                .compact(); // 직렬화 처리.
    }

    public String getUserId(String jwt) {
        return (String) getPayload(jwt).get("userId");
    }

    public Map<String, Object> getPayload(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthorizedException("user id를 가져오는 데 실패하였습니다.");
        }
        Map<String, Object> value = claims.getBody();
        return value;
    }

    public User getUserInfo(HttpServletRequest request) throws SQLException {
        String token = request.getHeader("access-token");
        if (!checkToken(token)) {
            throw new UnAuthorizedException("user 정보를 가져오는데 실패하였습니다.");
        }

        String userId = getUserId(token);
        User user = memberService.userInfo(userId);
        if (user == null) {
            throw new UnAuthorizedException("유저 정보를 가져오는데 실패하였습니다.");
        }
        return user;
    }
}
