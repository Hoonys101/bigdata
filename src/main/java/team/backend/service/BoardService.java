package team.backend.service;


import team.backend.domain.Board;

import java.util.List;

public interface BoardService {

    List<Board> listS();

    Board insertS(Board board);

    Board contentS(int seq);

    Board updateS(int seq);

    boolean updateSup(Board board);

    boolean deleteS(int seq);
}
