package com.hecker.exam.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewResolver {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
