package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private EventService eventService;

    @Mock
    private AuditoriumService auditoriumService;

    @Mock
    private TicketDao ticketDao;

    private static final LocalDateTime AIR_TIME = LocalDateTime.of(2019, 10, 12, 19, 0);


    @Test
    public void shouldReturnTicketPrice(){
        BookingService bookingService = new BookingServiceImpl(eventService,auditoriumService, ticketDao);
        Event event = EventFixtures.createEvent();
        User user = UserFixtures.createDefaultUser();
        Set<Long> seats = LongStream.rangeClosed(1,20)
                    .mapToObj(s->s)
                    .collect(Collectors.toSet());

        double ticketsPrice = bookingService.getTicketsPrice(event, AIR_TIME, user, seats);

        assertTrue(Math.abs(210-ticketsPrice)<0.01);
    }

    @Test
    public void shouldReturnPurchasedTicketForEvent(){
        Event event = EventFixtures.createEvent();
        User user = UserFixtures.createDefaultUser();
        Ticket ticket = new Ticket(user, event, AIR_TIME, 19L);
        when(ticketDao.getAll()).thenReturn(Collections.singleton(ticket));
        BookingService bookingService = new BookingServiceImpl(eventService, auditoriumService, ticketDao);
        bookingService.bookTickets(Collections.singleton(ticket));

        Set<Ticket> purchasedTickets = bookingService.getPurchasedTicketsForEvent(event, AIR_TIME);

        assertEquals(1, purchasedTickets.size());
        assertEquals(ticket, purchasedTickets.stream().findFirst().get());
    }

}