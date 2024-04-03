package org.vinhveer.message.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MessageAppController {
    public String index() {
        return "index";
    }
}
