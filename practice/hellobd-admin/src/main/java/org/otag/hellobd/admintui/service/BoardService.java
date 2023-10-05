package org.otag.hellobd.admintui.service;

import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.User;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void createBoard(Map<String, Object> form);

    List<Board> getBoardList();

    Board selectBoard(long boardId);

    void createArticle(User author, Board board, Map<String, Object> form);
}
