package org.vinhveer.message.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "index";
    }

    @RequestMapping("/login/register")
    public String register() {
        return "register";
    }
}
