package hello.siblings.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
public class Member {

    private Long memberNo;
    private String memberId;
    private String memberPw;
}
