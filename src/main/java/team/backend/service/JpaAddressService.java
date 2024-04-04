package team.backend.service;

import jakarta.transaction.Transactional;
import team.backend.domain.Address;
import team.backend.repository.AddressRepository;

import java.util.List;

//@Service
@Transactional //JPA는 트랜젝션 안에서 사용해야함 JPA Test 파일은 Commit 활성화 후 테스트가능
public class JpaAddressService implements AddressService {
    //@Autowired
    private final AddressRepository repository;
    //@Autowired
    public JpaAddressService(AddressRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Address> listS() {
        System.out.println("@service listS() jpa");
        return repository.list();
    }

    @Override
    public Address insertS(Address address) {
        System.out.println("@insertS by jpa :(Before) address " + address);
        address = repository.insert(address);
        System.out.println("@insertS by jpa : (after) address" + address);
        System.out.println("@insertS by jpa : seq " + address.getSeq());
        System.out.println("@insertS by jpa : rdate " + address.getRdate());
        return address;
    }

    @Override
    public boolean deleteS(Long seq) {
        System.out.println("@deleteS by jpa " );
        return repository.delete(seq);
    }
}
