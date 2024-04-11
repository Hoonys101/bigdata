package team.backend.control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.backend.domain.AvailableData;
import team.backend.domain.Member;
import team.backend.service.JavaPythonInter;
import team.backend.service.MybatisAvailableDataService;
import team.backend.service.MybatisMemberService;
import team.backend.service.addDataService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@RequestMapping("project")
@Controller
//@SessionAttributes("id")
public class pageController {
    @Autowired
    private MybatisMemberService service;
    @Autowired
    private MybatisAvailableDataService availableDataService;
    @Autowired
    private addDataService addData;
    @Autowired
    private JavaPythonInter javaPy;

    @GetMapping("home.do") //메인화면
    public String home(HttpSession session, HttpServletRequest request) {
        System.out.println("회원가입");
        AvailableData availableData = (AvailableData) session.getAttribute("availableData");

        return "/project/home";
    }
    @GetMapping("login.do") //로그인화면
    public String login(HttpSession session) {
        // 이미 로그인한 사용자라면 홈 페이지로 리다이렉트
        if (session.getAttribute("id") != null) {
            return "redirect:home.do";
        }
        return "/project/login";
    }
    // POST 방식으로 "/login" 엔드포인트에 접근할 때 사용자의 아이디와 비밀번호를 확인하여 로그인합니다.
    @PostMapping("login.do") //로그인
    public String login(@RequestParam("id") String id,
                        @RequestParam("pwd") String pwd,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        if (service.login(id, pwd)) {
            session.setAttribute("id", id);
            return "redirect:home.do"; // 로그인 성공 시 메인 페이지로 이동
        } else {
            redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "redirect:login.do"; // 로그인 실패 시 다시 로그인 페이지로 이동
        }
    }
    @GetMapping("logout.do") //로그아웃
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        System.out.println("logout test");
        //Enumeration<String> attributeNames = session.getAttributeNames();
        //while (attributeNames.hasMoreElements()) {
          //  String attributeName = attributeNames.nextElement();
            //session.removeAttribute(attributeName);
        //}
        //session.removeAttribute("id"); // 세션 없앤거 확인
        //session.invalidate(); // 세션 무효화
        //request.getSession().removeAttribute("id");
        //HttpSession session = request.getSession(false); // 존재하는 세션이 없을 경우 null 반환
        if (session != null) {
            //session.invalidate(); // 세션 무효화
            System.out.println("세선무효화");
            request.getSession().removeAttribute("id");
        }else{
            System.out.println("무효화 안됨");
        }
        return "redirect:home.do";
    }
    @GetMapping("join.do") //회원가입 창
    public String join(Model model){
        model.addAttribute("member", new Member());
        return "/project/join";
    }

    @PostMapping("join.do") //회원가입
    public String join(@ModelAttribute("member") Member member, HttpSession session) {
        System.out.println("member" + member);
        service.join(member);
        System.out.println("member" + member);
        return "redirect:home.do";
    }
    @GetMapping("withdraw.do") //회원탈퇴
    public String withdraw(@RequestParam("id") String id) {
        System.out.println("id"+id);
        service.withdraw(id);
        System.out.println("id"+id);
        return "redirect:logout.do"; // 로그아웃 페이지로 리다이렉트 또는 다른 페이지로 이동할 수 있습니다.
    }

    @GetMapping("analysis_page.do") //분석하기
    public String analysis_page(){
        return "/project/analysis_page";
    }
    @GetMapping("history.do") //히스토리
    public String history(){
        return "/project/history";
    }
    @GetMapping("list1.do") //기업1
    public String list_1(Model model){
        return  "/project/list1";
    }
    @PostMapping("list1.do") //기업1
    public String list_1(@RequestParam("name") String name){
        return  "/project/list1";
    }
    @GetMapping("list2.do") //기업2
    public String list_2(){
        return  "/project/list2";
    }
    @GetMapping("chart.do") //결과값
    public String chart(){
        return  "/project/chart";
    }

    @GetMapping("add.do") //기업추가
    public String add(Model model, HttpSession session){
        String id = (String)session.getAttribute("id");
        //List<AvailableData> filteredData = availableDataService.getAvailableDataByFilters(null, null, null, null,null);
        //model.addAttribute("filteredData", filteredData);
        List<String> nation = availableDataService.getNation(id);
        /*List<String> db_name = availableDataService.getDb(nation);
        List<String> stock_code = availableDataService.getStockCode(db_name);
        List<String> sector = availableDataService.getSector(stock_code);
        List<String> name = availableDataService.getName(sector);*/
        //List<String> stock_code = availableDataService.getStockCode(name);
//        List<String> db_name = new ArrayList<>();
//        List<String> sector = new ArrayList<>();
//        List<String> name = new ArrayList<>();
//        List<String> stock_code = new ArrayList<>();
//        for (String nationName : nation)     {
//            db_name.addAll(availableDataService.getDb(id, nationName));
//        }
        model.addAttribute("nation", nation);
        System.out.println("Nation: " + nation);

//        for (String dbName : db_name) {
//            sector.addAll(availableDataService.getSector(id, dbName));
//        }
//        for (String sectorName : sector) {
//            name.addAll(availableDataService.getName(id, sectorName));
//        }
//        for (String code : name) {
//            stock_code.addAll(availableDataService.getStockCode(id, code));
//        }
//
//        model.addAttribute("db_name", db_name);
//        model.addAttribute("sector", sector);
//        model.addAttribute("name", name);
//        model.addAttribute("stock_code", stock_code);

        return  "/project/add2";
}
@PostMapping("add.do")
    public String add(
                       @RequestParam("nation") String nation,
                       @RequestParam("db_name") String db_name,
                       @RequestParam("sector") String sector,
                       @RequestParam("name") String name,
                       @RequestParam("stock_code") String stock_code,
                       Model model) {
        System.out.println("post add");
//        List<String> filteredData = availableDataService.getAvailableDataByFilters(nation, db_name, sector,name,stock_code);
//        System.out.println("filteredData"+filteredData);
//        model.addAttribute("filteredData", filteredData);
        return "redirect:add.do";
    }
