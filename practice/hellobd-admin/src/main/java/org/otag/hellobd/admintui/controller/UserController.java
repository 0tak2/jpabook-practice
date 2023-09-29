package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.service.UserService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@AllArgsConstructor
public class UserController {
    Global global;
    BufferedReader br;
    UserService service;

    public void loginOrLogout() throws IOException {
        User user = global.getLoginedUser();

        if (user == null) {
            System.out.print("아이디: ");
            String username = br.readLine();

            System.out.print("비밀번호: ");
            String password = br.readLine();

            service.login(username, password);
        } else {
            System.out.print("현재 " + user.getUsername() + " 계정으로 로그인되어있습니다. 로그아웃 하시겠습니까? (y / N) ");
            String resp = br.readLine().toLowerCase();

            if ("y".equals(resp)) {
                service.logout();
            } else {
                System.out.println("로그아웃 작업이 취소되었습니다.");
            }
        }
    }
}
