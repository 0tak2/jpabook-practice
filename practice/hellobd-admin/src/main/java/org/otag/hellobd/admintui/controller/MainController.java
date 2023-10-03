package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.User;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@AllArgsConstructor
public class MainController {
    private final Global global;
    private final BufferedReader br;
    private final UserController userController;
    private final BoardController boardController;

    public void mainMenu() throws IOException {
        System.out.println("헬로우 보드");

        MAIN: while(true) {
            System.out.print("# ");
            String input = br.readLine().toLowerCase();

            String[] chunks = input.split(" ");

            String cmd = null;
            String arg = null;
            if (chunks.length > 2) {
                cmd = chunks[0] + " " + chunks[1];
                arg = chunks[2];
            } else {
                cmd = input;
            }

            try {
                switch (cmd) {
                    case "login":
                    case "logout":
                        userController.loginOrLogout();
                        break;
                    case "session":
                        printSessionInfo();
                        break;
                    case "create user":
                        userController.createUser();
                        break;
                    case "list user":
                        userController.listUser();
                        break;
                    case "create board":
                        boardController.createBoard();
                        break;
                    case "list board":
                        boardController.listBoard();
                        break;
                    case "select board":
                        if (arg == null) throw new RuntimeException("게시판 번호가 없습니다.");
                        boardController.selectBoard(Long.parseLong(arg));
                        break;
                    case "help":
                        System.out.println("도움말");
                        System.out.println("login/logout: 로그인 또는 로그아웃");
                        System.out.println("session: 현재 로그인 정보 및 선택 오브젝트");
                        System.out.println("create user: 유저 등록");
                        System.out.println("list user: 유저 조회");
                        System.out.println("create board: 게시판 생성");
                        System.out.println("list board: 게시판 조회");
                        System.out.println("select board N: N번 게시판 선택");
                        System.out.println("quit: 종료");
                        break;
                    case "exit":
                    case "quit":
                        break MAIN;
                    default:
                        System.out.println("다시 입력해주세요. 도움말을 보려면 help를 입력하세요.");
                        break;
                }
            } catch (RuntimeException re) {
                System.out.println("오류: " + re.getMessage());
            }
        }
    }

    private void printSessionInfo() {
        System.out.println("=== 로그인 정보 ===");
        User loginedUser = global.getLoginedUser();
        if (loginedUser == null) {
            System.out.println("현재 비로그인 상태입니다. login 명령으로 로그인할 수 있습니다.");
        } else {
            System.out.println("현재 " + loginedUser.getUsername() + " 계정으로 로그인되어 있습니다.");
            System.out.println(global.getLoginedUser());
        }
        System.out.println();

        System.out.println("=== 선택된 게시판 ===");
        Board selectedBoard = global.getSelectedBoard();
        if (selectedBoard == null) {
            System.out.println("현재 선택된 게시판이 없습니다.");
        } else {
            System.out.println("현재 " + selectedBoard.getId() + "번 게시판이 선택되어 있습니다.");
            System.out.println(selectedBoard);
        }
    }
}
