package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Ticket;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public interface TicketDao {


    void addAll(Set<Ticket> tickets);

    Collection<Ticket> getAll();
}
