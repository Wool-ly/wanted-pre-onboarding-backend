package com.preonboarding.wanted.security;

import com.preonboarding.wanted.entity.UserRole;
import com.preonboarding.wanted.exception.ErrorCode;
import com.preonboarding.wanted.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.token.key}")
    private String secretKey;

    //토큰 유효시간 설정
    private long tokenValidTime = 1000L * 60 * 30; // 30분

    //secretkey를 미리 인코딩 해줌.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT 토큰 생성
    public String createToken(String email, UserRole role) {

        //payload 설정
        //registered claims
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access_token") //토큰제목
                .setIssuedAt(now) //발행시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)); // 토큰 만료기한

        //private claims
        claims.put("email", email); // 정보는 key - value 쌍으로 저장.
        claims.put("role", role);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT") //헤더
                .setClaims(claims) // 페이로드
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
                .compact();
    }

    // 토큰의 유효성 + 만료일자 확인  // -> 토큰이 expire되지 않았는지 True/False로 반환해줌.
    public boolean validateToken(HttpServletRequest request, String jwtToken) {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || bearerToken.trim().isEmpty() || bearerToken.equals("Bearer") || bearerToken.equals("Bearer ")) {
            log.info("WRONG_BEARER_TOKEN");
            throw new JwtException(ErrorCode.MISSING_TOKEN.getMessage());
        }
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (SecurityException e) {
            log.info("SignatureException");
            throw new JwtException(ErrorCode.MALFORMED_TOKEN.getMessage());
        }catch (MalformedJwtException e) {
            log.info("MalformedJwtException");
            throw new JwtException(ErrorCode.MALFORMED_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException");
            throw new JwtException(ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("UnsupportedJwtException");
            throw new JwtException(ErrorCode.UNSUPPORTED_TOKEN.getMessage());
        }
    }

    //JWT 토큰에서 인증정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token) {
        try {
            return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

}
