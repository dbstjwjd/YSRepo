package com.YS.YSrepo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String start() {
        return "start";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
