package org.otag.hellobd.admintui;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class GeneralTest {
    @Test
    public void 암호화테스트() {
        String plainStr = "EncryptionTest1234";
        String encrypted = BCrypt.hashpw(plainStr, BCrypt.gensalt());

        assertTrue(BCrypt.checkpw(plainStr, encrypted));
    }
}
