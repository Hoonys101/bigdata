 package team.backend.control;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.backend.domain.*;

import team.backend.service.*;

import java.io.UnsupportedEncodingException;

import java.util.*;

import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private ExclusionperiodHistory excludedquarterHistory;//4
    @Autowired
    private BranchHistory branchHistory;//2

    @Autowired
    private ExclusionperiodHistory exclusionperiodHistory;//3


    public static final String PLOTS_DIRECTORY = "C:/plots";
    public static final String PLOTS_DIRECTORY_STATIC = "C:/plots";

    @Autowired
    public pageController(ServiceUsage serviceUsage, ExclusionperiodHistory exclusionperiodHistory, BranchHistory branchHistory) {
        this.serviceUsage = serviceUsage;
        this.exclusionperiodHistory = exclusionperiodHistory;
        this.branchHistory = branchHistory;
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
        if (service.login(id, pwd, redirectAttributes)) {
            session.setAttribute("id", id);
            return "redirect:home.do"; // 로그인 성공 시 메인 페이지로 이동
        } else {
            redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "redirect:home.do"; // 로그인 실패 시 다시 로그인 페이지로 이동
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
        } else {
            System.out.println("무효화 안됨");
        }
        return "redirect:home.do";
    }

    @GetMapping("join.do") //회원가입 창
    public String join(Model model) {
        model.addAttribute("member", new Member());
        return "/project/join";
    }

    @PostMapping("join.do") //회원가입
    public String join(@ModelAttribute("member") Member member, HttpSession session, RedirectAttributes redirectAttributes) {
        if (service.join(member, redirectAttributes)) {
            return "redirect:home.do";
        } else {
            return "redirect:join.do";
        }
    }

    @GetMapping("withdraw.do") //회원탈퇴
    public String withdraw(@RequestParam("id") String id) {
        System.out.println("id" + id);
        service.withdraw(id);
        System.out.println("id" + id);
        return "redirect:logout.do"; // 로그아웃 페이지로 리다이렉트 또는 다른 페이지로 이동할 수 있습니다.
    }

    @GetMapping("add2.do") //기업추가
    public String add(Model model, HttpSession session) {

        String id = (String) session.getAttribute("id");
        List<String> nation = availableDataService.getNation(id);
        model.addAttribute("nation", nation);
        System.out.println("Nation: " + nation);
        return "/project/add2";
    }

    @PostMapping("add2.do")
    public String excuteAdd(@RequestParam("db_name") String db_name, @RequestParam("stock_code") String stock_code, HttpSession session) {
        String id = (String) session.getAttribute("id");
        db_name = db_name.split("\\s\\(")[0];
        addData.insertToAddition(id, stock_code);
        if (addData.getArchivedDataStockCode(stock_code).isEmpty()) {
            //stock_code가 archiveddata에 있는지 확인(id 무관)
            //mapper 에서 archiveddata에서 where stock_code=#stock_code인 조건으로 쿼리.
            //쿼리 결과가 list length가 0 인 경우 체크
            System.out.println("python add_data 호출, db_name: " + db_name + ", stock_code: " + stock_code);

            javaPy.strParameter("add_data", db_name, stock_code);
        }
        //없으면, python 호출, -> addition에 추가
        //mapper에서 insert 생성 추가.
        //있건 없건, addition에 추가
        return "redirect:home.do";
    }

    @GetMapping("analysis_page.do") //분석하기
    public String analysis_page(Model model, HttpSession session, AvailableData availableData) {
        String id = (String) session.getAttribute("id");
        List<AvailableData> list = availableDataService.getList(id, availableData);
        model.addAttribute("list", list);
        System.out.println("list" + list);
        return "/project/analysis_page";
    }
    @GetMapping("analysis_page2.do") //분석하기
    public String analysis_page2(Model model, HttpSession session, AvailableData availableData) {
        String id = (String) session.getAttribute("id");
        List<AvailableData> list = availableDataService.getList(id,availableData);
        model.addAttribute("list", list);

        return "/project/analysis_page2";
    }
    @GetMapping("analysis_page3.do") //분석하기
    public String analysis_page3(Model model, HttpSession session, AvailableData availableData) {
        String id = (String) session.getAttribute("id");
        List<AvailableData> list = availableDataService.getList(id,availableData);
        model.addAttribute("list", list);

        return "/project/analysis_page3";
    }
    @GetMapping("analysis_page4.do") //분석하기
    public String analysis_page4(Model model, HttpSession session, AvailableData availableData) {
        String id = (String) session.getAttribute("id");
        List<AvailableData> list = availableDataService.getList(id,availableData);
        model.addAttribute("list", list);

        return "/project/analysis_page4";
    }


    @PostMapping("analysis_page.do") //기간분석
    public String analysis_page(@RequestParam("stock_code1") String stock_code1,
                                @RequestParam("stock_code2") String stock_code2,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("end_date") String end_date,
                                HttpSession session, Model model,
                                ServletRequest request,
                                ServletResponse response
    ) throws UnsupportedEncodingException {
//        FileDeletionUtil.deleteFiles(PLOTS_DIRECTORY);
        System.out.println("analysis start");
        String id = (String) session.getAttribute("id");
        System.out.println("id" + id);
        start_date = start_date.replace("-", "").substring(0, 8);
        end_date = end_date.replace("-", "").substring(0, 8);

        String name1 = availableDataService.getCompany(stock_code1);
        String name2 = availableDataService.getCompany(stock_code2);
        serviceUsage.setStock_code1(stock_code1);
        serviceUsage.setStock_code2(stock_code2);
        serviceUsage.setName1(name1);
        serviceUsage.setName2(name2);

        serviceUsage.setStart_date(start_date);
        serviceUsage.setEnd_date(end_date);
        serviceUsage.setId(id);
        System.out.println("서비스"+serviceUsage);

        List<String> result = javaPy.strParameter("cal_data", stock_code1, stock_code2, start_date, end_date);

        List<String> result2 = javaPy.strParameter("cal_data", stock_code2, stock_code1, start_date, end_date);
        System.out.println("result"+result);
        System.out.println("result2"+result2);
        int length = result.size();

        System.out.println("length: " + length);
        for (int i = 0; i < 10; i++) {
            result.set(i, result.get(length + i - 10));
            System.out.println("result.get(length): "+result.get(length + i - 10));
        }
        for (int i = 0; i < 10; i++) {
            result2.set(i, result2.get(length + i - 10));
            System.out.println("result2.get(length): "+result2.get(length + i - 10));
        }

        String report = javaPy.analysisData(result.subList(0, 5));
        String report2 = javaPy.analysisData(result2.subList(0, 5));
        serviceUsage.setReport(report);
        serviceUsage.setReport(report2);

        for (int i = 5; i < 10; i++) {
            result.set(i, "img/plots/" + result.get(i));
        }
        for (int i = 5; i < 10; i++) {
            result2.set(i, "img/plots/" + result2.get(i));
        }
        String company1 = availableDataService.getCompany(stock_code1);
        String company2 = availableDataService.getCompany(stock_code2);

        System.out.println("company1"+company1);
        System.out.println("company2"+company2);

        addData.insertToServiceUsage(serviceUsage);
        List<String> plotFile = new ArrayList<>();
//        List<String> plotFileUrl = new ArrayList<>();
        List<String> plotFile2 = new ArrayList<>();
//        List<String> plotFile2Url = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String plot = "http://127.0.0.1:8080/img/plots/" + stock_code1 + "_" + stock_code2 + "_" + start_date + "_" + end_date + "_" + i + ".png";
//            String plotUrl = PLOTS_DIRECTORY + "/"+ stock_code1 + "_" + stock_code2 + "_" + start_date + "_" + end_date + "_" + i + ".png";
            plotFile.add(i, plot);
//            plotFileUrl.add(i,plotUrl);
        }
        for (int i = 0; i < 5; i++) {
            String plot2 = "http://127.0.0.1:8080/img/plots/" + stock_code2 + "_" + stock_code1 + "_" + start_date + "_" + end_date + "_" + i + ".png";
//            String plotUrl2 = PLOTS_DIRECTORY + "/"+stock_code2 + "_" + stock_code1 + "_" + start_date + "_" + end_date + "_" + i + ".png";
            plotFile2.add(i, plot2);
//            plotFile2Url.add(i,plotUrl2);
        }

        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[]{"0", result.get(0)});
        dataList.add(new String[]{"1", result.get(1)});
        dataList.add(new String[]{"2", result.get(2)});
        dataList.add(new String[]{"3", result.get(3)});
        dataList.add(new String[]{"4", result.get(4)});

        List<String[]> dataList2 = new ArrayList<>();
        dataList2.add(new String[]{"0", result2.get(0)});
        dataList2.add(new String[]{"1", result2.get(1)});
        dataList2.add(new String[]{"2", result2.get(2)});
       dataList2.add(new String[]{"3", result2.get(3)});
        dataList2.add(new String[]{"4", result2.get(4)});
        model.addAttribute("company1", company1);
        model.addAttribute("company2", company2);
        model.addAttribute("dataList", dataList);
        model.addAttribute("dataList2", dataList2);
        model.addAttribute("report", report);
        model.addAttribute("report2", report2);
        model.addAttribute("plots", plotFile);
        model.addAttribute("plots2", plotFile2);
        model.addAttribute("serviceUsage", serviceUsage);
