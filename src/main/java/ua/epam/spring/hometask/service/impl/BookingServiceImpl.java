package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.dao.TicketDao;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service("BookingService")
public class BookingServiceImpl implements BookingService {

    private static final int TICKET_PRICE = 10;
    private static final double VIP_PRICE_MODIFIER = 0.2;
    private EventService eventService;
    private AuditoriumService auditoriumService;
    private TicketDao ticketDao;

    @Autowired
    public BookingServiceImpl(EventService eventService, AuditoriumService auditoriumService) {
        this.eventService = eventService;
        this.auditoriumService = auditoriumService;
    }

    @Autowired
    @Qualifier("DerbyTicketDao")
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        return calculatePrice(seats, auditorium.getVipSeats());
    }

    private double calculatePrice(Set<Long> seats, Set<Long> vipSeats) {
        int reservedSeats = seats.size();
        long vipSeatsCount = seats.stream()
                .filter(vipSeats::contains)
                .count();
        return reservedSeats * TICKET_PRICE + vipSeatsCount * VIP_PRICE_MODIFIER * TICKET_PRICE;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        ticketDao.addAll(tickets);
        tickets.stream()
                .filter(ticket -> ticket.getUser() != null)
                .forEach(ticket -> ticket.getUser().getTickets().add(ticket));
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDao.getAll().stream()
                .filter(ticket -> ticket.getEvent().equals(event))
                .filter(ticket -> ticket.getEvent().getAirDates().contains(dateTime))
                .collect(Collectors.toSet());
    }
}
