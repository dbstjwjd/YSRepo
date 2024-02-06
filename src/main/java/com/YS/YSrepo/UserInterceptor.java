package com.YS.YSrepo;

import com.YS.YSrepo.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Principal principal = request.getUserPrincipal();
            if (principal != null)
                modelAndView.getModelMap().addAttribute("user", memberService.getByUsername(principal.getName()));
        }
    }

}
