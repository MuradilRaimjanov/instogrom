package org.example;

import org.example.main.model.Friend;
import org.example.main.model.Like;
import org.example.main.model.Post;
import org.example.main.model.User;
import org.example.main.model.enums.PostType;
import org.example.main.service.Service;
import org.example.main.service.impl.FriendService;
import org.example.main.service.impl.LikeService;
import org.example.main.service.impl.PostService;
import org.example.main.service.impl.UserService;

import java.time.LocalDateTime;

/**
 * Hello world!
 */
public class App {
    /**
     *
     * Я В ТЕБЕ ВЕРЮ, У ТЕБЯ ВСЕ ПОЛУЧИТСЯ!!!
     */
    public static void main(String[] args) {

        System.out.println(UserService.getNewPosts());

    }
}
