package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@AllArgsConstructor
public class MainController {
    BufferedReader br;
    UserController userController;

    public void mainMenu() throws IOException {
        System.out.println("헬로우 보드");

        MAIN: while(true) {
            System.out.print("# ");
            String cmd = br.readLine().toLowerCase();

            try {
                switch (cmd) {
                    case "l":
                        userController.loginOrLogout();
                        break;
                    case "h":
                        System.out.println("도움말");
                        System.out.println("l: 로그인 또는 로그아웃");
                        System.out.println("q: 종료");
                        break;
                    case "q":
                        break MAIN;
                    default:
                        System.out.println("다시 입력해주세요. 도움말을 보려면 h를 입력하세요.");
                        break;
                }
            } catch (RuntimeException re) {
                System.out.println("오류: " + re.getMessage());
            }
        }
    }
}
