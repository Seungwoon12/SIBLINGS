package hello.siblings.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "member_name", length = 50, unique = true)
    private String memberName;


    @Column(name = "email", nullable = false)
    private String email;


    @Column(name = "password", length = 100)
    private String password;

    @Column
    private String picture;

    @Column(name = "nickname", length = 50)
    private String nickname;


    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;


    @Column(name = "activated")
    private boolean activated;

    private String refreshToken;



    public Member update(String memberName, String picture) {
        this.memberName = memberName;
        this.picture = picture;

        return this;
    }
}

