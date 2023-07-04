package org.example.main.service.impl;

import org.example.main.config.Config;
import org.example.main.model.Friend;
import org.example.main.model.User;
import org.example.main.service.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendService implements Service<Friend> {

    public void createTable() {
        String query = """
                 create table friends(
                id serial primary key ,
                name varchar(50) not null ,
                last_name varchar(70),
                email varchar,
                age integer,
                address varchar,
                user_id integer references users(id)
                )
                """;

        try (Connection connection = Config.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(Friend friend) {
        String query = """
                insert into friends (name,last_name,email,age,address,user_id)
                values (?,?,?,?,?,?)
                """;
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, friend.getName());
            preparedStatement.setString(2,friend.getLast_name());
            preparedStatement.setString(3, friend.getEmail());
            preparedStatement.setInt(4, friend.getAge());
            preparedStatement.setString(5, friend.getAddress());
            preparedStatement.setInt(6, friend.getUser_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Friend findById(int id) {
        String query = """
                select * from friends where id = ?;
                """;
        Friend friendByBD = new Friend();
        try (Connection connection = Config.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                friendByBD.setId(resultSet.getInt("id"));
                friendByBD.setName(resultSet.getString("name"));
                friendByBD.setLast_name(resultSet.getString("last_name"));
                friendByBD.setEmail(resultSet.getString("email"));
                friendByBD.setAge(resultSet.getInt("age"));
                friendByBD.setAddress(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return friendByBD;
    }

    public void update(int id, Friend friend) {
        String query = """
                update friends set name = ?, last_name = ?, email = ?, age = ?, address = ? 
                where id = ?                
                """;
        try (Connection connection = Config.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, friend.getName());
            preparedStatement.setString(2, friend.getLast_name());
            preparedStatement.setString(3, friend.getEmail());
            preparedStatement.setInt(4, friend.getAge());
            preparedStatement.setString(5, friend.getAddress());
            preparedStatement.setInt(6, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Friend> getAll() {
        List<Friend> friendList = new ArrayList<>();
        String query = """
                select * from friends
                """;
        try(Connection connection = Config.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Friend friend = new Friend();
                friend.setId(resultSet.getInt("id"));
                friend.setName(resultSet.getString("name"));
                friend.setLast_name(resultSet.getString("last_name"));
                friend.setEmail(resultSet.getString("email"));
                friend.setAge(resultSet.getInt("age"));
                friend.setAddress(resultSet.getString("address"));
                friendList.add(friend);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return friendList;
    }

    public void deleteById(int id) {
        String query = """
                delete from friends where id = ?;
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
