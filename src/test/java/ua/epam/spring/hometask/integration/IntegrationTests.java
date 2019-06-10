package ua.epam.spring.hometask.integration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.dao.CounterStatsDao;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import ua.epam.spring.hometask.dao.DiscountStatsDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class IntegrationTests {

    @Test
    public void shouldAddAndRemoveUsers() {
        ApplicationContext applicationContext = getApplicationContext();
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
        ApplicationContext applicationContext = getApplicationContext();
        EventService eventService = applicationContext.getBean(EventService.class);
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        Auditorium insertAud1 = applicationContext.getBean("auditorium1", Auditorium.class);
        Auditorium insertAud2 = applicationContext.getBean("auditorium2", Auditorium.class);
        auditoriumService.save(insertAud1);
        auditoriumService.save(insertAud2);

        eventService.save(EventFixtures.createEvent("Left Auditorium"));
        Event eventById = eventService.getById(1L);
        Event eventByName = eventService.getByName("Hamlet");

        assertEquals(eventById,eventByName);
        assertEquals(1,eventService.getAll().size());
    }



    @Test
    public void shouldReturnAuditoriums(){
        ApplicationContext applicationContext = getApplicationContext();
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        Auditorium insertAud1 = applicationContext.getBean("auditorium1", Auditorium.class);
        Auditorium insertAud2 = applicationContext.getBean("auditorium2", Auditorium.class);
        auditoriumService.save(insertAud1);
        auditoriumService.save(insertAud2);


        Auditorium auditorium1 = auditoriumService.getByName("Left Auditorium");
        Auditorium auditorium2 = auditoriumService.getByName("Right Auditorium");

        assertEquals(auditorium1.getName(),"Left Auditorium");
        assertEquals(auditorium2.getName(),"Right Auditorium");
        assertEquals(2, auditoriumService.getAll().size());
    }

    private AnnotationConfigApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext("ua.epam.spring.hometask.service",
                "ua.epam.spring.hometask.dao","ua.epam.spring.hometask.dao.impl","ua.epam.spring.hometask.statistics");
    }

    @Test
    public void shouldBookTickets(){
        ApplicationContext applicationContext = getApplicationContext();
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
        ApplicationContext applicationContext = getApplicationContext();
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        User user = UserFixtures.createDefaultUser();
        EventService eventService = applicationContext.getBean(EventService.class);
        Event event = EventFixtures.createEvent("Left Auditorium");
        BookingService bookingService = applicationContext.getBean(BookingService.class);
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        DiscountService discountService = applicationContext.getBean("DiscountService",DiscountService.class);
        Auditorium insertAud1 = applicationContext.getBean("auditorium1", Auditorium.class);
        Auditorium insertAud2 = applicationContext.getBean("auditorium2", Auditorium.class);


        auditoriumService.save(insertAud1);
        auditoriumService.save(insertAud2);

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
    @Test
    public void shouldCountOnlyEventByNameInvocations(){
        ApplicationContext applicationContext = getApplicationContext();
        EventService eventService = applicationContext.getBean(EventService.class);
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        CounterStatsDao eventStats = applicationContext.getBean("Derby",CounterStatsDao.class);
        Auditorium insertAud1 = applicationContext.getBean("auditorium1", Auditorium.class);
        Auditorium insertAud2 = applicationContext.getBean("auditorium2", Auditorium.class);
        auditoriumService.save(insertAud1);
        auditoriumService.save(insertAud2);

        eventService.save(EventFixtures.createEvent("Left Auditorium"));
        for(int i=0;i<5;i++) {
            eventService.getByName("Hamlet");
            auditoriumService.getByName("Hamlet");
        }

        assertEquals(5,eventStats.getStatsForName("Hamlet").getCallsByName());
    }

    @Test
    public void shouldCountPriceChecks(){
        ApplicationContext applicationContext = getApplicationContext();
        UserService userService = applicationContext.getBean(UserService.class);
        User user = UserFixtures.createDefaultUser();
        EventService eventService = applicationContext.getBean(EventService.class);
        Event event = EventFixtures.createEvent("Left Auditorium");
        BookingService bookingService = applicationContext.getBean(BookingService.class);
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        CounterStatsDao eventStats = applicationContext.getBean("Derby",CounterStatsDao.class);
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        Auditorium insertAud1 = applicationContext.getBean("auditorium1", Auditorium.class);
        Auditorium insertAud2 = applicationContext.getBean("auditorium2", Auditorium.class);


        auditoriumService.save(insertAud1);
        auditoriumService.save(insertAud2);

        userService.save(user);
        eventService.save(event);
        int numberOfTickets = 20;
        Set<Long> seats = createSeats(numberOfTickets);
        bookingService.getTicketsPrice(event, airTime, user, seats);

        assertEquals(1,eventStats.getStatsForName("Hamlet").getCallsByPriceCheck());
    }

    @Test
    public void shouldCountTicketBookings(){
        ApplicationContext applicationContext = getApplicationContext();
        UserService userService = applicationContext.getBean(UserService.class);
        User user = UserFixtures.createDefaultUser();
        EventService eventService = applicationContext.getBean(EventService.class);
        Event event = EventFixtures.createEvent("Left Auditorium");
        BookingService bookingService = applicationContext.getBean(BookingService.class);
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        CounterStatsDao eventStats = applicationContext.getBean("Derby",CounterStatsDao.class);
        AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        Auditorium insertAud1 = applicationContext.getBean("auditorium1", Auditorium.class);
        Auditorium insertAud2 = applicationContext.getBean("auditorium2", Auditorium.class);

        auditoriumService.save(insertAud1);
        auditoriumService.save(insertAud2);
        userService.save(user);
        eventService.save(event);
        Set<Ticket> purchasedTickets = Collections.singleton(new Ticket(user, event, airTime, 20));
        bookingService.bookTickets(purchasedTickets);
        bookingService.bookTickets(purchasedTickets);

        assertEquals(2,eventStats.getStatsForName("Hamlet").getCallsByTicketsBooked());
    }

    @Test
    public void shouldCountAllDiscountCalls(){
        ApplicationContext applicationContext = getApplicationContext();
        User user = UserFixtures.createDefaultUser();
        Event event = EventFixtures.createEvent("Left Auditorium");
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        DiscountStatsDao discountStatsDao = applicationContext.getBean("DerbyDiscountStatsDao",DiscountStatsDao.class);
        DiscountService discountService = applicationContext.getBean("DiscountService",DiscountService.class);

        for(int i=0;i<5;i++) {
            discountService.getDiscount(user, event, airTime, 20);
        }

        assertEquals(5,discountStatsDao.getTotalDiscounts());
    }

}
