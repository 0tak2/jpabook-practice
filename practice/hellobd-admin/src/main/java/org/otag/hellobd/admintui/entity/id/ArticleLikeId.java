package org.otag.hellobd.admintui.entity.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikeId implements Serializable {
    private Long article;
    private UUID user;
}
