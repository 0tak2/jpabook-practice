package org.otag.hellobd.admintui.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MainController {
    UserController userController;

    public void mainMenu() {
        System.out.println("메인메뉴");

        userController.userMain();
    }
}
