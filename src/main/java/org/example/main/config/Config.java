package org.example.main.config;

import java.sql.Connection;
import java.sql.DriverManager;

// TODO: 27.06.2023
//  Если захочешь используй мою конфигурацию, либо напиши свой!!!
public class Config {

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "mura");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
