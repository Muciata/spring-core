package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import java.util.Collection;

public interface EventDao {
    Event getByName(String name);

    Event saveEvent(Event event);

    void removeEvent(Event event);

    Event getById(Long id);

    Collection<Event> getAll();
}
