package team.backend.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JspController {
    @GetMapping("main.do")
    public String hi(Model model){
        model.addAttribute("data", "안녕 JSP^^");
        return "main";
    }
}
