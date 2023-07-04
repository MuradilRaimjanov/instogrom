package org.example.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

// TODO: 27.06.2023 Здесь тебе достаточно добавить одно поле userId;
//  Знаешь почему?
//  Потому что Я наследовал этот класс от User; P.S. помогает наследование!
public class Friend extends User {
    private int id;
    private int user_id;

    public Friend(String name, String last_name, String email, int age, String address, int user_id) {
        super(name, last_name, email, age, address);
        this.user_id = user_id;
    }
}
