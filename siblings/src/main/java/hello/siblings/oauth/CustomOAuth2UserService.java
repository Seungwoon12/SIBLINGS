package hello.siblings.oauth;

import hello.siblings.entity.AuthProvider;
import hello.siblings.entity.Member;
import hello.siblings.entity.MemberRole;
import hello.siblings.oauth.exception.OAuthProcessingException;
import hello.siblings.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

  private final MemberRepository memberRepository;


    // OAuth2UserRequest에 있는 Access Token으로 유저 정보 get
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        return process(oAuth2UserRequest, oAuth2User);

    }

    // 획득한 유저 정보를 Java Model과 맵핑 후 프로세스 진행
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        // 현재 진행중인 서비스를 구분하기 위해 문자열로 받음. oAuth2UserRequest.getClientRegistration().getRegistrationId()에 값이 들어있다. {registrationId='naver'} 이런식으로
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        if (userInfo.getEmail().isEmpty()) {
            throw new OAuthProcessingException("Email not found from OAuth2 Provider");
        }

        Optional<Member> memberOptional = memberRepository.findByEmail(userInfo.getEmail());
        Member member;


        if (memberOptional.isPresent()) {       // 이미 가입된 경우
            member = memberOptional.get();
            if (authProvider != member.getAuthProvider()) {
                throw new OAuthProcessingException("Wrong Match Auth Provider");
            }
        } else {        // 가입되지 않은 경우
            member = createMember(userInfo, authProvider);
        }
        return CustomUserDetails.create(member, oAuth2User.getAttributes());


//        // OAuth2 로그인 시 키 값이 된다. 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다. 각각 다르므로 이렇게 따로 변수로 받아서 넣어줘야함.
//        String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        // OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
//        OAuthAttributes attributes = OAuthAttributes.
//                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//        Member member = saveOrUpdate(attributes);
//
//
//        List<GrantedAuthority> authorities = member.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());
//
//
//        return new DefaultOAuth2User(authorities,
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey()
//        );
    }

    private Member createMember(OAuth2UserInfo userInfo, AuthProvider authProvider) {
        Member member = Member.builder()
                .email(userInfo.getEmail())
                .picture(userInfo.getImageUrl())
                .role(MemberRole.USER)
                .activated(true)
                .authProvider(authProvider)
                .build();

        return memberRepository.save(member);

    }


}
