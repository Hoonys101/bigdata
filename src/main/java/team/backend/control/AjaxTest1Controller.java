package team.backend.control;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.backend.domain.Address;
import team.backend.service.AddressAjaxService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequestMapping("ajax1")
@Controller
public class AjaxTest1Controller {
    @Autowired
    private AddressAjaxService service;

    @GetMapping("search1.do")
    public void search1(@RequestParam("seq") long seq, HttpServletResponse response){
        Address address = service.getBySeqS(seq);
        pln("#AjaxTest1Controller search1() address: " + address);

        String addressJson = null;
        if(address != null){
            addressJson = "{\"seq\":\""+address.getSeq()
                    +"\", \"name\":\""+address.getName()
                    +"\", \"addr\":\""+address.getAddr()
                    +"\", \"rdate\":\""+address.getRdate()
                    +"\"}";
        }
        try{
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(addressJson);
        }catch(IOException ie){}
    }
    @PostMapping("search2.do")
    public void search2(@RequestParam("name") String name, HttpServletResponse response){
        List<Address> list = service.getListByNames(name);
        pln("#AjaxTest1Controller search2() list: " + list);

        String addressJson = null;
        if(list.size() != 0){
            addressJson = "[";
            for(Address address: list){
                addressJson += "{\"seq\":\""+address.getSeq()
                        +"\", \"name\":\""+address.getName()
                        +"\", \"addr\":\""+address.getAddr()
                        +"\", \"rdate\":\""+address.getRdate()
                        +"\"}";
                addressJson += ",";
            }
            addressJson = addressJson.substring(0, addressJson.length()-1);
            addressJson += "]";
        }else{
            addressJson = "[]";
        }

        try{
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(addressJson);
        }catch(IOException ie){}
    }

    void pln(String str){
        System.out.println(str);
    }
}
