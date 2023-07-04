package org.example.main.model;
import lombok.NoArgsConstructor;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

// TODO: 27.06.2023 ID,
//  LIKED(время когда было поставлено лайк),
//  int postId,
//  int friendId(кто поставил этот лайк).
//  P.s. Не забудь геттеры и сеттеры во всех классах!!!



public class Like {
    private int id;
    private LocalDateTime localDateTime;
    private  int postId;
    private int friendId;


    public Like(LocalDateTime localDateTime, int postId, int friendId) {
        this.localDateTime = localDateTime;
        this.postId = postId;
        this.friendId = friendId;
    }
}
