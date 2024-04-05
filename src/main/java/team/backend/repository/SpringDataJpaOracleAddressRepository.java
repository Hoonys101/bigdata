package team.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.backend.domain.Address;

import java.util.List;

public interface SpringDataJpaOracleAddressRepository extends JpaRepository<Address, Long> {
    //extends JpaRepository<Address, Long> 에서 Long은 고유한 id값인 pk를가진 seq의 타입
  List<Address> findByName(String name);
  // JPQL -> select a from Address a where a.name=:name
  List<Address> findByNameAndAddr(String name, String addr);

  List<Address> findByNameContaining(String name);//xxxContaining()은 like연산자의 역할
}
