package com.gustavo.tienda_de_chaquetas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcome")
public class WelcomeController {
    @GetMapping("/index")
    public String loginSuccess() {
        return "index";
    }
}
