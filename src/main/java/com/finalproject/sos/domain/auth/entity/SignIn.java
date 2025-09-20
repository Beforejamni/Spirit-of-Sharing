package com.finalproject.sos.domain.auth.entity;


import com.finalproject.sos.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
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
}
