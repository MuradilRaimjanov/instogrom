package org.example.main.service.impl;

import org.example.main.config.Config;
import org.example.main.model.Friend;
import org.example.main.model.Post;
import org.example.main.model.User;
import org.example.main.model.enums.PostType;
import org.example.main.service.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService implements Service<Post> {

    @Override
    public void createTable() {
        String query = """
                create table posts(
                id serial primary key ,
                name varchar,
                description varchar(300),
                post_time timestamp,
                like_counter integer,
                user_id integer references users(id),
                postType varchar
                )
                """;

        try (Connection connection = Config.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Post post) {
        String query = """
                insert into posts (name,description,post_time,user_id,posttype)
                values (?,?,?,?,?)
                """;
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2,post.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getLocalDateTime()));
            preparedStatement.setInt(4, post.getUserId());
            preparedStatement.setString(5, String.valueOf(post.getPostType()));
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Post findById(int id) {
        String query = """
                select * from posts where id = ?;
                """;
        Post postByBd = new Post();
        try (Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                postByBd.setId(resultSet.getInt("id"));
                postByBd.setName(resultSet.getString("name"));
                postByBd.setDescription(resultSet.getString("description"));
                postByBd.setLocalDateTime(resultSet.getTimestamp("post_time").toLocalDateTime());
                postByBd.setLikeCounter(resultSet.getInt("like_counter"));
                postByBd.setUserId(resultSet.getInt("user_id"));
                postByBd.setPostType(PostType.valueOf(resultSet.getString("posttype")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postByBd;

    }
        @Override
    public void update (int id, Post post) {
            String query = """
                update posts set name = ?,description = ?,post_time = ?,like_counter = ?,user_id = ?,posttype = ? 
                where id = ?                
                """;
            try (Connection connection = Config.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, post.getName());
                preparedStatement.setString(2, post.getDescription());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getLocalDateTime()));
                preparedStatement.setInt(4, post.getLikeCounter());
                preparedStatement.setString(5, String.valueOf(post.getPostType()));
                preparedStatement.setInt(6, id);
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }


    @Override
    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        String query = """
                select * from posts
                """;
        try(Connection connection = Config.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setName(resultSet.getString("name"));
                post.setDescription(resultSet.getString("description"));
                post.setLocalDateTime(resultSet.getTimestamp("post_time").toLocalDateTime());
                post.setLikeCounter(resultSet.getInt("like_counter"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setPostType(PostType.valueOf(resultSet.getString("posttype")));
                postList.add(post);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postList;
    }

    @Override
    public void deleteById(int id) {
        String query = """
                delete from posts where id = ?;
                """;
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
