package org.otag.hellobd.admintui.repository;

import org.otag.hellobd.admintui.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsernameAndPassword(String username, String password);
}
