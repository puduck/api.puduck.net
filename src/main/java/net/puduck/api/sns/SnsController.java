package net.puduck.api.sns;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequestMapping("/sns")
@Controller
@AllArgsConstructor
public class SnsController{

    @RequestMapping(value = "")
    public String oauth2Callback(Model model) {

        return "sns";
    }

    @RequestMapping(value = "/callback", produces = "application/x-www-form-urlencoded")
    public String oauth2Callback(HttpServletRequest request) {
        return "sns";
    }
}
