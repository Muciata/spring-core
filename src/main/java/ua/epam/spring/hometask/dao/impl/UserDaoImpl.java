package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dao.UserDao;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User getById(long id) {
        return users.getOrDefault(id, null);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> foundUser = users.values().stream()
                .filter(user -> Objects.nonNull(user.getEmail()))
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return foundUser.orElse(null);
    }

    @Override
    public User saveUser(User user) {
        Long id = user.getId();
        if (id != null) {
            users.put(id, user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void removeUser(User user) {
        if (user.getId() != null || users.containsKey(user.getId())) {
            users.remove(user.getId());
        }
    }
}
