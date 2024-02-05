package com.YS.YSrepo.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "아이디 입력란을 확인해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호 입력란을 확인해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인란을 확인해주세요.")
    private String password2;

    @NotEmpty(message = "닉네임 입력란을 확인해주세요.")
    private String nickname;
}
