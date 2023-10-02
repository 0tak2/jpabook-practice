package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.service.BoardService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
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
}
