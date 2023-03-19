package hello.siblings.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes; // Oauth2UserInfo와 같은 패키지 + 다른 패키지에 있는 클래스중 얘를 상속받은 애들만 접근 가능

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
