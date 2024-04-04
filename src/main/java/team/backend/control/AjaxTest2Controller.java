package team.backend.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RequestMapping("ajax2")
@Controller
public class AjaxTest2Controller {
    private AddressAjaxService service;

    @GetMapping("search1.do")
    public void search1(@RequestParam("seq") long seq, HttpServletResponse response){
        Address address = service.getBySeqS(seq);

        ObjectMapper om = new ObjectMapper();
        try{
            String addressJson = om.writeValueAsString(address);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(addressJson);
        }catch(JsonProcessingException je) {
        }catch(IOException ie){}
    }
    @PostMapping("search2.do")
    public void search2(@RequestParam("name") String name, HttpServletResponse response){
        List<Address> list = service.getListByNames(name);

        ObjectMapper om = new ObjectMapper();
        try{
            String addressJson = om.writeValueAsString(list);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(addressJson);
        }catch(JsonProcessingException je) {
        }catch(IOException ie){}
    }

}