package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.entity.enums.RoleEnum;
import org.otag.hellobd.admintui.service.UserService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public void createUser() throws IOException {
        User user = global.getLoginedUser();
        if (user == null) throw new RuntimeException("로그인이 필요한 작업입니다.");

        Map<String, Object> form = new HashMap<>();

        System.out.println("아이디: ");
        form.put("username", br.readLine());

        System.out.println("비밀번호: ");
        form.put("password", br.readLine());

        System.out.println("이름: ");
        form.put("name", br.readLine());

        System.out.println("이메일: ");
        String emailInput = br.readLine();
        if (!emailInput.contains("@")) throw new RuntimeException("이메일을 잘못 입력했습니다.");
        form.put("email", emailInput);

        System.out.println("생일(YYYY-MM-DD): ");
        String birthdayInput = br.readLine();
        String[] birthDataStr = birthdayInput.split("-");
        int[] birthData = Arrays.stream(birthDataStr).mapToInt(Integer::parseInt).toArray();
        if (birthData.length != 3) throw new RuntimeException("생일을 잘못 입력했습니다.");
        form.put("birthday", LocalDateTime.of(birthData[0], birthData[1], birthData[2], 0, 0));

        System.out.println("활성화 여부 (y / N): ");
        boolean isActive = "y".equalsIgnoreCase(br.readLine());
        form.put("isActive", isActive);

        System.out.println("권한: (0: 일반, 1: 관리자, 2: 시스템 관리자)");
        int order = Integer.parseInt(br.readLine());
        RoleEnum role = switch (order) {
            case 0 -> RoleEnum.USER;
            case 1 -> RoleEnum.ADMIN;
            case 2 -> RoleEnum.SYS_ADMIN;
            default -> throw new RuntimeException("권한을 잘못 입력했습니다.");
        };
        form.put("role", role);

        service.createUser(form);
    }
}
