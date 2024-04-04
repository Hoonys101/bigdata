package team.backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.backend.domain.Board;

import java.util.List;

@SpringBootTest
class JdbcOracleBoardRepositoryTest {
    @Autowired
    JdbcOracleBoardRepository repository;

    @Test
    void getConnection() {
        System.out.println("#repository: " + repository);
        System.out.println("#con : " +repository.getConnection());
    }

    @Test
    void list() {
        List<Board> list = repository.list();
        System.out.println("list"+ list);
    }

    @Test
    void insert() {
    }

    @Test
    void content() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}