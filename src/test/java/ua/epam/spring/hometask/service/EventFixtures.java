package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

import java.time.LocalDateTime;
import java.util.*;

import static ua.epam.spring.hometask.service.AuditoriumFixtures.createAuditorium;

public class EventFixtures {

    public static final Event createEvent(){
        Event event = new Event();
        event.setId(1L);
        event.setBasePrice(20.0);
        event.setName("Hamlet");
        event.setRating(EventRating.MID);
        LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0);
        event.setAirDates(new TreeSet<>(
                Collections.singletonList(
                        airTime)));
        event.setAuditoriums(new TreeMap<>(Collections.singletonMap(airTime,createAuditorium())));
        return event;
    }

    public static final Collection<Event> createEvents(int no){
        Collection<Event> eventGroup = new ArrayList<>();
        for(long id=1;id<=no;id++) {
            Event event = new Event();
            event.setId(id);
            event.setBasePrice(20.0);
            event.setName("Hamlet"+no);
            event.setRating(EventRating.MID);
            LocalDateTime airTime = LocalDateTime.of(2019, 10, 12, 19, 0).plusDays(id);
            event.setAirDates(new TreeSet<>(
                    Collections.singletonList(
                            airTime)));
            event.setAuditoriums(new TreeMap<>(Collections.singletonMap(airTime, createAuditorium())));
            eventGroup.add(event);
        }
        return eventGroup;
    }
}
