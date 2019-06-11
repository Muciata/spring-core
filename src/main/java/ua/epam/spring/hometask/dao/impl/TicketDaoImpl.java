package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.dao.TicketDao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class TicketDaoImpl implements TicketDao {

    private Set<Ticket> allTickets = new HashSet<>();


    @Override
    public void addAll(Set<Ticket> tickets) {
        allTickets.addAll(tickets);
    }

    @Override
    public Collection<Ticket> getAll() {
        return allTickets;
    }

    @Override
    public Collection<Ticket> getAllWithoutUser() {
        return allTickets;
    }
}
