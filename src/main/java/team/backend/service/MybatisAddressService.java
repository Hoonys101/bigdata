package team.backend.service;

import team.backend.domain.Address;
import team.backend.mapper.AddressMapper;

import java.util.List;
//@Service
public class MybatisAddressService implements AddressService {
    //@Autowired
    private final AddressMapper mapper;

    //@Autowired
    public MybatisAddressService(AddressMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public List<Address> listS() {
        //List<Address> list = mapper.list();
        System.out.println("listS mapper" );
        return mapper.list();
    }
    @Override
    public Address insertS(Address address) {
        System.out.println("insertS mapper");
        mapper.insertSelectKey(address);
        long seq = address.getSeq(); //중요
        System.out.println("insertS address.getSeq() :" + seq);
        Address addressDb = mapper.selectBySeq(seq);
        System.out.println("insertS addressDb :" + addressDb);

        return addressDb;
    }
    @Override
    public boolean deleteS(Long seq) {
        System.out.println("deleteS mapper");
        mapper.delete(seq);
        return true;
    }
}
