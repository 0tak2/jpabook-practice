package org.otag.hellobd.admintui;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@AllArgsConstructor
public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

        MainController mainController = context.getBean(MainController.class);

        mainController.mainMenu();
    }
}