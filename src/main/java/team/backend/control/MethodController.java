package team.backend.control;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MethodController {
    @GetMapping("template.do")
    public static String m1(@RequestParam(name="na", required = false, defaultValue = "심광섭") String name,@NonNull Model model){
        System.out.println("#m1() 호출 name:"+name);
        model.addAttribute("name", name);
        return "template";
    }
    @ResponseBody
    @GetMapping("string.do")
    public String m2(@RequestParam("na") String name){
        System.out.println("#m2() 호출 name:"+name);
        return name;
    }
    @ResponseBody
    @GetMapping("json.do")
    public Book m3(@RequestParam("title")String title,
                   @RequestParam("price")int price){

        System.out.println("#m3() 호출 title:"+title+", price" + price);
        return new Book(title,price);
    }
}
class Book{
    private String title;
    private  int price;
    Book(String title , int price){
        this.title = title;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
