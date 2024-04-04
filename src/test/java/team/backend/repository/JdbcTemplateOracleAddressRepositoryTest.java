package team.backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.backend.domain.Address;

import java.util.List;

@SpringBootTest
class JdbcTemplateOracleAddressRepositoryTest {
    @Autowired
    JdbcTemplateOracleAddressRepository repository;

    @Test
    void showRepository(){
        System.out.println("repository : " + repository);

    }

    @Test
    void list() {
        List<Address> list = repository.list();
        System.out.println("list : " +list);

    }

    @Test
    void insert() {
        Address address = new Address(-1L, "심광섭", "서울",null);
        Address address2 = repository.insert(address);
        System.out.println("address2" + address2);
    }

    @Test
    void delete() {
        boolean flag = repository.delete(8);
        System.out.println("flag" + flag);

    }
}