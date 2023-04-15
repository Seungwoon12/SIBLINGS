package hello.siblings.util.cookie;

import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        /**
         * HttpOnly 설정
         * 자바스크립트를 통한 탈취를 막고자 브라우저에서 쿠키에 접근할 수 없도록 제한하기 위함(XSS 공격 차단).
         * 그러나 클라이언트에서 서버로 전달하는 패킷을 가로채는 해킹방법(스니핑)은 막을 수 없음.
         * 패킷을 캡처하면 쿠키의 정보를 알아낼 수 있음(EX. 무료 와이파이 사용하는 공공장소)
         */
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object obj) {
        /**
         * 직렬화란, Java 시스템 내에서 사용되는 객체를 외부의 Java 시스템에서도 사용할 수 있도록
         * Byte 형태로 데이터를 변환하는 기술과 Byte로 변환된 데이터를 다시 객체로 변환하는 기술을 통틀어 말함.
         * 즉, JVM의 메모리에 상주되어 있는 데이터를 Byte 형태로 변환하는 것
         */
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj)); // Base64 encoded Byte Array로 변환 후 String로 변환
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
