package team.backend.service;

import team.backend.domain.Board;
import team.backend.mapper.BoardMapper;

import java.util.List;
//@Service
public class MybatisBoardService implements BoardService{
    //@Autowired
    private final BoardMapper mapper;
    //@Autowired
    public MybatisBoardService(BoardMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public List<Board> listS() {
        return mapper.list();
    }

    @Override
    public Board insertS(Board board) {
        mapper.insertSelectKey(board);
        int seq = board.getSeq(); //중요
        Board boardDb = mapper.selectBySeq(seq);
        return boardDb;
    }

    @Override
    public Board contentS(int seq) {
        //System.out.println("service content");
        return mapper.selectBySeq(seq);
    }

    @Override
    public Board updateS(int seq) {
        return mapper.selectBySeq(seq);
    }
    public boolean updateSup(Board board) {
        mapper.update(board);
        return true;
    }

    @Override
    public boolean deleteS(int seq) {
        mapper.delete(seq);
        return true;
    }
}
