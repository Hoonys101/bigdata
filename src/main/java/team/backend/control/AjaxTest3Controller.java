package team.backend.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.backend.domain.Address;
import team.backend.service.AddressAjaxService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("ajax3")
@Controller
public class AjaxTest3Controller {
    private AddressAjaxService service;

    @GetMapping("search1.do")
    @ResponseBody
    public Address search1(@RequestParam("seq") long seq){
        Address address = service.getBySeqS(seq);
        return address;
    }
    @PostMapping("search2.do")
    @ResponseBody
    public List<Address> search2(@RequestParam("name") String name){
        List<Address> list = service.getListByNames(name);
            return list;
    }
}
