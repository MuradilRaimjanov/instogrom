package org.example.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

// TODO: 27.06.2023 ID, NAME, LAST_NAME, EMAIL, AGE, ADDRESS, FRIEND_ID;
public class User {

    private int id;
    private String name;
    private String last_name;
    private String email;
    private int age;
    private String address;


    public User(String name, String last_name, String email, int age, String address) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.age = age;
        this.address = address;

    }


}
