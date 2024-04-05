package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.Address;

import java.util.List;

@Mapper
@Repository
public interface AddressMapper { //나중에 추상 메소드 추가
    List<Address> list();

    Address selectBySeq(long seq);
    void insertSelectKey(Address address);
    void delete(long seq);
}
