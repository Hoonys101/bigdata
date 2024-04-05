package team.backend.repository;

import team.backend.domain.Board;

import java.util.List;

public interface BoardRepository {


    List<Board> list();

    Board insert(Board board);
    Board content(int seq);
    Boolean delete(int seq);

    Boolean update(Board board);



}
