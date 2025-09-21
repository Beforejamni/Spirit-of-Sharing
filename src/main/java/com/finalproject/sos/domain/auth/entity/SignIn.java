package com.finalproject.sos.domain.auth.entity;


import com.finalproject.sos.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class SignIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long signInId;

    @Column(unique = true, nullable = false, length = 12)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "signIn", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private Member member;


    @Builder
    private SignIn(String username, String password){
        this.username = username;
        this.password = password;
    }
}
