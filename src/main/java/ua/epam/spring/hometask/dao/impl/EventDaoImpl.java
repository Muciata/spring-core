package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.dao.EventDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class EventDaoImpl implements EventDao {
    private Map<Long, Event> events = new HashMap<>();

    @Override
    public Event getByName(String name) {
        Optional<Event> foundEvent = events.values().stream()
                .filter(event -> name.equals(event.getName()))
                .findFirst();
        return foundEvent.orElse(null);
    }

    @Override
    public Event saveEvent(Event event) {
        if (event.getId() != null) {
            return events.put(event.getId(), event);
        }
        return null;
    }

    @Override
    public void removeEvent(Event event) {
        if (event.getId() != null) {
            events.remove(event.getId());
        }
    }

    @Override
    public Event getById(Long id) {
        if (id != null) {
            return events.getOrDefault(id, null);
        } else {
            return null;
        }
    }

    @Override
    public Collection<Event> getAll() {
        return events.values();
    }
}
