package hello.siblings.controller;

import hello.siblings.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/join")
    public String join(@ModelAttribute Member member) {
        log.info("id = {}, pw = {}", member.getMemberId(), member.getMemberPw());
        return "redirect:http://localhost:3000/";
    }

}
