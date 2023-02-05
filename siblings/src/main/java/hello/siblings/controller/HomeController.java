package hello.siblings.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class HomeController {
    @GetMapping("/test")
    @ResponseBody
    public String home(HttpServletRequest request) {
        log.info("home = {}", request.getRequestURL() );
        return "Test Success";
    }
}
