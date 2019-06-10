package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.impl.UserServiceImpl;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserDao userDao;

    @Test
    public void shouldReturnUserById(){
        when(userDao.getById(anyLong())).thenReturn(UserFixtures.createDefaultUser());
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        userService.save(UserFixtures.createDefaultUser());

        User user = userService.getById(1L);

        UserFixtures.assertUser(user);
    }

    @Test
    public void shouldReturnUserByEmail(){
        when(userDao.getByEmail("andrzej.strzelba@gmail.com")).thenReturn(UserFixtures.createDefaultUser());
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);;
        userService.save(UserFixtures.createDefaultUser());

        User user = userService.getUserByEmail("andrzej.strzelba@gmail.com");

        UserFixtures.assertUser(user);
    }

    @Test
    public void shouldReturnAllUsers(){
        when(userDao.getAll()).thenReturn(Collections.singleton(UserFixtures.createDefaultUser()));
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        User userSaved = UserFixtures.createDefaultUser();
        userService.save(userSaved);

        Collection<User> users = userService.getAll();

        assertEquals(1, users.size());
        users.forEach(UserFixtures::assertUser);
    }

    @Test
    public void shouldRemoveUser(){
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        User userSaved = UserFixtures.createDefaultUser();
        userService.save(userSaved);

        userService.remove(userSaved);

        Collection<User> users = userService.getAll();
        assertTrue( users.isEmpty());
    }

}