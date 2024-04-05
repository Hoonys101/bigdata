package team.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.backend.domain.Board;

public interface SpringDataJpaOracleBoardRepository  extends JpaRepository<Board, Integer> {

    Board findBySeq(int seq);//xxxContaining()은 like연산자의 역할

}
