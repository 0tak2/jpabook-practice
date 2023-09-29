package org.otag.hellobd.admintui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;

@AllArgsConstructor
public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

        MainController mainController = context.getBean(MainController.class);

        try {
            mainController.mainMenu();
        } catch (Exception e) {
            System.out.println("예기치 못한 오류가 발생하였습니다. " + e.getMessage());
        } finally {
            EntityManager em = context.getBean(EntityManager.class);
            em.close();
            EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
            emf.close();
            System.out.println("[Info] EntityManagerFactory and EntityManager are now closed.");

            BufferedReader br = context.getBean(BufferedReader.class);
            br.close();
            System.out.println("[Info] BufferedReader is now closed.");
        }
    }
}