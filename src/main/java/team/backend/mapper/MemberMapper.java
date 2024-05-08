package team.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.Member;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
    public boolean login(String id, String pwd);
    Member findById(String id);

    void insertMember(Member member);

    void deleteUser(String id);

    void deleteUserByAddition(String id);

    void deleteUserByServiceUsage(String id);
    void deleteUserByBranchHistory(String id);
    void deleteUserByExclusionperiodHistory(String id);
    void deleteUserByExcludedquarterHistory(String id);



    Member findUserByUsernameDobAndEmail(Member member);
    Member findUserByPwd(Member member);
}
