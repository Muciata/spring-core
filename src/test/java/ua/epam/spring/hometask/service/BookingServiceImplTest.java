package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private EventService eventService;

    @Mock
    private AuditoriumService auditoriumService;



    @Test
    public void shouldReturnTicketPrice(){
        BookingService bookingService = new BookingServiceImpl(eventService,auditoriumService);
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        Event event = EventFixtures.createEvent();
        User user = UserFixtures.createDefaultUser();
        Set<Long> seats = LongStream.rangeClosed(1,20)
                    .mapToObj(s->s)
                    .collect(Collectors.toSet());

        double ticketsPrice = bookingService.getTicketsPrice(event, airTime, user, seats);

        assertTrue(Math.abs(210-ticketsPrice)<0.01);
    }

}