@PostMapping("url.do")
@ResponseBody
    public List<String> add2(@RequestParam("action") String action,
                           @RequestParam("selectedNation")String selectedNation,
                           @RequestParam("selectedDb") String selectedDb,
                           @RequestParam("selectedSection") String selectedSector,
                           @RequestParam("selectedName") String selectedName,
                           HttpSession session) {

        String id = (String)session.getAttribute("id");
        System.out.println("action: "+action + "\nselectedNation: "+selectedNation+ "\nid: "+id);
        // 요청에 따라 데이터를 로드하고 응답할 리스트를 생성합니다.
        List<String> responseData = null;

        // 요청에 따라 처리합니다.
        switch (action) {
            case "getNation":
                // 데이터베이스에서 나라를 가져옵니다.
                responseData = availableDataService.getNation(id);
                System.out.println(responseData);
                break;
            case "getDb":
                // 데이터베이스에서 db명을 가져옵니다.
                responseData = availableDataService.getDb(id,selectedNation);
                System.out.println(responseData);
                break;
            case "getSector":
                // 데이터베이스에서 업종명 가져옵니다.
                responseData = availableDataService.getSector(id,selectedDb);
                System.out.println(responseData);
                break;
            case "getName":
                // 데이터베이스에서 회사명을 가져옵니다.
                responseData = availableDataService.getName(id,selectedSector);
                System.out.println(responseData);
                break;
            case "getStockCode":
                responseData = availableDataService.getStockCode(id,selectedName);
                System.out.println(responseData);
            default:
                // 알 수 없는 액션인 경우, null 또는 적절한 오류 응답을 반환합니다.
                break;
        }
        return responseData;
    }

    @PostMapping("add2.do")
    public String excuteAdd(@RequestParam("db_name") String db_name, @RequestParam("stock_code") String stock_code, HttpSession session){
        String id = (String)session.getAttribute("id");
        if(addData.getArchivedDataStockCode(stock_code).isEmpty()) {
            //stock_code가 addition에 있는지 확인(id 무관)
            //mapper 에서 addition에서 where stock_code=#stock_code인 조건으로 쿼리.
            //쿼리 결과가 list length가 0 인 경우 체크
            javaPy.strParameter("add_data",db_name,stock_code);
        }
        addData.insertToAddition(id, stock_code);
        //없으면, python 호출, -> addition에 추가
            //mapper에서 insert 생성 추가.
        //있건 없건, addition에 추가
    return "redirect:analysis_page.do";
    }
}