package ua.epam.spring.hometask.integration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.*;

import static org.junit.Assert.assertEquals;


public class IntegrationTests {

    @Test
    public void shouldAddAndRemoveUsers() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
        UserService userService = applicationContext.getBean(UserService.class);


        UserFixtures.createMultipleUsers(5).forEach(userService::save);
        User userByEmail = userService.getUserByEmail("5andrzej.strzelba@gmail.com");
        User userById = userService.getById(4L);
        userService.remove(userByEmail);


        UserFixtures.assertUser(userByEmail,5L);
        UserFixtures.assertUser(userById, 4L);
        assertEquals(4,userService.getAll().size());

    }

    @Test
    public void shouldAddAndRemoveEvents(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
        EventService eventService = applicationContext.getBean(EventService.class);

        eventService.save(EventFixtures.createEvent());
        Event eventById = eventService.getById(1L);
        Event eventByName = eventService.getByName("Hamlet");

        assertEquals(eventById,eventByName);
        assertEquals(1,eventService.getAll().size());

    }

    @Test
    public  void shouldReturnAuditoriums(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);

        Auditorium auditorium1 = auditoriumService.getByName("Muzyczny");
        Auditorium auditorium2 = auditoriumService.getByName("Roma");

        assertEquals(auditorium1.getName(),"Left Auditorium");
        assertEquals(auditorium2.getName(),"Right Auditorium");
        assertEquals(2, auditoriumService.getAll().size());
    }

}
