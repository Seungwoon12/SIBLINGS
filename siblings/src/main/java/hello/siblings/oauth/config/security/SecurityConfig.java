package hello.siblings.oauth.config.security;

import hello.siblings.jwt.*;
import hello.siblings.oauth.CustomOAuth2UserService;
import hello.siblings.oauth.handler.OAuth2AuthenticationFailureHandler;
import hello.siblings.oauth.handler.OAuth2AuthenticationSuccessHandler;
import hello.siblings.oauth.repository.CookieAuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정을 위한 클래스
 */
// 기본적인 웹 보안(Spring Security)을 활성화 하겠다.
@EnableWebSecurity
// Controller에서 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 @PreAuthorize 어노테이션을 사용하는데,
// 해당 어노테이션에 대한 설정을 활성화시키는 어노테이션임. 필수는 아님
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler authenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler authenticationFailureHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * WebSecurityConfigurerAdapter가 deprecated 되어서 기존처럼 상속 받아 사용할 수 없고
     * 대신 SecurityFilterChain을 Bean으로 등록해서 사용해야함
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors() // CORS on
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //세션을 사용하지 않기 때문에 세션 설정을 STATELESS로 설정

                .and()
                .csrf().disable() //토큰 방식을 사용하기(stateless) 때문에 csrf 설정을 disable 처리. 서버에 인증정보를 저장하지 않기 때문에 굳이 필요 없음
                .httpBasic().disable() // BASIC Auth off
                .formLogin().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401
                .accessDeniedHandler(jwtAccessDeniedHandler) // 403

                .and()
                .authorizeRequests()//HttpServletRequest를 사용하는 요청들에 대해 접근 제한을 설정하겠다.
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/oauth2/**", "/auth/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() //나머지 요청들은 모두 인증을 받아야한다.


                //h2-console을 위한 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()


                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization") // /oauth2/authorization/{provider-id}
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)

                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*") // /login/oauth2/code/{provider-id}

                .and()
                .userInfoEndpoint()// OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                .userService(customOAuth2UserService) // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록. 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음

                .and()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class) // JwtAuthenticationFilter에서 exception 발생하면 JwtExceptionFilter가 처리하기 위함

                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                            .ignoring()
                            .antMatchers("/h2-console/**","/favicon.ico");
    }


}
