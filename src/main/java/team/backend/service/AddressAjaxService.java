package team.backend.service;

import team.backend.domain.Address;

import java.util.List;

public interface AddressAjaxService  {
    List<Address> listS();
    Address insertS(Address address);
    boolean deleteS(long seq);

    //for Ajax
    Address getBySeqS(long seq);
    List<Address> getListByNames(String name);
}
