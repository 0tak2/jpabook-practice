package org.otag.hellobd.admintui.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.BoardAdmin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Board> findById(long boardId) {
        Board board = null;
        try {
            board = em.find(Board.class, boardId);
        } catch (NoResultException ignored) {
        }

        return Optional.ofNullable(board);
    }
}