//        for (String attributeName : model.asMap().keySet()) {
//            Object attributeValue = model.getAttribute(attributeName);
//            request.setAttribute(attributeName, attributeValue);
//        }
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/project/chart.jsp");
//        try{
//            dispatcher.forward(request, response);
//        }catch(IOException | ServletException ie){
//            System.out.println("오류는 "+ie);
//        }
//        try{
//            Thread.sleep(2000);
//        }catch(InterruptedException ie){}
//        FileDeletionUtil.deleteSelectedFile(plotFileUrl);
//        FileDeletionUtil.deleteSelectedFile(plotFile2Url);

        return "/project/chart";
    }


    @PostMapping("analysis_page2.do") //분기분석
    public String analysis_page2(@RequestParam("stock_code1") String stock_code1,
                                 @RequestParam("stock_code2") String stock_code2,
                                 HttpSession session, Model model) {
        FileDeletionUtil.deleteFiles(PLOTS_DIRECTORY);
        System.out.println("test");
        String id = (String) session.getAttribute("id");
        System.out.println("stock_code1"+stock_code1);
        System.out.println("stock_code2"+stock_code2);
        List<String> result1 = javaPy.strParameter("find_period", stock_code1, stock_code2);
        List<String> result2 = javaPy.strParameter("find_period", stock_code2, stock_code1);
        System.out.println("result1" + result1);
        System.out.println("총 연관된 결과수"+result1.size());
        System.out.println("result2" + result2);
        System.out.println("사이즈"+result1.size());


        String name1 = availableDataService.getCompany(stock_code1);
        String name2 = availableDataService.getCompany(stock_code2);

        List<BranchHistory> branchHistoryList = new ArrayList<>();
        List<BranchHistory> branchHistoryList1 = new ArrayList<>();

        for (String item : result1) {
            String[] resultArray = item.split(",\\s*");
            if (resultArray.length >= 3) {

                BranchHistory branchHistory = new BranchHistory(); // 각 요소에 대해 새로운 ServiceUsage 객체를 생성

                branchHistory.setStock_code1(stock_code1);
                branchHistory.setStock_code2(stock_code2);
                branchHistory.setName1(name1);
                branchHistory.setName2(name2);
                branchHistory.setId(id);
                branchHistory.setStart_date(resultArray[0]);
                branchHistory.setEnd_date(resultArray[1]);
                branchHistory.setReport(resultArray[2]);
                branchHistory.setResultcount(result1.size());
                try {
                    int reportValue = Integer.parseInt(branchHistory.getReport());
                    String result = generateResult(reportValue);
                    branchHistory.setReport(result);
                } catch (NumberFormatException e) {
                    // 숫자 형식이 아닌 경우 처리할 로직을 여기에 추가합니다.
                    // 예를 들어, 기본값을 설정하거나 오류 메시지를 기록할 수 있습니다.
                    branchHistory.setReport("Invalid Report");
                    // 오류 메시지를 출력합니다.
                    System.err.println("NumberFormatException occurred: " + e.getMessage());
                }

                addData.insertToBranchHistory(branchHistory);
                branchHistoryList.add(branchHistory);
            }

        }



        for (String item : result2) {
            String[] resultArray = item.split(",\\s*");
            if (resultArray.length >= 3) {
                BranchHistory branchHistory = new BranchHistory(); // 각 요소에 대해 새로운 ServiceUsage 객체를 생성
                branchHistory.setStock_code1(stock_code2);
                branchHistory.setStock_code2(stock_code1);
                branchHistory.setName1(name2);
                branchHistory.setName2(name1);
                branchHistory.setId(id);
                branchHistory.setStart_date(resultArray[0]);
                branchHistory.setEnd_date(resultArray[1]);
                branchHistory.setReport(resultArray[2]);
                branchHistory.setResultcount(result2.size());


                try {
                    int reportValue = Integer.parseInt(branchHistory.getReport());
                    String result = generateResult(reportValue);
                    branchHistory.setReport(result);
                } catch (NumberFormatException e) {
                    // 숫자 형식이 아닌 경우 처리할 로직을 여기에 추가합니다.
                    // 예를 들어, 기본값을 설정하거나 오류 메시지를 기록할 수 있습니다.
                    branchHistory.setReport("Invalid Report");
                    // 오류 메시지를 출력합니다.
                    System.err.println("NumberFormatException occurred: " + e.getMessage());
                }

                addData.insertToBranchHistory(branchHistory);
                branchHistoryList1.add(branchHistory);
            }
        }



        String company1 = addData.getCompany1(stock_code1);
        String company2 = addData.getCompany2(stock_code2);

        System.out.println("company1: " + name1);
        System.out.println("company2: " + name2);
        System.out.println("serviceUsages1: " + branchHistoryList);
        System.out.println("serviceUsages2: " + branchHistoryList1);
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);

        model.addAttribute("company1", name1);
        model.addAttribute("company2", name2);
        model.addAttribute("serviceUsages0", branchHistoryList);
        model.addAttribute("serviceUsages2", branchHistoryList1);

        return "/project/branchresult";
    }
    @PostMapping("analysis_page3.do") //기간제외
    public String analysis_page3(@RequestParam("stock_code1") String stock_code1,
                                @RequestParam("stock_code2") String stock_code2,
                                 @RequestParam("stock_code3") String stock_code3,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("end_date") String end_date,
                                HttpSession session, Model model,
                                ServletRequest request,
                                ServletResponse response
    ) throws UnsupportedEncodingException {
        FileDeletionUtil.deleteFiles(PLOTS_DIRECTORY);
        System.out.println("analysis start");
        String id = (String) session.getAttribute("id");
        System.out.println("id" + id);
        start_date = start_date.replace("-", "").substring(0, 8);
        end_date = end_date.replace("-", "").substring(0, 8);
        System.out.println(stock_code1);
        System.out.println(stock_code2);
        System.out.println(stock_code3);
        String name1 = availableDataService.getCompany(stock_code1);
        String name2 = availableDataService.getCompany(stock_code2);
        String name3 = availableDataService.getCompany(stock_code3);
        System.out.println("company1"+name1);
        System.out.println("company2"+name2);
        System.out.println("company3"+name3);


        System.out.println("stock_code3"+stock_code3);
        exclusionperiodHistory.setStock_code1(stock_code1);
        exclusionperiodHistory.setStock_code2(stock_code2);
        exclusionperiodHistory.setStock_code3(stock_code3);

        exclusionperiodHistory.setName1(name1);
        exclusionperiodHistory.setName2(name2);
        exclusionperiodHistory.setName3(name3);
        exclusionperiodHistory.setStart_date(start_date);
        exclusionperiodHistory.setEnd_date(end_date);
        exclusionperiodHistory.setId(id);

        System.out.println("서비스 3"+ serviceUsage);
        System.out.println("stock_code1"+stock_code1);
        System.out.println("stock_code2"+stock_code2);
        System.out.println("stock_code3"+stock_code3);
        System.out.println("start_date"+start_date);
        System.out.println("end_date"+end_date);

        List<String> result = javaPy.strParameter("diff_cal_data", stock_code1,stock_code3, stock_code2,stock_code3, start_date, end_date);
        List<String> result2 = javaPy.strParameter("diff_cal_data", stock_code2,stock_code3, stock_code1,stock_code3, start_date, end_date);
        System.out.println("result" + result);
        System.out.println("result2" + result2);
        System.out.println(result.toString());
        int length = result.size();

        System.out.println("length: " + length);
        for (int i = 0; i < 10; i++) {
            result.set(i, result.get(length + i - 10));
            System.out.println("result.get(length): "+result.get(length + i - 10));
        }
        for (int i = 0; i < 10; i++) {
            result2.set(i, result2.get(length + i - 10));
            System.out.println("result2.get(length): "+result2.get(length + i - 10));
        }
        String report = javaPy.analysisData(result.subList(0, 5));
        String report2 = javaPy.analysisData(result2.subList(0, 5));
        exclusionperiodHistory.setReport(report);
        exclusionperiodHistory.setReport(report2);

        for (int i = 5; i < 10; i++) {
            result.set(i, "img/plots/" + result.get(i));
        }
        for (int i = 5; i < 10; i++) {
            result2.set(i, "img/plots/" + result2.get(i));
        }


        addData.insertToExclusionperiodHistory(exclusionperiodHistory);
        List<String> plotFile = new ArrayList<>();
        List<String> plotFile2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String plot = "http://127.0.0.1:8080/img/plots/" + stock_code1 + "_" + stock_code2 + "_" + start_date + "_" + end_date + "_" + i + ".png";
            plotFile.add(i, plot);
        }
        for (int i = 0; i < 5; i++) {
            String plot2 = "http://127.0.0.1:8080/img/plots/" + stock_code2 + "_" + stock_code1 + "_" + start_date + "_" + end_date + "_" + i + ".png";
            plotFile2.add(i, plot2);
        }

        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[]{"0", result.get(0)});
        dataList.add(new String[]{"1", result.get(1)});
        dataList.add(new String[]{"2", result.get(2)});
        dataList.add(new String[]{"3", result.get(3)});
        dataList.add(new String[]{"4", result.get(4)});

        List<String[]> dataList2 = new ArrayList<>();
        dataList2.add(new String[]{"0", result2.get(0)});
        dataList2.add(new String[]{"1", result2.get(1)});
        dataList2.add(new String[]{"2", result2.get(2)});
        dataList2.add(new String[]{"3", result2.get(3)});
        dataList2.add(new String[]{"4", result2.get(4)});

        String company1 = addData.getCompany1(stock_code1);
        String company2 = addData.getCompany2(stock_code2);

