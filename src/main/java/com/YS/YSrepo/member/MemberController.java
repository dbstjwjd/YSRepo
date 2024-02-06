package com.YS.YSrepo.member;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody String userdata) {
        JSONObject jsonObject = new JSONObject(userdata);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String nickname = jsonObject.getString("nickname");
        memberService.createMember(username, password, nickname);
        return "'" + nickname + "'님 가입을 환영합니다!";
    }

    @PostMapping("/check/username")
    @ResponseBody
    public boolean checkUsername(String username) {
        return memberService.checkUsernameExists(username);
    }

    @PostMapping("/check/nickname")
    @ResponseBody
    public boolean checkNickname(String nickname) {
        return memberService.checkNicknameExists(nickname);
    }
}
