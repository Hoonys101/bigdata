package team.backend.control;


//import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.backend.control.domain.Human;
import team.backend.control.domain.ToDo;
import team.backend.control.domain.HumanList;

import java.util.ArrayList;
import java.util.Calendar;

@RequestMapping("test")
@Controller
public class TestController {
    @RequestMapping("")
    public void m00() {
        System.out.println("#m00(): default URL");
    }
    @RequestMapping("/base1")
    public void m01() {
        System.out.println("#m01(): Get, Post, Put, Delete ,... 지원");
    }
    @RequestMapping(value = "/base2", method = RequestMethod.GET)
    public void m02() {
        System.out.println("#m02(): Get방식만 지원");
    }
    @RequestMapping(value = "/base3", method ={ RequestMethod.GET,RequestMethod.POST})
    public void m03() {
        System.out.println("#m03(): Get과 Post 방식 지원");
    }
    @GetMapping(value = "param1")
    public void m04(Human dto) {
        System.out.println("#m04(): dto:" +dto);
    }
    @GetMapping("/param2")
    public void m05(@RequestParam("name") String na, @RequestParam("age") int a) {
        System.out.println("#m05() na: " + na + ", a: " + a);
    }
    @GetMapping("/param3")
    public void m06(@RequestParam("names") ArrayList<String> names) {
        System.out.println("#m06() names: " + names);
    }
    @GetMapping("/param4")
    public void m07(@RequestParam("ns") ArrayList<String> names) {
        System.out.println("#m07() ns: " + names);
    }
   //// @GetMapping("/param5")
   // public void m08(@NotNull @RequestParam("names") String @NotNull [] names) {
     //   System.out.println("#m08() names: " + names);
      //  for(String name: names) System.out.println("name: " + name);
   // }
    @GetMapping("/param6")
    public void m09(HumanList list){
        System.out.println("#m09() list: " + list);
    }
    @GetMapping("/param7")
    public void m10(Human dto, @RequestParam("page") int page) {
        System.out.println("#m10() dto: " + dto + ", page: " + page);
    }
    @GetMapping("/param8")
    public void m11(ToDo dto ) {
        System.out.println("#m11() dto: " + dto);

        java.util.Date d = dto.getCdate();
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int dm = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR);
        int mi = c.get(Calendar.MINUTE);
        int s = c.get(Calendar.SECOND);

        System.out.println(y + "년" + m + "월" + dm + "일" + h + "시" + mi + "분" + s + "초");
    }
        @GetMapping("/json1")
        public ResponseEntity<String> m12(){
            String msg = "{\"name\":\"홍길동\",\"age\":20}";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json;charset=utf-8");
            return new ResponseEntity<String>(msg, headers, HttpStatus.OK);//OK > 상태코드??
        }

        @GetMapping("/json2")
        public  @ResponseBody Human m13(){
            Human man = new Human("이순신", 50);
            return man;
    }
    }

