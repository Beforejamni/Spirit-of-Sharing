package com.finalproject.sos.domain.auth.entity;


import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.member.entity.Member;
import jakarta.persistence.*;


@Entity
@Table(name = "social_account",
    uniqueConstraints = @UniqueConstraint(name = "provider_social_id",
                                          columnNames = {"social_provider", "socialId"})
)
public class SocialAccount extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialAccountId;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private SocialProvider socialProvider;


    @OneToOne(mappedBy = "socialAccount", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private Member member;

}
