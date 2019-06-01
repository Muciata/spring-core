package ua.epam.spring.hometask.service;

import org.junit.Test;
import ua.epam.spring.hometask.domain.User;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserServiceImplTest {

    @Test
    public void shouldReturnUserById(){
        UserService userService = new UserServiceImpl();
        userService.save(UserFixtures.createDefaultUser());

        User user = userService.getById(1L);

        UserFixtures.assertUser(user);
    }

    @Test
    public void shouldReturnUserByEmail(){
        UserService userService = new UserServiceImpl();
        userService.save(UserFixtures.createDefaultUser());

        User user = userService.getUserByEmail("andrzej.strzelba@gmail.com");

        UserFixtures.assertUser(user);
    }

    @Test
    public void shouldReturnAllUsers(){
        UserService userService = new UserServiceImpl();
        User userSaved = UserFixtures.createDefaultUser();
        userService.save(userSaved);

        Collection<User> users = userService.getAll();

        assertEquals(1, users.size());
        users.forEach(UserFixtures::assertUser);
    }

    @Test
    public void shouldRemoveUser(){
        UserService userService = new UserServiceImpl();
        User userSaved = UserFixtures.createDefaultUser();
        userService.save(userSaved);

        userService.remove(userSaved);

        Collection<User> users = userService.getAll();
        assertTrue( users.isEmpty());
    }

}