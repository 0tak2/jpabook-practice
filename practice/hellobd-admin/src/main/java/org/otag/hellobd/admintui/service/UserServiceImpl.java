package org.otag.hellobd.admintui.service;

import lombok.AllArgsConstructor;
import org.otag.hellobd.admintui.Global;
import org.otag.hellobd.admintui.entity.User;
import org.otag.hellobd.admintui.entity.enums.RoleEnum;
import org.otag.hellobd.admintui.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository repository;
    Global global;

    @Override
    public void login(String username, String password) {
        String salt = global.getUserSalt();

        User user = repository.findByUsernameAndPassword(username, BCrypt.hashpw(password, salt))
                .orElseThrow(() -> new RuntimeException("아이디나 비밀번호가 잘못되었습니다."));

        if (user.getRole() != RoleEnum.SYS_ADMIN) {
            throw new RuntimeException("시스템 관리자만 로그인할 수 있습니다.");
        }

        global.setLoginedUser(user);
    }

    @Override
    public void logout() {
        global.setLoginedUser(null);
    }

    @Override
    public void createUser(Map<String, Object> form) {
        String salt = global.getUserSalt();

        User user = User.builder()
                .username((String)form.get("username"))
                .password(BCrypt.hashpw((String)form.get("password"), salt))
                .name((String)form.get("name"))
                .email((String)form.get("email"))
                .birthday((LocalDateTime) form.get("birthday"))
                .isActive((Boolean)form.get("isActive"))
                .role((RoleEnum)form.get("role"))
                .build();

        repository.insert(user);
    }

    @Override
    public List<User> getUserList() {
        return repository.selectList();
    }
}
