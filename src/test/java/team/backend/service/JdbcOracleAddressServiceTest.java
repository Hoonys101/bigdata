package team.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.backend.domain.Address;

import java.util.List;

@SpringBootTest
class JdbcOracleAddressServiceTest {
    @Autowired
    AddressService service;
    @Test
    void listS() {
        List<Address> list = service.listS();
        System.out.println("@list : " +list);
    }

    @Test
    void insertS() {
        Address address = new Address(-1L, "심광섭","서울",null);
        Address addressDb = service.insertS(address);
        System.out.println("@addressDb"+addressDb);
    }

    @Test
    void deleteS() {
        long seq = 6;
        boolean flag = service.deleteS(seq);
        System.out.println("@flag : "+flag);
    }
}