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
        mapper.insertMember(member);
    }
    public void withdraw(String id){
        mapper.deleteUser(id);
    }

}

