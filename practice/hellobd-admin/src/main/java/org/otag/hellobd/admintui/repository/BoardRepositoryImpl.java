package org.otag.hellobd.admintui.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.BoardAdmin;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BoardRepositoryImpl implements BoardRepository {
    EntityManager em;

    @Override
    public void insert(Board board) {
        em.persist(board);
    }

    @Override
    public void insertAdmin(BoardAdmin boardAdmin) {
        em.persist(boardAdmin);
    }

    @Override
    public List<Board> selectAllBoard() {
        return em.createQuery("select b from Board b join fetch b.boardAdmins", Board.class)
                .getResultList();
    }
}
