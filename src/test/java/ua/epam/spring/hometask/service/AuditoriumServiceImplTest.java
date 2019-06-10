package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.impl.AuditoriumServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static ua.epam.spring.hometask.service.AuditoriumFixtures.createAuditorium;

@RunWith(MockitoJUnitRunner.class)
public class AuditoriumServiceImplTest {

    @Mock
    private AuditoriumDao auditoriumDao;


    @Test
    public void shouldReturnAuditoriumByName(){
        when(auditoriumDao.getAll()).thenReturn(Collections.singleton(AuditoriumFixtures.createAuditorium()));

        AuditoriumServiceImpl auditoriumService = new AuditoriumServiceImpl();
        auditoriumService.setAuditoriumDao(auditoriumDao);

        Auditorium auditorium = auditoriumService.getByName("1. Auditorium");

        assertEquals( "1. Auditorium", auditorium.getName());
        assertEquals(250L, auditorium.getNumberOfSeats());
        assertEquals(5,auditorium.getVipSeats().size());
    }


    @Test
    public void shouldReturnAllAuditoriums(){
        Set<Auditorium> auditoriums = new HashSet<>(Arrays.asList(createAuditorium("Aud1"),
                createAuditorium("Aud2")));
        when(auditoriumDao.getAll()).thenReturn(auditoriums);

        AuditoriumServiceImpl auditoriumService = new AuditoriumServiceImpl();
        auditoriumService.setAuditoriumDao(auditoriumDao);

        Set<Auditorium> recievedAuditoriums = auditoriumService.getAll();

        assertEquals(2,recievedAuditoriums.size());
    }
}