package org.otag.hellobd.admintui;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your password: ");
        String plainStr = br.readLine();
        String encrypted = BCrypt.hashpw(plainStr, BCrypt.gensalt());

        System.out.println("Encrypted: " + encrypted);

        System.out.print("Enter any string to check: ");
        String inputStr = br.readLine();

        String result = BCrypt.checkpw(inputStr, encrypted)
                ? "Correct!"
                : "Incorrect!";
        System.out.println(result);
    }
}