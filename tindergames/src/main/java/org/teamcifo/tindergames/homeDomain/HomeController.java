package org.teamcifo.tindergames.homeDomain;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String RESPONSE_MESSAGE = "responseMessage";

    @GetMapping({"/", ""})
    public String index(Model containerToView) {
        return "index";
    }

    @GetMapping({"/aboutus"})
    public String aboutUs(Model containerToView) {
        return "frontend/aboutus";
    }
}
