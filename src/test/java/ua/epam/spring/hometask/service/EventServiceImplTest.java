package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.impl.EventServiceImpl;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static ua.epam.spring.hometask.service.EventFixtures.createEvent;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {

    @Mock
    private EventDao eventDao;

    @Test
    public void shouldReturnAllEvents(){
        when(eventDao.getAll()).thenReturn(EventFixtures.createEvents(5));
        EventServiceImpl subject = new EventServiceImpl();
        subject.setEventDao(eventDao);
        Collection<Event> eventsInserted = EventFixtures.createEvents(5);
        eventsInserted.forEach(subject::save);

        Collection<Event> allEvents = subject.getAll();

        assertEquals(5, allEvents.size());
        assertTrue(eventsInserted.containsAll(eventsInserted));
    }

    @Test
    public void shouldReturnEventByName(){
        when(eventDao.getByName("Hamlet")).thenReturn(createEvent());
        EventServiceImpl subject = new EventServiceImpl();
        subject.setEventDao(eventDao);
        Event insertedEvent = createEvent();
        subject.save(insertedEvent);

        Event event = subject.getByName("Hamlet");

        assertEquals(insertedEvent,event);
    }

    @Test
    public void shouldGetById(){
        when(eventDao.getById(1L)).thenReturn(createEvent());

        EventServiceImpl subject = new EventServiceImpl();
        subject.setEventDao(eventDao);
        Event insertedEvent = createEvent();
        subject.save(insertedEvent);

        Event event = subject.getById(1L);

        assertEquals(insertedEvent,event);
    }

    @Test
    public void shouldRemoveEvent(){
        when(eventDao.getAll()).thenReturn(Collections.emptySet());

        EventServiceImpl subject = new EventServiceImpl();
        subject.setEventDao(eventDao);
        Event insertedEvent = createEvent();
        subject.save(insertedEvent);

        subject.remove(insertedEvent);
        Collection<Event> allEvents = subject.getAll();

        assertTrue(allEvents.isEmpty());
    }

}