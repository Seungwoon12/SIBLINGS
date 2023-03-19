//package hello.siblings.controller;
//
//import hello.siblings.dto.LoginDto;
//import hello.siblings.dto.MemberDto;
//import hello.siblings.dto.TokenDto;
//import hello.siblings.entity.Member;
//import hello.siblings.jwt.TokenProvider;
//import hello.siblings.oauth.CustomUserDetails;
//import hello.siblings.repository.MemberRepository;
//import hello.siblings.service.MemberService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@RestController
//@Slf4j
//@RequestMapping("/member")
//public class MemberController {
//
//    private final MemberRepository memberRepository;
//    public MemberController(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
//    public Member getCurrentMember(@AuthenticationPrincipal CustomUserDetails user) {
//        return memberRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("Not Found User"));
//    }
//
//
//}
