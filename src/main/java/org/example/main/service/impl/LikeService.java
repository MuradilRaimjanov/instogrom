package org.example.main.service.impl;

import org.example.main.config.Config;
import org.example.main.model.Like;
import org.example.main.model.Post;
import org.example.main.model.User;
import org.example.main.model.enums.PostType;
import org.example.main.service.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeService implements Service<Like> {

    public void createTable() {
        String query = """
                create table likes(
                id serial primary key,
                like_time timestamp,
                post_id integer references posts(id),
                friend_id integer references friends(id)
                );  
                """;

        try (Connection connection = Config.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void save(Like like) {
        String query = """
                insert into likes (like_time,post_id,friend_id)
                values (?,?,?)
                """;
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(like.getLocalDateTime()));
            preparedStatement.setInt(2,like.getPostId());
            preparedStatement.setInt(3, like.getFriendId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Like findById(int id) {
        String query = """
                select * from likes where id = ?;
                """;
        Like likeByBd = new Like();
        try (Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                likeByBd.setId(resultSet.getInt("id"));
                likeByBd.setLocalDateTime(resultSet.getTimestamp("like_time").toLocalDateTime());
                likeByBd.setPostId(resultSet.getInt("post_id"));
                likeByBd.setFriendId(resultSet.getInt("friend-id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return likeByBd;
    }

    public void update(int id, Like like) {
        String query = """
                update likes set like_time = ?, post_id = ?, friend_id = ?
                where id = ?
                """;
        try(Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(like.getLocalDateTime()));
            preparedStatement.setInt(2,like.getPostId());
            preparedStatement.setInt(3,like.getFriendId());
            preparedStatement.setInt(4, id);
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

    }

    public List<Like> getAll() {
        List<Like> likeList = new ArrayList<>();
        String query = """
                select * from likes
                """;
        try(Connection connection = Config.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Like like = new Like();
                like.setId(resultSet.getInt("id"));
                like.setLocalDateTime(resultSet.getTimestamp("like_time").toLocalDateTime());
                like.setPostId(resultSet.getInt("post_id"));
                like.setFriendId(resultSet.getInt("friend-id"));
                likeList.add(like);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return likeList;
    }

    public void deleteById(int id) {
        String query = """
                delete from likes where id = ?;
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
