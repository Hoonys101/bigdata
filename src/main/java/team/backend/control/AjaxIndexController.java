package team.backend.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("ajax")
@Controller
public class AjaxIndexController {
    @GetMapping("test1.do")
    public String test1(){
        return "ajax/test1";
    }
    @GetMapping("test2.do")
    public String test2(){
        return "ajax/test2";
    }
    @GetMapping("test3.do")
    public String test3(){
        return "ajax/test3";
    }
    @GetMapping("test4.do")
    public String test4(){
        return "ajax/test4";
    }
}
