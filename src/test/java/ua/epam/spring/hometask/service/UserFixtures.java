package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserFixtures {
    public static User createDefaultUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("andrzej.strzelba@gmail.com");
        user.setFirstName("Andrzej");
        user.setLastName("Strzelba");
        user.setTickets(new TreeSet<>());
        user.setBirthDay(LocalDate.of(2000,10,10));
        return user;
    }
    public static Collection<User> createMultipleUsers(int n) {
        Collection<User> userGroup = new ArrayList<>();
        for(long id=1;id<=n;id++) {
            User user = new User();
            user.setId(id);
            user.setEmail(id+"andrzej.strzelba@gmail.com");
            user.setFirstName("Andrzej"+id);
            user.setLastName("Strzelba"+id);
            user.setTickets(new TreeSet<>());
            user.setBirthDay(LocalDate.of(2000,10,10));
            userGroup.add(user);
        }
        return userGroup;
    }



    public static void assertUser(User user) {
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("andrzej.strzelba@gmail.com", user.getEmail());
        assertEquals("Andrzej", user.getFirstName());
        assertEquals("Strzelba", user.getLastName());
        assertTrue(user.getTickets().isEmpty());
    }

    public static void assertUser(User user,long id) {
        assertEquals(Long.valueOf(id), user.getId());
        assertEquals(id+"andrzej.strzelba@gmail.com", user.getEmail());
        assertEquals("Andrzej"+id, user.getFirstName());
        assertEquals("Strzelba"+id, user.getLastName());
        assertTrue(user.getTickets().isEmpty());
    }
}
