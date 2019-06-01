package ua.epam.spring.hometask.service;

import org.junit.Test;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.service.AuditoriumFixtures.createAuditorium;

public class AuditoriumServiceImplTest {

    @Test
    public void shouldReturnAuditoriumByName(){
        AuditoriumService auditoriumService = new AuditoriumServiceImpl(Collections.singleton(createAuditorium()));

        Auditorium auditorium = auditoriumService.getByName("1. Auditorium");

        assertEquals( "1. Auditorium", auditorium.getName());
        assertEquals(250L, auditorium.getNumberOfSeats());
        assertEquals(5,auditorium.getVipSeats().size());
    }


    @Test
    public void shouldReturnAllAuditoriums(){
        Set<Auditorium> auditoriums = new HashSet<>();
        auditoriums.add(createAuditorium("Aud1"));
        auditoriums.add(createAuditorium("Aud2"));
        AuditoriumService auditoriumService = new AuditoriumServiceImpl(auditoriums);

        Set<Auditorium> recievedAuditoriums = auditoriumService.getAll();

        assertEquals(2,recievedAuditoriums.size());
    }
}