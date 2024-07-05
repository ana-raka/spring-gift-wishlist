package gift.controller;


import gift.dto.MemberDto;
import gift.model.member.Member;
import gift.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@RequestMapping("/members")
@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewMember(@RequestBody MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password(),memberDto.role());
        memberService.registerNewMember(member);
        String token = memberService.generateToken(memberDto.email());
        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password(),memberDto.role());
        if (memberService.loginMember(member) ){
            String token = memberService.generateToken(memberDto.email());
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

