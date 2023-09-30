package org.otag.hellobd.admintui.service;

import org.otag.hellobd.admintui.entity.User;

import java.util.Map;

public interface UserService {
    void login(String username, String password);

    void logout();

    void createUser(Map<String, Object> form);
}
