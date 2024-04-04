package team.backend.service;

import team.backend.domain.Member;

public interface MemberService {


    boolean login(String id, String pwd);

    public void join(Member member);


}
