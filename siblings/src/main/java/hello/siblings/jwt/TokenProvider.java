package hello.siblings.jwt;

import hello.siblings.dto.TokenDto;
import hello.siblings.entity.Member;
import hello.siblings.entity.RefreshToken;
import hello.siblings.oauth.CustomUserDetails;
import hello.siblings.repository.MemberRepository;
import hello.siblings.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * TokenProvider 클래스는 토큰의 생성, 토큰의 유효성 검증등을 담당함
 */
@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private final String secret;
    private final long ACCESS_TOKEN_VALIDITY_SECONDS;
    private final long REFRESH_TOKEN_VALIDITY_SECONDS;
    private final String COOKIE_REFRESH_TOKEN_KEY;
    private static final String AUTHORITIES_KEY = "auth";
    private Key key;
    private final MemberRepository memberRepository;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessToken-validity-in-seconds}") long ACCESS_TOKEN_VALIDITY_SECONDS,
            @Value("${jwt.refreshToken-validity-in-seconds}") long REFRESH_TOKEN_VALIDITY_SECONDS,
            @Value("${app.auth.token.refresh-cookie-key}") String cookieKey,
            MemberRepository memberRepository) {
        this.secret = secret;
        this.ACCESS_TOKEN_VALIDITY_SECONDS = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        this.REFRESH_TOKEN_VALIDITY_SECONDS = REFRESH_TOKEN_VALIDITY_SECONDS * 1000;
        this.COOKIE_REFRESH_TOKEN_KEY = cookieKey;
        this.memberRepository = memberRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *Authentication(유저 정보) 객체의 권한 정보를 이용해서 토큰을 생성하는 역할
     */
    public String createAccessToken(Authentication authentication) {
        // 권한 가져오는 작업
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + this.ACCESS_TOKEN_VALIDITY_SECONDS);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiration)
                .compact();

    }
    // Refresh Token 생성
    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date refreshTokenExpiration = new Date(now.getTime() + this.REFRESH_TOKEN_VALIDITY_SECONDS);
        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        saveRefreshToken(authentication, refreshToken);

        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(REFRESH_TOKEN_VALIDITY_SECONDS / 1000)
                .path("/")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }


    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());

        memberRepository.updateRefreshToken(id, refreshToken);
    }


    /**
     * JWT 토큰을 복호화. accessToken에 담겨있는 권한 정보를 이용해 Authentication 객체를 리턴하는 메소드
     */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        CustomUserDetails principal = new CustomUserDetails(Long.valueOf(claims.getSubject()), "", authorities);
        // credential은 유저의 password를 의미하는듯?
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    /**
     * 토큰의 유효성 검증을 수행하는 validateToken 메소드
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 Access 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }


}
