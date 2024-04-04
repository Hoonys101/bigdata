package team.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.backend.domain.Board;

import java.util.List;

@SpringBootTest
class JdbcOracleBoardServiceTest {
    @Autowired
    BoardService service;
    @Test
    void listS() {
        List<Board> list = service.listS();
        System.out.println("service list : "+ list );
    }

    @Test
    void insertS() {
    }

    @Test
    void contentS() {
    }

    @Test
    void updateS() {
    }

    @Test
    void deleteS() {
    }
}