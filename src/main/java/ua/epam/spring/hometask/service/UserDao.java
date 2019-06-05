package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.User;

import java.util.Collection;

public interface UserDao {

    Collection<User> getAll();

    User getById(long id);

    User getByEmail(String email);

    User saveUser(User user);

    void removeUser(User user);
}
