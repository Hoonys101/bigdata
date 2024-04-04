package team.backend.service;

import team.backend.domain.Address;

import java.util.List;

//인터페이스 에서는 어노테이션을 안쓴다.
public interface AddressService {

    List<Address> listS();
    Address insertS(Address address);
    boolean deleteS(Long seq);

}
