package team.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.Member;

@Mapper
@Repository
public interface MemberMapper {
    public boolean login(String id, String pwd);
    Member findById(String id);
    void insertMember(Member member);
}
