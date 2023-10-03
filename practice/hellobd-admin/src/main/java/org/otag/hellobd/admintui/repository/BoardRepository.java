package org.otag.hellobd.admintui.repository;

import jakarta.persistence.EntityTransaction;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.BoardAdmin;

import java.util.List;

public interface BoardRepository {
    void insert(Board board);

    void insertAdmin(BoardAdmin boardAdmin);

    List<Board> selectAllBoard();
}
