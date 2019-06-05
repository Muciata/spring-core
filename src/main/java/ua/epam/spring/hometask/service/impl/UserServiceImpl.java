package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserDao;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public User save(@Nonnull User user) {
        return userDao.saveUser(user);
    }

    @Override
    public void remove(@Nonnull User user) {
        userDao.removeUser(user);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }
}
