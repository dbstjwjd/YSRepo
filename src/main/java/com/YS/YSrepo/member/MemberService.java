package com.YS.YSrepo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member createMember(String username, String password, String nickname) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setNickname(nickname);
        member.setJoinDate(LocalDateTime.now());
        memberRepository.save(member);
        return member;
    }

    public boolean checkUsernameExists(String username) {
        return memberRepository.findByUsername(username).isPresent();
    }

    public boolean checkNicknameExists(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    public Member getByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if (member.isPresent()) return member.get();
        else throw new RuntimeException("가입된 회원이 아닙니다.");
    }
}
