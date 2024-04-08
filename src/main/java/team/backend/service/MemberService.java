package team.backend.service;

import team.backend.domain.Member;

import java.util.List;

public interface MemberService {


    boolean login(String id, String pwd);

    public void join(Member member);


    void withdraw(String id);


}
