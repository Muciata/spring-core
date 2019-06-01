package ua.epam.spring.hometask.integration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
    public void shouldReturnAuditoriums(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);

        Auditorium auditorium1 = auditoriumService.getByName("Muzyczny");
        Auditorium auditorium2 = auditoriumService.getByName("Roma");

        assertEquals(auditorium1.getName(),"Left Auditorium");
        assertEquals(auditorium2.getName(),"Right Auditorium");
        assertEquals(2, auditoriumService.getAll().size());
    }

    @Test
    public void shouldBookTickets(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
        BookingService bookingService = applicationContext.getBean(BookingService.class);
        User user = UserFixtures.createDefaultUser();
        LocalDateTime airTime = LocalDateTime.of(2019,10,10,20,0);
        Event event = EventFixtures.createEvent();
        event.setAirDates(new TreeSet<>(Collections.singleton(airTime)));
        Ticket ticket = new Ticket(user, event, airTime, 20);

        bookingService.bookTickets(Collections.singleton(ticket));
        Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(event, airTime);
        Ticket retrievedTicket = purchasedTickets.stream().findFirst().get();

        assertEquals(ticket,retrievedTicket);
    }

    @Test
    public void newUserBuysTickets(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/beans.xml");
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        User user = UserFixtures.createDefaultUser();
        EventService eventService = applicationContext.getBean(EventService.class);
        Event event = EventFixtures.createEvent("Left Auditorium");
        BookingService bookingService = applicationContext.getBean(BookingService.class);
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        DiscountService discountService = applicationContext.getBean(DiscountService.class);

        userService.save(user);
        User userById = userService.getById(user.getId());
        Auditorium leftAuditorium = auditoriumService.getByName("Left Auditorium");
        eventService.save(event);
        Event eventById = eventService.getById(event.getId());
        int numberOfTickets = 20;
        Set<Long> seats = createSeats(numberOfTickets);
        Set<Ticket> tickets = createTicketSet(userById,eventById,airTime,seats);
        double ticketsPrice = bookingService.getTicketsPrice(event, airTime, user, seats);
        bookingService.bookTickets(tickets);
        Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(event, airTime);
        byte discount = discountService.getDiscount(userById, eventById, airTime, numberOfTickets);

        purchasedTickets.forEach(t-> assertAuditorium(t,leftAuditorium));
        assertTrue(Math.abs(210-ticketsPrice)<0.001);
        assertEquals(5,discount);
    }

    private void assertAuditorium(Ticket ticket, Auditorium expectedAuditorium) {
        Auditorium ticketAuditorium = ticket.getEvent().getAuditoriums().get(ticket.getDateTime());
        assertEquals(ticketAuditorium.getName(),expectedAuditorium.getName());
    }

    private Set<Long> createSeats(int seats) {
        return IntStream.rangeClosed(1, seats).mapToObj(s->(long)s).collect(Collectors.toSet());
    }

    private Set<Ticket> createTicketSet(User userById, Event eventById, LocalDateTime airTime, Set<Long> seats) {
        return  seats.stream()
                .map(seat-> new Ticket(userById,eventById,airTime,seat))
                .collect(Collectors.toSet());
    }

}