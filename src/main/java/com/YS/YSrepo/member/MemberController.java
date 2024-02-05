package com.YS.YSrepo.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String signup(SignupForm signupForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "user/signup";
        } else if (!signupForm.getPassword1().equals(signupForm.getPassword2())) {
            model.addAttribute("errorMessage",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "user/signup";
        }
        memberService.createMember(signupForm.getUsername(), signupForm.getPassword1(), signupForm.getNickname());
        return "redirect:/member/login";
    }

}
