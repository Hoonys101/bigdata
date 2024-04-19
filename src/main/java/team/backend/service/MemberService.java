package team.backend.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.backend.domain.Member;

import java.util.List;

public interface MemberService {


    boolean login(String id, String pwd, RedirectAttributes redirectAttributes);

    boolean join(Member member, RedirectAttributes redirectAttributes);


    void withdraw(String id);

    String find_id(Member member);

    String find_pwd(Member member);


}
