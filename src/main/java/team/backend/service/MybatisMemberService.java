package team.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.backend.domain.Member;
import team.backend.mapper.MemberMapper;

import java.util.List;

@Service
public class MybatisMemberService implements MemberService {
    @Autowired
    private final MemberMapper mapper;
    @Autowired
    public MybatisMemberService(MemberMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public boolean login(String id, String pwd, RedirectAttributes redirectAttributes) {
        Member member = mapper.findById(id);
        if (member != null && member.getPwd().equals(pwd)) {
            return true;
        } else {
            redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return false;
        }
    }
    public boolean join(Member member, RedirectAttributes redirectAttributes) {
        Member existingMember = mapper.findById(member.getId());
        if (existingMember != null) {
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 아이디입니다.");
            return false;
        }

        // 중복이 없으면 회원가입 처리
        mapper.insertMember(member);
        return true;
    }
    public void withdraw(String id){
        mapper.deleteUser(id);
    }



}

