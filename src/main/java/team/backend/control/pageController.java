package team.backend.control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.backend.domain.Addition;
import team.backend.domain.AvailableData;
import team.backend.domain.Member;

import team.backend.domain.ServiceUsage;
import team.backend.service.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@RequestMapping("project")
@Controller
//@SessionAttributes("id")
public class pageController {
    @Autowired
    private MemberService service;
    @Autowired
    private AvailableDataService availableDataService;
    @Autowired
    private addDataService addData;
    @Autowired
    private JavaPythonInter javaPy;
    @Autowired
    private ServiceUsage serviceUsage;
    @Autowired
    public pageController(ServiceUsage serviceUsage) {
        this.serviceUsage = serviceUsage;
    }

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


    @GetMapping("history.do") //히스토리
    public String history(){
        return "/project/history";
    }
    @GetMapping("list1.do") //기업1
    public String list_1(Model model){

        AvailableData availableData = new AvailableData();




        
        //addition.setId(/* 여기에 id 값 설정 */);
        //addition.setStockCode(/* 여기에 stock_code 값 설정 */);
        //model.addAttribute("addition", addition);
        //System.out.println(data);
        return  "/project/list1";
    }
    //@PostMapping("list1.do") //기업1
    //public String list_1(@RequestParam("id") String id,
      //                   @RequestParam("stock_code") String stock_code){


        //javaPython.strParameter("add_data",stock_code,id);
        //
        //return  "/project/list1";
   // }
    @GetMapping("list2.do") //기업2
    public String list_2(@RequestParam("stock_code") String stock_code,
                         @RequestParam("db_name") String db_name
                         ){

        javaPy.strParameter("add_data",stock_code,db_name);

        return  "/project/list2";
    }


    @GetMapping("add2.do") //기업추가
    public String add(Model model, HttpSession session){
        //List<AvailableData> filteredData = availableDataService.getAvailableDataByFilters(null, null, null, null,null);
        //model.addAttribute("filteredData", filteredData);
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
        String id = (String)session.getAttribute("id");
        List<String> nation = availableDataService.getNation(id);
        model.addAttribute("nation", nation);
        System.out.println("Nation: " + nation);
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
        selectedNation=selectedNation.split("\\s\\(")[0];
        selectedDb=selectedDb.split("\\s\\(")[0];
        selectedSector=selectedSector.split("\\s\\(")[0];
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
        db_name=db_name.split("\\s\\(")[0];
        if(addData.getArchivedDataStockCode(stock_code).isEmpty()) {
            //stock_code가 addition에 있는지 확인(id 무관)
            //mapper 에서 addition에서 where stock_code=#stock_code인 조건으로 쿼리.
            //쿼리 결과가 list length가 0 인 경우 체크
            System.out.println("python add_data 호출, db_name: "+db_name+", stock_code: "+stock_code);

            javaPy.strParameter("add_data",db_name,stock_code);
        }
        addData.insertToAddition(id, stock_code);
        //없으면, python 호출, -> addition에 추가
            //mapper에서 insert 생성 추가.
        //있건 없건, addition에 추가
    return "redirect:home.do";
    }
    @GetMapping("analysis_page.do") //분석하기
    public String analysis_page(Model model ,HttpSession session,AvailableData availableData){
        String id = (String)session.getAttribute("id");
        List<AvailableData> list = availableDataService.getList(id,availableData);
        model.addAttribute("list" ,list);
        System.out.println("list"+list);
        return "/project/analysis_page";
    }
    @PostMapping("analysis_page.do") //분석하기
    public String analysis_page(@RequestParam("stock_code1") String stock_code1,
                                @RequestParam("stock_code2") String stock_code2,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("end_date") String end_date,
                                HttpSession session,Model model){
        System.out.println("analysis start");
        String id = (String)session.getAttribute("id");
        System.out.println("id"+id);
        start_date = start_date.replace("-", "").substring(0, 8);
        end_date = end_date.replace("-", "").substring(0, 8);
        serviceUsage.setStock_code1(stock_code1);
        serviceUsage.setStock_code2(stock_code2);
        serviceUsage.setStart_date(start_date);
        serviceUsage.setEnd_date(end_date);
        serviceUsage.setId(id);
        System.out.println(serviceUsage);

        List<String> result=javaPy.strParameter("cal_data",stock_code1,stock_code2,start_date,end_date);

        System.out.println(result.toString());
        int length=result.size();
        System.out.println("length: "+length);
        for(int i=0;i<10;i++){
            result.set(i,result.get(length+i-10));
            System.out.println(result.get(length+i-10));
        }
        String report=javaPy.analysisData(result.subList(0,4));
        for(int i=5;i<10;i++){
            result.set(i,"img/plots/"+result.get(i));
        }
        addData.insertToServiceUsage(serviceUsage);
        List<String> plotFile = new ArrayList<>();
        for(int i=0;i<5;i++) {
            String plot = "http://127.0.0.1:8080/img/plots/"+stock_code1 + "_" + stock_code2 + "_" + start_date + "_" + end_date + "_" + i + ".png";
            plotFile.add(i,plot);
        }
        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[]{"0",result.get(0)});
        dataList.add(new String[]{"1",result.get(1)});
        dataList.add(new String[]{"2",result.get(2)});
        dataList.add(new String[]{"3",result.get(3)});
        dataList.add(new String[]{"4",result.get(4)});

        model.addAttribute("dataList",dataList);
        model.addAttribute("report",report);
        model.addAttribute("plots",plotFile);
        return  "/project/chart";
    }



//    @GetMapping("chart.do") //결과값
//    public String chart(@RequestParam("stock_code1") String stock_code1,
//                        @RequestParam("stock_code2") String stock_code2,
//                        @RequestParam("start_date") String start_date,
//                        @RequestParam("end_date") String end_date, Model model){
//        model.addAttribute("stock_code1", stock_code1);
//        model.addAttribute("stock_code2", stock_code2);
//        model.addAttribute("start_date", start_date);
//        model.addAttribute("end_date", end_date);
//        //model.addAttribute("imagePath", imagePath);
//        //  result.size() 12개 2~6 결과값 7~11 그림
//        // List<String> subList = result.subList(7, 12);
//        //<div>
//        //    <h2>Plot</h2>
//        //    <img src="img/plots/${stock_code1}_${stock_code2}_${start_date}_${end_date}.png" alt="Plot">
//        //</div>
//        return  "/project/chart";
//    }
//        // ArchivedData  ['add_data', 'KONEX', '317240'] db name , 스톡 코드 기업추가
//
//        // 검색 국가 DB 회사명 종목 dto (nation, db_name, sector, name)
//
//        // 파라미터 id, stockcode
//        // addition id 값조회
//        // ArchivedData  stockcode 조회
//        // ?? 조인결과 조회 후 리턴
//
//        // 추가 테이블의 id가 갖고있는 스톡코드로 나라 db 종목 회사를 쿼리한다
//
//        // 쿼리문 SELECT nation, db_name, sector, name
//        //FROM AvailableData
//        //INNER JOIN addition ON AvailableData.stock_code = addition.stock_code
//        //WHERE addition.id = '1' AND AvailableData.stock_code = '317240'
//        //GROUP BY nation, db_name, name, sector;
//        //기업선택 1 , 기업선택 2 , 시작날짜 , 종료날짜
//
//        //javaPy.strParameter("cal_dat",first_stock_code,second_stock_code,startdate,lastdate);
//        //
//
//


}
