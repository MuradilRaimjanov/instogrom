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

// TODO: 27.06.2023
//  Здесь ты должен реализовать весь CRUD
//  (create -save,
//  read - findById || getAll,
//  update - update,
//  delete - delete) и дополнительные методы!!!
public class UserService implements Service<User> {

    public User followYou(int userId, int friendId) {
        return null;

    }

    public static List<Friend> getAllFriends(int userId) {
        List<Friend> friendList = new ArrayList<>();
        String query = """
                    select * from friends where user_id = ?
                    """;

        try (Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setId(resultSet.getInt("id"));
                friend.setName(resultSet.getString("name"));
                friend.setLast_name(resultSet.getString("last_name"));
                friend.setEmail(resultSet.getString("email"));
                friend.setAge(resultSet.getInt("age"));
                friend.setAddress(resultSet.getString("address"));
                friend.setUser_id(resultSet.getInt("user_id"));
                friendList.add(friend);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return friendList;
    }

    public static void sendLike(int postId) {
        String sql = """
               update posts set like_counter  = case when like_counter is null then 1
                   when like_counter is not null then like_counter + 1 end
               where id = ?
                """;

        try (Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postId);
            preparedStatement.execute();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Post> getNewPosts() {
        List<Post> postList = new ArrayList<>();
        Post post = new Post();
        LocalDateTime ldt = LocalDateTime.now();
        String query = """
                select * from posts where post_time between ? and ?  
                """;
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(ldt.getYear(), ldt.getMonth(),
                    ldt.getDayOfMonth(), ldt.getHour(),ldt.getMinute(),ldt.getSecond()).minusDays(3)));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(ldt.getYear(), ldt.getMonth(),
                    ldt.getDayOfMonth(), ldt.getHour(),ldt.getMinute(),ldt.getSecond())));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                post.setId(resultSet.getInt("id"));
                post.setName(resultSet.getString("name"));
                post.setDescription(resultSet.getString("description"));
                post.setLocalDateTime(resultSet.getTimestamp("post_time").toLocalDateTime());
                post.setLikeCounter(resultSet.getInt("like_counter"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setPostType(PostType.valueOf(resultSet.getString("posttype")));
                postList.add(post);
            }

        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return  postList;
    }

    public static Post getPopularPost() {
        Post post = new Post();
        String query = """
                select * from posts where like_counter = (select max(like_counter) from posts)
                """;

        try (Connection connection = Config.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                post.setId(resultSet.getInt("id"));
                post.setName(resultSet.getString("name"));
                post.setDescription(resultSet.getString("description"));
                post.setLocalDateTime(resultSet.getTimestamp("post_time").toLocalDateTime());
                post.setLikeCounter(resultSet.getInt("like_counter"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setPostType(PostType.valueOf(resultSet.getString("posttype")));

            }


        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return post;
    }

    public void createTable() {
        String query = """
                 create table users(
                id serial primary key ,
                name varchar(50) not null ,
                last_name varchar(70),
                email varchar,
                age integer,
                address varchar              
                )
                """;

        try (Connection connection = Config.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void save(User user) {
        String query = """
                insert into users (name,last_name,email,age,address)
                values (?,?,?,?,?)
                """;
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2,user.getLast_name());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User findById(int id) {
        String query = """
                select * from users where id = ?;
                """;
        User userFromBd = new User();
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userFromBd.setId(resultSet.getInt("id"));
                userFromBd.setName(resultSet.getString("name"));
                userFromBd.setLast_name(resultSet.getString("last_name"));
                userFromBd.setEmail(resultSet.getString("email"));
                userFromBd.setAge(resultSet.getInt("age"));
                userFromBd.setAddress(resultSet.getString("address"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userFromBd;
    }

    public void update(int id, User user) {
        String query = """
                update users set name = ?, last_name = ?, email = ?, age = ?, address = ? 
                where id = ?                
                """;
        try (Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setInt(6,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String query = """
                select * from users
                """;
        try(Connection connection = Config.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("address"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;

    }

    public void deleteById(int id) {
        String query = """
                delete from users where id = ?;
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
