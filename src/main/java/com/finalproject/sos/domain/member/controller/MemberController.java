package com.finalproject.sos.domain.member.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.member.address.dto.response.UpdateAddress;
import com.finalproject.sos.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/member")
public class MemberController {


    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<Map<String, String>> updateMemberAddress(@AuthenticationPrincipal CustomUserDetails userDetails ,
                                                                   @RequestBody UpdateAddress updateAddress){

        return ResponseEntity.ok().body( memberService.updateMemberAddress(userDetails,updateAddress));
    }
}
