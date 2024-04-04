package team.backend.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.backend.domain.Address;
import team.backend.service.AddressAjaxService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("auto")
@Controller
public class AutoController {

    private AddressAjaxService service;

    @GetMapping("auto.do")
    public String showView(){
        return "auto/autocomplete";
    }
    @PostMapping("autoData.do")
    @ResponseBody
    public List<Address> getAddressList(@RequestParam("writer") String writer){
        List<Address> list = service.getListByNames(writer);
        return list;
    }
}
