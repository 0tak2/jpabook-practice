package org.otag.hellobd.admintui.service;

import org.otag.hellobd.admintui.entity.User;

public interface UserService {
    void login(String username, String password);

    void logout();
}
