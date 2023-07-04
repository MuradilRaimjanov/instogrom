package org.example.main.service;

import org.example.main.model.User;

import java.util.List;

// TODO: 27.06.2023 Я сделал этот сервис GENERIC TYPE, чтобы можно было использовать для всех реализаторов. 
public interface Service<T> {

    void createTable();

    void save(T t);

    T findById(int id);

    void update(int id, T t);

    List<T> getAll();

    void deleteById(int id);


}
