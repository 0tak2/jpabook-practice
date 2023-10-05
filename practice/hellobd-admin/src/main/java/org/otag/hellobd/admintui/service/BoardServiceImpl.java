package org.otag.hellobd.admintui.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.Article;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.BoardAdmin;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.entity.enums.RoleEnum;
import org.otag.hellobd.admintui.repository.BoardRepository;
import org.otag.hellobd.admintui.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.*;
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

    @Override
    public List<Board> getBoardList() {
        return boardRepository.selectAllBoard();
    }

    @Override
    public Board selectBoard(long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("입력한 아이디의 게시판을 찾을 수 없습니다."));
    }

    @Override
    public void createArticle(User author, Board board, Map<String, Object> form) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Article article = Article.builder()
                .author(author)
                .board(board)
                .title((String) form.get("title"))
                .content((String) form.get("content"))
                .viewCount(0L)
                .isHidden(false)
                .build();

        boardRepository.insertArticle(article);

        tx.commit();
    }
}
