package org.vinhveer.message.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @RequestMapping("/home")
    public String home() {
        return "app/index";
    }
}
