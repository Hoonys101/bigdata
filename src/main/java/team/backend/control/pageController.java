package team.backend.control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.backend.domain.*;

import team.backend.service.*;

import java.io.UnsupportedEncodingException;
import java.net.Authenticator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
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
        if (service.login(id, pwd, redirectAttributes)) {
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
        String id = (String) session.getAttribute("id");
        List<String> nation = availableDataService.getNation(id);
        model.addAttribute("nation", nation);
        System.out.println("Nation: " + nation);
        return "/project/add2";
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
        return "redirect:add2.do";
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


    @PostMapping("add2.do")
    public String excuteAdd(@RequestParam("db_name") String db_name, @RequestParam("stock_code") String stock_code, HttpSession session) {
        String id = (String) session.getAttribute("id");
        db_name = db_name.split("\\s\\(")[0];
        if (addData.getArchivedDataStockCode(stock_code).isEmpty()) {
            //stock_code가 addition에 있는지 확인(id 무관)
            //mapper 에서 addition에서 where stock_code=#stock_code인 조건으로 쿼리.
            //쿼리 결과가 list length가 0 인 경우 체크
            System.out.println("python add_data 호출, db_name: " + db_name + ", stock_code: " + stock_code);

            javaPy.strParameter("add_data", db_name, stock_code);
        }
        addData.insertToAddition(id, stock_code);
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


    @PostMapping("analysis_page.do") //분석하기
    public String analysis_page(@RequestParam("stock_code1") String stock_code1,
                                @RequestParam("stock_code2") String stock_code2,
                                @RequestParam("start_date") String start_date,
                                @RequestParam("end_date") String end_date,
                                HttpSession session, Model model) throws UnsupportedEncodingException {
        System.out.println("analysis start");
        String id = (String) session.getAttribute("id");
        System.out.println("id" + id);
        start_date = start_date.replace("-", "").substring(0, 8);
        end_date = end_date.replace("-", "").substring(0, 8);


        serviceUsage.setStock_code1(stock_code1);
        serviceUsage.setStock_code2(stock_code2);
        serviceUsage.setStart_date(start_date);
        serviceUsage.setEnd_date(end_date);
        serviceUsage.setId(id);
        System.out.println(serviceUsage);

        List<String> result = javaPy.strParameter("cal_data", stock_code1, stock_code2, start_date, end_date);

        System.out.println(result.toString());
        int length = result.size();
        System.out.println("length: " + length);
        for (int i = 0; i < 10; i++) {
            result.set(i, result.get(length + i - 10));
            System.out.println(result.get(length + i - 10));
        }
        String report = javaPy.analysisData(result.subList(0, 4));
        serviceUsage.setReport(report);
        System.out.println("control report" + serviceUsage);
        for (int i = 5; i < 10; i++) {
            result.set(i, "img/plots/" + result.get(i));
        }


        addData.insertToServiceUsage(serviceUsage);
        List<String> plotFile = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String plot = "http://127.0.0.1:8080/img/plots/" + stock_code1 + "_" + stock_code2 + "_" + start_date + "_" + end_date + "_" + i + ".png";
            plotFile.add(i, plot);
        }

        List<String[]> dataList = new ArrayList<>();
        dataList.add(new String[]{"0", result.get(0)});
        dataList.add(new String[]{"1", result.get(1)});
        dataList.add(new String[]{"2", result.get(2)});
        dataList.add(new String[]{"3", result.get(3)});
        dataList.add(new String[]{"4", result.get(4)});

        model.addAttribute("dataList", dataList);
        model.addAttribute("report", report);
        model.addAttribute("plots", plotFile);
        //model.addAttribute("reportUrl", reportUrl);
        return "/project/chart";
    }

    @GetMapping("history.do") //히스토리
    public String history(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        List<ServiceUsage> serviceUsages = addData.getHistory(id);
        model.addAttribute("serviceUsages", serviceUsages);
        System.out.println("serviceUsages" + serviceUsages);
        return "/project/history";
    }

    @GetMapping("historyDel.do")
    public String deleteHistory(@RequestParam("serviceusage_seq") int serviceusage_seq) {
        addData.deleteHistoryBySeq(serviceusage_seq);
        return "redirect:history.do"; // 삭제 후 다시 히스토리 페이지로 리다이렉트
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
            message.setSubject("빅데이터 분석 플랫폼 아이디 찾기");
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
}
