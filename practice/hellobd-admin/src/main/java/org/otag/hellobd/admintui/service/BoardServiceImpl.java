package org.otag.hellobd.admintui.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.BoardAdmin;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.entity.enums.RoleEnum;
import org.otag.hellobd.admintui.repository.BoardRepository;
import org.otag.hellobd.admintui.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    EntityManager em;
    BoardRepository boardRepository;
    UserRepository userRepository;

    @Override
    public void createBoard(Map<String, Object> form) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Board board = Board.builder()
                .name((String) form.get("name"))
                .description((String) form.get("description"))
                .build();

        boardRepository.insert(board);

        Set<BoardAdmin> boardAdmins = Arrays.stream((String[]) form.get("admins"))
                .map(username -> {
                    User admin = userRepository.findByUsername(username).orElseThrow(
                            () -> {
                                tx.rollback();
                                return new RuntimeException("관리자를 찾을 수 없습니다.");
                            }
                    );
                    if (admin.getRole() == RoleEnum.USER) {
                        tx.rollback();
                        throw new RuntimeException("일반 유저는 게시판 관리자가 될 수 없습니다.");
                    }
                    BoardAdmin boardAdmin = BoardAdmin.builder().board(board).admin(admin).build();
                    boardRepository.insertAdmin(boardAdmin);
                    return boardAdmin;
                })
                .collect(Collectors.toSet());

        board.getBoardAdmins().addAll(boardAdmins);

        tx.commit();
    }
}