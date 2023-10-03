package org.otag.hellobd.admintui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.otag.hellobd.admintui.entity.Board;
import org.otag.hellobd.admintui.entity.User;

@Setter
@Getter
@RequiredArgsConstructor
public class Global {
    private User loginedUser;

    private final String userSalt;

    private Board selectedBoard;
}
