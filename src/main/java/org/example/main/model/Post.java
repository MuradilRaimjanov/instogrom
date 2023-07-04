package org.example.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.main.model.enums.PostType;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@ToString




// TODO: 27.06.2023 ID,
//  NAME,
//  DESCRIPTION,
//  CREATED(дата создание поста),
//  likeCounter(количество лайков, поставленных на этот пост),
//  userId(с каим пользователем связано),
//  тип поста(видео или фото).
public class Post {


    private int id;
    private String name;
    private String description;
    private LocalDateTime localDateTime;
    private int likeCounter;
    private int userId;
    private PostType postType;


    public Post(String name, String description, LocalDateTime localDateTime, int userId, PostType postType) {
        this.name = name;
        this.description = description;
        this.localDateTime = localDateTime;
        this.userId = userId;
        this.postType = postType;
    }
}
