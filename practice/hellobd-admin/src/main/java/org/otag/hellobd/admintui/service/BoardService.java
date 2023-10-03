package org.otag.hellobd.admintui.service;

import org.otag.hellobd.admintui.entity.Board;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void createBoard(Map<String, Object> form);

    List<Board> getBoardList();
}
