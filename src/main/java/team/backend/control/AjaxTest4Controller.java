package team.backend.control;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.backend.domain.Address;
import team.backend.service.AddressAjaxService;

import java.util.List;

//Rest 서비스 >  view 는말고 데이터만 넘겨준다?
@AllArgsConstructor
@RequestMapping("ajax4")
@RestController //@ResponseBody를 안써도 리턴이 데이터 의미한다
public class AjaxTest4Controller {
    private AddressAjaxService service;
    @GetMapping("search1.do")
    public Address search1(@RequestParam("seq") long seq){
        Address address = service.getBySeqS(seq);
        return address;
    }
    @PostMapping("search2.do")
    public List<Address> search2(@RequestParam("name") String name){
        List<Address> list = service.getListByNames(name);
        return list;
    }
    @GetMapping("txt") //얘는 뭘리턴?
    public String getText(){
        return "good";
    }
}
