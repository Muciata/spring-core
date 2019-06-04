package ua.epam.spring.hometask.service;

import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("EventService")
public class EventServiceImpl implements EventService {

    private Map<Long, Event> events = new HashMap<>();

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        Optional<Event> foundEvent = events.values().stream()
                .filter(event -> name.equals(event.getName()))
                .findFirst();
        return foundEvent.orElse(null);
    }

    @Override
    public Event save(@Nonnull Event event) {
        if (event.getId() != null) {
            return events.put(event.getId(), event);
        }
        return null;
    }

    @Override
    public void remove(@Nonnull Event event) {
        if (event.getId() != null) {
            events.remove(event.getId());
        }
    }

    @Override
    public Event getById(@Nonnull Long id) {
        if (id != null) {
            return events.getOrDefault(id, null);
        } else {
            return null;
        }
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return events.values();
    }
}
