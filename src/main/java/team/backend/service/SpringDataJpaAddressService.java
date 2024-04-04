package team.backend.service;

import org.springframework.data.domain.Sort;
import team.backend.domain.Address;
import team.backend.repository.SpringDataJpaOracleAddressRepository;

import java.util.List;

//@Service
public class SpringDataJpaAddressService implements AddressService {
    //@Autowired
    private final SpringDataJpaOracleAddressRepository repository;
   //@Autowired
    public SpringDataJpaAddressService(SpringDataJpaOracleAddressRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Address> listS() {
        System.out.println("@listS() by SpringDataJpa");
        //return repository.findAll(); 정렬방법은 모르겠다. 아마 이름?
        return repository.findAll(Sort.by(Sort.Direction.DESC, "seq"));
        //return repository.findByName("ㅇ");
        //return repository.findByNameAndAddr("ㅇ","ㅇ");
        //return repository.findByNameContaining("홍"); // -> like 연산자
    }

    @Override
    public Address insertS(Address address) {
        System.out.println("@insertS() by SpringDataJpa");
        address = repository.save(address);
        System.out.println("@insertS() by SpringDataJpa address : " + address);
        return address;
    }

    @Override
    public boolean deleteS(Long seq) {
        System.out.println("@deleteS() by SpringDataJpa");
        repository.deleteById(seq);
        return true;
    }
    public List<Address> findByName(String name){
        List<Address> list = repository.findByName(name);
        System.out.println("@findByName() by SpringDataJpa list : " + list);
        return list;
    }
    public List<Address> findByNameAndAddr(String name, String addr){
        List<Address> list = repository.findByNameAndAddr(name, addr);
        System.out.println("@findByNameAndAddr() by SpringDataJpa list : " + list);
        return list;
    }
    public List<Address> findByNameContaining(String name){
        List<Address> list = repository.findByNameContaining(name);
        System.out.println("@findByContaining() by SpringDataJpa list : " + list);
        return list;
    }
}
