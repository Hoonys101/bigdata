package team.backend.service;

import org.springframework.data.domain.Sort;
import team.backend.domain.Board;
import team.backend.repository.SpringDataJpaOracleBoardRepository;

import java.util.List;
//@Service
public class SpringDataJpaBoardService implements BoardService {
    //@Autowired
    private final SpringDataJpaOracleBoardRepository repository;
    //@Autowired
    public SpringDataJpaBoardService(SpringDataJpaOracleBoardRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Board> listS() {
        List<Board> list = repository.findAll(Sort.by(Sort.Direction.DESC,"seq"));
        return list;
    }

    @Override
    public Board insertS(Board board) {
        return repository.save(board);
    }

    @Override
    public Board contentS(int seq) {
        return repository.findBySeq(seq);
    }

    @Override
    public Board updateS(int seq) {
        return repository.findBySeq(seq);
    }

    @Override
    public boolean updateSup(Board board) {
        board = repository.save(board);
        System.out.println("@updateSup : " + board);
        return true;
    }

    @Override
    public boolean deleteS(int seq) {
        repository.deleteById(seq);
        return true;
    }
}
