package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class AuditoriumFixtures {

    public static final Auditorium createAuditorium(){
        Auditorium auditorium = new Auditorium();
        auditorium.setNumberOfSeats(250L);
        auditorium.setVipSeats(LongStream.rangeClosed(10,14).mapToObj(t->t).collect(Collectors.toSet()));
        auditorium.setName("1. Auditorium");
        return auditorium;
    }

    public static final Auditorium createAuditorium(String name){
        Auditorium auditorium = new Auditorium();
        auditorium.setNumberOfSeats(250L);
        auditorium.setVipSeats(LongStream.rangeClosed(10,14).mapToObj(t->t).collect(Collectors.toSet()));
        auditorium.setName(name);
        return auditorium;
    }
}
