package team.backend.control;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.backend.dto.ChartDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequestMapping("chart")
@Controller
public class ChartController {
    @GetMapping("chart.do")
    public String showView(ServletContext application, HttpServletRequest session,
                           HttpServletRequest request, Object page){
        System.out.println("application 1번: " + application);
        application = session.getServletContext();
        System.out.println("application 2번: " + application);
        System.out.println("session : " + session);
        System.out.println("request : " + request);
        System.out.println("page : " + page);
        return "chart/chart";
    }
    private Random r = new Random();

    @PostMapping("chartData.do")
    public @ResponseBody List<ChartDTO> getChartData(){
        List<ChartDTO> list = new ArrayList<ChartDTO>();
        String items[] = {"봄", "여름", "가을", "겨울"};
        for(int i=0; i<items.length; i++){
            int number = r.nextInt(100);
            ChartDTO dto = new ChartDTO(items[i], number);
            list.add(dto);
        }
        return list;
    }
}