//        model.addAttribute("stock_code3"+stock_code3);
        model.addAttribute("company1", name1);
        model.addAttribute("company2", name2);
        model.addAttribute("dataList", dataList);
        model.addAttribute("dataList2", dataList2);
        model.addAttribute("report", report);
        model.addAttribute("report2", report2);
        model.addAttribute("plots", plotFile);
        model.addAttribute("plots2", plotFile2);

        model.addAttribute("serviceUsage1", exclusionperiodHistory);

        return "/project/chart";
    }




    @PostMapping("analysis_page4.do") //제외분기
    public String analysis_page4(@RequestParam("stock_code1") String stock_code1,
                                 @RequestParam("stock_code2") String stock_code2,
                                 @RequestParam("stock_code3") String stock_code3,
                                 HttpSession session, Model model) {
        List<String> result1 = javaPy.strParameter("diff_find_period",stock_code1,stock_code2,stock_code3);
        List<String> result2 = javaPy.strParameter("diff_find_period",stock_code2,stock_code1,stock_code3);
        System.out.println("result1"+result1);
        System.out.println("result2"+result2);
        String id = (String) session.getAttribute("id");
        String name1 = availableDataService.getCompany(stock_code1);
        String name2 = availableDataService.getCompany(stock_code2);
        String name3 = availableDataService.getCompany(stock_code3);
        System.out.println("name1"+name1);
        System.out.println("name2"+name2);

        List<ExcludedquarterHistory> serviceUsages0 = new ArrayList<>();
        List<ExcludedquarterHistory> serviceUsages2 = new ArrayList<>();

        int resultCount = 0;
        for (String item : result1) {
            String[] resultArray = item.split(",\\s*");
            if (resultArray.length >= 3) {
                ExcludedquarterHistory serviceUsage1 = new ExcludedquarterHistory(); // 각 요소에 대해 새로운 ServiceUsage 객체를 생성
                serviceUsage1.setStock_code1(stock_code1);
                serviceUsage1.setStock_code2(stock_code2);
                serviceUsage1.setStock_code3(stock_code3);
                serviceUsage1.setName1(name1);
                serviceUsage1.setName2(name2);
                serviceUsage1.setName3(name3);
                serviceUsage1.setId(id);
                serviceUsage1.setStart_date(resultArray[0]);
                serviceUsage1.setEnd_date(resultArray[1]);
                serviceUsage1.setReport(resultArray[2]);
                serviceUsage1.setResultcount(result1.size());
                resultCount++;
                try {
                    int reportValue = Integer.parseInt(serviceUsage1.getReport());
                    String result = generateResult(reportValue);
                    serviceUsage1.setReport(result);
                } catch (NumberFormatException e) {
                    // 숫자 형식이 아닌 경우 처리할 로직을 여기에 추가합니다.
                    // 예를 들어, 기본값을 설정하거나 오류 메시지를 기록할 수 있습니다.
                    serviceUsage1.setReport("Invalid Report");
                    // 오류 메시지를 출력합니다.
                    System.err.println("NumberFormatException occurred: " + e.getMessage());
                }

                addData.insertExcludedquarterHistory(serviceUsage1);
                serviceUsages0.add(serviceUsage1);
            }
        }

        for (String item : result2) {
            String[] resultArray = item.split(",\\s*");
            if (resultArray.length >= 3) {
                ExcludedquarterHistory serviceUsage1 = new ExcludedquarterHistory(); // 각 요소에 대해 새로운 ServiceUsage 객체를 생성

                serviceUsage1.setStock_code1(stock_code2);
                serviceUsage1.setStock_code2(stock_code1);
                serviceUsage1.setStock_code3(stock_code3);
                serviceUsage1.setName1(name2);
                serviceUsage1.setName2(name1);
                serviceUsage1.setName3(name3);
                serviceUsage1.setId(id);
                serviceUsage1.setStart_date(resultArray[0]);
                serviceUsage1.setEnd_date(resultArray[1]);
                serviceUsage1.setReport(resultArray[2]);
                serviceUsage1.setResultcount(result2.size());
                resultCount++;
                try {
                    int reportValue = Integer.parseInt(serviceUsage1.getReport());
                    String result = generateResult(reportValue);
                    serviceUsage1.setReport(result);
                } catch (NumberFormatException e) {
                    // 숫자 형식이 아닌 경우 처리할 로직을 여기에 추가합니다.
                    // 예를 들어, 기본값을 설정하거나 오류 메시지를 기록할 수 있습니다.
                    serviceUsage1.setReport("Invalid Report");
                    // 오류 메시지를 출력합니다.
                    System.err.println("NumberFormatException occurred: " + e.getMessage());
                }

                addData.insertExcludedquarterHistory(serviceUsage1);
                serviceUsages2.add(serviceUsage1);
            }
        }

        String company1 = addData.getCompany1(stock_code1);
        String company2 = addData.getCompany2(stock_code2);

        System.out.println("company1: " + name1);
        System.out.println("company2: " + name2);
        System.out.println("serviceUsages0: " + serviceUsages0);
        System.out.println("serviceUsages2: " + serviceUsages2);
        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
        model.addAttribute("stock_code3", stock_code3);
        model.addAttribute("company1", name1);
        model.addAttribute("company2", name2);
        model.addAttribute("serviceUsages0", serviceUsages0);
        model.addAttribute("serviceUsages2", serviceUsages2);





        return "/project/exceptionresult";
    }
    @GetMapping("periodhistory.do") //히스토리
    public String periodhistory(HttpSession session, Model model) {
        //
        String id = (String) session.getAttribute("id");


        List<ExclusionperiodHistory> serviceUsages = addData.getHistoryByPeriod(id); //기간
        List<ExcludedquarterHistory> serviceUsages1 = addData.getHistoryByQuarter(id); //분기


        System.out.println("serviceUsages"+serviceUsages);
        System.out.println("serviceUsages1"+serviceUsages1);
//        for (ExcludedquarterHistory history : serviceUsages) {
//            System.out.println("ExcludedquarterHistory_seq type: " + history.getExcludedquarterHistory_seq());
//        }
//
//        for (ExclusionperiodHistory history : serviceUsages1) {
//            System.out.println("ExclusionperiodHistory_seq type: " + history.getExclusionperiodHistory_seq());
//        }
        model.addAttribute("serviceUsages", serviceUsages);
        model.addAttribute("serviceUsages1", serviceUsages1);


        return "/project/periodhistory";
    }
    @GetMapping("history.do") //히스토리
    public String history(HttpSession session, Model model) {
        System.out.println("startstartstartstart");
        String id = (String) session.getAttribute("id");
        List<ServiceUsage> serviceUsages2 = addData.getHistory(id);//제외 동시에x
        List<BranchHistory> serviceUsages3 = addData.getHistorybranch(id);//제외 동시에

        System.out.println("serviceUsages2"+serviceUsages2);
        System.out.println("serviceUsages3"+serviceUsages3);
        model.addAttribute("serviceUsages2", serviceUsages2);
        model.addAttribute("serviceUsages3", serviceUsages3);


        return "/project/history";
    }
    @GetMapping("historyDel.do")
    public String deleteHistory(@RequestParam("serviceusage_seq") int serviceusage_seq) {

        addData.deleteHistoryBySeq(serviceusage_seq);
        return "redirect:history.do"; // 삭제 후 다시 히스토리 페이지로 리다이렉트
    }
    @GetMapping("exclusionperiodHistoryDel.do")
    public String deleteExclusionperiodHistoryDel(@RequestParam("exclusionperiodHistory_seq") int exclusionperiodHistory_seq) {

        addData.deleteExclusionperiodHistoryBySeq(exclusionperiodHistory_seq);
        return "redirect:history.do"; // 삭제 후 다시 히스토리 페이지로 리다이렉트
    }

    @GetMapping("branchHistoryDel.do")
    public String deleteBranchHistory(@RequestParam("branchHistory_seq") int branchHistory_seq) {

        addData.deleteBranchHistoryBySeq(branchHistory_seq);
        return "redirect:history.do"; // 삭제 후 다시 히스토리 페이지로 리다이렉트
    }
    @PostMapping("resultDel.do")
    public String deleteRusult(@RequestParam("serviceusage_seq") String serviceusage_seq) {
        try {
            int serviceUsageId = Integer.parseInt(serviceusage_seq);
            addData.deleteHistoryBySeq(serviceUsageId);
            System.out.println(addData.deleteHistoryBySeq(serviceUsageId));
        }catch(NumberFormatException e){
            System.out.println(e);
        }
        return "redirect:analysis_page2.do"; // 삭제 후 다시 히스토리 페이지로 리다이렉트
    }
    private String generateResult(int reportValue) {
        String result = "";
        if (reportValue == 0) {
            result = "두 데이터는 동시에 움직입니다.\n";
        } else if (reportValue > 0 && reportValue < 4) {
            result = "두 데이터는 " + reportValue + "주의 간격을 두고 전파되는 관계에 있습니다.\n";
        } else {
            result = "두 데이터는 4주 이상의 간격을 두고 전파되는 관계에 있습니다.\n";
        }
        return result;
    }

    @GetMapping("find_id.do")
    public String find_id() {
        return "/project/find_id";
    }

    @PostMapping("find_id.do")
    public String find_id(Member member, HttpSession session) {
        String username = service.find_id(member);
        if (username != null) {
            // 아이디가 데이터베이스에서 찾아졌을 때,
            // 이메일 전송 로직을 수행합니다.
            boolean emailSent = sendEmail(member.getEmail(), "Your Id is: " + username);
            if (emailSent) {
                // 이메일이 성공적으로 전송되었을 때,
                // 세션에 성공 메시지를 저장합니다.
                session.setAttribute("message", "Email successfully sent.");

            } else {
                // 이메일 전송에 실패했을 때,
                // 세션에 실패 메시지를 저장합니다.
                session.setAttribute("message", "Failed to send email.");

            }
        } else {
            // 아이디가 데이터베이스에서 찾지 못했을 때,
            // 세션에 아이디를 찾을 수 없다는 메시지를 저장합니다.
            session.setAttribute("message", "Username not found.");

        }
        // 리다이렉트합니다.
        return "redirect:find_id.do";
    }
    @GetMapping("find_pwd.do")
    public String find_pwd() {
        return "/project/find_pwd";
    }
    @PostMapping("find_pwd.do")
    public String find_pwd(Member member, HttpSession session) {
        String userpwd = service.find_pwd(member);
        if (userpwd != null) {
            // 아이디가 데이터베이스에서 찾아졌을 때,
            // 이메일 전송 로직을 수행합니다.
            boolean emailSent = sendEmail(member.getEmail(), "Your Pwd is: " + userpwd);

            if (emailSent) {
                // 이메일이 성공적으로 전송되었을 때,
                // 세션에 성공 메시지를 저장합니다.
                session.setAttribute("message", "Email successfully sent.");

            } else {
                // 이메일 전송에 실패했을 때,
                // 세션에 실패 메시지를 저장합니다.
                session.setAttribute("message", "Failed to send email.");

            }
        } else {
            // 아이디가 데이터베이스에서 찾지 못했을 때,
            // 세션에 아이디를 찾을 수 없다는 메시지를 저장합니다.
            session.setAttribute("message", "Username not found.");

        }
        // 리다이렉트합니다.
        return "redirect:find_pwd.do";
    }

    private boolean sendEmail(String recipient, String messageBody) {
        // 발신자 이메일 주소와 비밀번호를 설정합니다.
        final String username = "swsim2@naver.com"; // 발신자 이메일 주소
        final String password = "atesttest1!!"; // 발신자 이메일 비밀번호

        // SMTP 서버 설정을 구성합니다.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.naver.com");
        props.put("mail.smtp.port", "587");

        // 발신자의 인증 정보를 사용하여 세션을 생성합니다.
        javax.mail.Session mailSession = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // 이메일 메시지 객체를 생성합니다.
            MimeMessage message = new MimeMessage(mailSession);
            // 발신자 설정
            message.setFrom(new InternetAddress(username));
            // 수신자 설정
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            // 이메일 제목 설정
            message.setSubject("빅데이터 분석 플랫폼");
            // 이메일 내용 설정
            message.setText(messageBody);

            // 이메일을 전송합니다.
            Transport.send(message);
            return true; // 이메일 전송 성공을 나타내는 true 반환
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // 이메일 전송 실패를 나타내는 false 반환
        }
    }
    @PostMapping("ai_analysis_endpoint.do")
    @ResponseBody
    public ResponseEntity<?> performAIAnalysis(@RequestParam("stock_code1") String stock_code1,
                                               @RequestParam("stock_code2") String stock_code2,
                                               @RequestParam(name = "stock_code3", required = false) String stock_code3,
                                                Model model,
                                               HttpSession session
                                               ) {
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("stock_code1"+stock_code1);
        System.out.println("stock_code2"+stock_code2);
        if (stock_code3!=null) {
            System.out.println("stock_code3" + stock_code3);
        }

        // AI 분석을 수행하고 결과를 얻는 로직을 작성합니다.
        // 예를 들어, AI 분석 서비스를 호출하고 결과를 받아온다고 가정합니다.

        // 아래는 가상의 AI 분석 결과를 생성하는 코드입니다. 실제로는 AI 분석을 수행하는 코드로 대체되어야 합니다.
        List<String> aiResult=null;
        if(stock_code3==null){
            aiResult = javaPy.strParameter("tree_data",stock_code1,stock_code2);
        }else{
            aiResult = javaPy.strParameter("tree_data",stock_code1,stock_code2,stock_code3);
        }
        model.addAttribute("aiResult",aiResult);
//        session.setAttribute("aiResult", aiResult);
//        System.out.println("ai -------------- " +aiResult);
        System.out.println("AI 결과: " + aiResult);




        // 클라이언트에게 JSON 형태로 결과를 반환합니다.
        return ResponseEntity.ok().body(aiResult);
    }
    @PostMapping("aai_analysis_endpoint.do")
    @ResponseBody
    public ResponseEntity<?> performAIAnalysiss(@RequestParam("stock_code1") String stock_code1,
                                               @RequestParam("stock_code2") String stock_code2,
                                                @RequestParam(name = "stock_code3", required = false) String stock_code3,
                                               Model model,
                                               HttpSession session
    ) {
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("performAIAnalysis start");
        System.out.println("stock_code1" + stock_code1);
        System.out.println("stock_code2" + stock_code2);
        System.out.println("stock_code3" + stock_code3);

        // AI 분석을 수행하고 결과를 얻는 로직을 작성합니다.
        // 예를 들어, AI 분석 서비스를 호출하고 결과를 받아온다고 가정합니다.

        // 아래는 가상의 AI 분석 결과를 생성하는 코드입니다. 실제로는 AI 분석을 수행하는 코드로 대체되어야 합니다.
        List<String> aiResult2 = null;
        if (stock_code3==null){
            aiResult2 = javaPy.strParameter("tree_data", stock_code2, stock_code1);
        }else{
            aiResult2 = javaPy.strParameter("tree_data", stock_code2, stock_code1,stock_code3);
        }
        model.addAttribute("aiResult2", aiResult2);
//        session.setAttribute("aiResult", aiResult2);
        System.out.println("ai2-------------- " + aiResult2);

        // 클라이언트에게 JSON 형태로 결과를 반환합니다.
        return ResponseEntity.ok().body(aiResult2);
    }
    @GetMapping("add_by_name_or_code.do")
    @ResponseBody
    public List<AvailableData> addByNameOrCode(@RequestParam("stock_code_or_name") String stock_code_or_name,HttpSession session){
        System.out.println(stock_code_or_name);
        if(stock_code_or_name.length()==0)
            return null;
        String id = (String) session.getAttribute("id");
        List<AvailableData> responseData = availableDataService.getByNameOrStock_code(id, stock_code_or_name);
        return responseData;
    }
    @GetMapping("add2_by_name.do")
    public String add2_by_name(){
        return "/project/add2_by_name";
    }
    @PostMapping("url.do")
    @ResponseBody
    public List<String> add2(@RequestParam("action") String action,
                             @RequestParam("selectedNation") String selectedNation,
                             @RequestParam("selectedDb") String selectedDb,
                             @RequestParam("selectedSection") String selectedSector,
                             @RequestParam("selectedName") String selectedName,

                             HttpSession session) {

        String id = (String) session.getAttribute("id");
        selectedNation = selectedNation.split("\\s\\(")[0];
        selectedDb = selectedDb.split("\\s\\(")[0];
        selectedSector = selectedSector.split("\\s\\(")[0];
        System.out.println("action: " + action + "\nselectedNation: " + selectedNation + "\nid: " + id);
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
                responseData = availableDataService.getDb(id, selectedNation);
                System.out.println(responseData);
                break;
            case "getSector":
                // 데이터베이스에서 업종명 가져옵니다.
                responseData = availableDataService.getSector(id, selectedDb);
                System.out.println(responseData);
                break;
            case "getName":
                // 데이터베이스에서 회사명을 가져옵니다.
                responseData = availableDataService.getName(id, selectedSector);
                System.out.println(responseData);
                break;
            case "getStockCode":
                responseData = availableDataService.getStockCode(id, selectedName);
                System.out.println(responseData);
            default:
                // 알 수 없는 액션인 경우, null 또는 적절한 오류 응답을 반환합니다.
                break;
        }
        return responseData;
    }
}