package team.backend.repository;

import jakarta.persistence.EntityManager;
import team.backend.domain.Address;

import java.util.List;

//@Repository
public class JpaOracleAddressRepository implements AddressRepository {
    //@Autowired
    private  final EntityManager em;
   // @Autowired
    public JpaOracleAddressRepository(EntityManager em){
        this.em = em;
    }
    @Override
    public List<Address> list() {
        List<Address> list = em.createQuery("select a from Address a", Address.class)
                .getResultList();
        return list;
    }
    public Address findBySeq(long seq){ //시퀀스로 찾는법
        Address address = em.find(Address.class, seq);
        return address;
    }
    public List<Address> findByName(String name){//이름으로 찾는것
        List<Address> list = em.createQuery("select a from Address a where a.name=:name", Address.class)
                .setParameter("name", name)
                .getResultList();
        return list;
    }

    @Override
    public Address insert(Address address) { //insert, update ,delete 기능은 jpql이 필요없음 메소드가 다지원
        em.persist(address);
        return address;
    }
    @Override
    public boolean delete(long seq) { //interface에 void delete(long seq) 또는 Address delete(long seq)이 좋음
        Address address = findBySeq(seq);
        em.remove(address);
        return true;
    }
}
