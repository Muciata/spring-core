package ua.epam.spring.hometask.service;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Event;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.service.EventFixtures.createEvent;

public class EventServiceImplTest {

    @Test
    public void shouldReturnAllEvents(){
        EventService subject = new EventServiceImpl();
        Collection<Event> eventsInserted = EventFixtures.createEvents(5);
        eventsInserted.forEach(subject::save);

        Collection<Event> allEvents = subject.getAll();

        assertEquals(5, allEvents.size());
        assertTrue(eventsInserted.containsAll(eventsInserted));
    }

    @Test
    public void shouldReturnEventByName(){
        EventService subject = new EventServiceImpl();
        Event insertedEvent = createEvent();
        subject.save(insertedEvent);

        Event event = subject.getByName("Hamlet");

        assertEquals(insertedEvent,event);
    }

    @Test
    public void shouldGetById(){
        EventService subject = new EventServiceImpl();
        Event insertedEvent = createEvent();
        subject.save(insertedEvent);

        Event event = subject.getById(1L);

        assertEquals(insertedEvent,event);
    }

    @Test
    public void shouldRemoveEvent(){
        EventService subject = new EventServiceImpl();
        Event insertedEvent = createEvent();
        subject.save(insertedEvent);

        subject.remove(insertedEvent);
        Collection<Event> allEvents = subject.getAll();

        assertTrue(allEvents.isEmpty());
    }

}