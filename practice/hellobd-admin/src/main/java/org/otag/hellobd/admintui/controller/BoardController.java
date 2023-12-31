package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.service.BoardService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class BoardController {
    private final Global global;
    private final BufferedReader br;
    private final BoardService service;

    public void createBoard() throws IOException {
        User user = global.getLoginedUser();
        if (user == null) throw new RuntimeException("로그인이 필요한 작업입니다.");

        Map<String, Object> form = new HashMap<>();

        System.out.println("게시판 이름: ");
        form.put("name", br.readLine());

        System.out.println("게시판 설명: ");
        form.put("description", br.readLine());

        System.out.println("게시판 관리자 아이디(여러 명이면 스페이스 바로 구분: ");
        String[] adminUsernames = br.readLine().split(" ");
        form.put("admins", adminUsernames);

        service.createBoard(form);

        System.out.println("\"" + form.get("name") + "\" 게시판이 생성되었습니다.");
    }

    public void listBoard() {
        User user = global.getLoginedUser();
        if (user == null) throw new RuntimeException("로그인이 필요한 작업입니다.");

        List<Board> boardList = service.getBoardList();

        boardList.forEach(System.out::println);
    }

    public void selectBoard(long boardId) {
        Board board = service.selectBoard(boardId);
        global.setSelectedBoard(board);
        System.out.println(boardId + "번 게시판이 선택되었습니다.");
    }

    public void createArticle() throws IOException {
        User user = global.getLoginedUser();
        if (user == null) throw new RuntimeException("로그인이 필요한 작업입니다.");

        Board selectedBoard = global.getSelectedBoard();
        if (selectedBoard == null) throw new RuntimeException("선택된 게시판이 없습니다. select board N 명령으로 게시판을 선택할 수 있습니다.");

        Map<String, Object> form = new HashMap<>();

        System.out.println("게시글 제목: ");
        form.put("title", br.readLine());

        System.out.println("게시글 내용: ");
        form.put("content", br.readLine());

        service.createArticle(user, selectedBoard, form);

        System.out.println("\"" + form.get("title") + "\" 게시글이 생성되었습니다.");
    }
}
