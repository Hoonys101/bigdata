package team.backend.service;

import team.backend.domain.Board;
import team.backend.repository.BoardRepository;

import java.util.List;
//@Service
public class JdbcOracleBoardService implements BoardService {
    private final BoardRepository repository;

    public JdbcOracleBoardService(BoardRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Board> listS() {
        return repository.list();
    }

    @Override
    public Board insertS(Board board) {
        return null;
    }

    @Override
    public Board contentS(int seq) {
        return null;
    }

    @Override
    public Board updateS(int seq) {
        return null;
    }

    public boolean updateSup(Board board) {
        return false;
    }

    @Override
    public boolean deleteS(int seq) {
        return false;
    }
}
