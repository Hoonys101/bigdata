package team.backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.backend.domain.Address;

import java.util.List;

@SpringBootTest
class JdbcOracleAddressRepositoryTest {
    @Autowired
    JdbcOracleAddressRepository repository;

    @Test
    void getConnection() {
        System.out.println("#repository: " + repository);
        System.out.println("#con : " +repository.getConnection());

    }

    @Test
    void list() {
        List<Address> list = repository.list();
        System.out.println("list"+ list);
    }

    @Test
    void insert() {
        Address address = new Address(-1L, "예준", "서울", null);
        Address addressDb = repository.insert(address);
        System.out.println("addressDb"+addressDb);
    }

    @Test
    void delete() {
        boolean flag = repository.delete(5);
        System.out.println("#flag: " + flag);

    }
}