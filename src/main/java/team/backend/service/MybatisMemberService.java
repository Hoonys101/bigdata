package team.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.Member;
import team.backend.mapper.MemberMapper;

@Service
public class MybatisMemberService implements MemberService {
    @Autowired
    private final MemberMapper mapper;
    @Autowired
    public MybatisMemberService(MemberMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public boolean login(String id, String pwd) {
        Member member = mapper.findById(id);
        return member != null && member.getPwd().equals(pwd);
    }
    public void join(Member member) {
        //member = new Member();
        //member.setId(member.getId());
       // member.setPwd(member.getPwd());
        //member.setName(member.getName());
        //member.setEmail(member.getEmail());
        ///member.setBirth_date(member.getBirth_date());
        //member.setGender(member.getGender());
        //member.setSignup_date(member.getSignup_date());
        mapper.insertMember(member);
    }

}

