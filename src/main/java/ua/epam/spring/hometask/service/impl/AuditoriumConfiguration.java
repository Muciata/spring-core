package ua.epam.spring.hometask.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class AuditoriumConfiguration {

    @Bean(name = "auditorium1")
    public static Auditorium createLeftAuditorium(){
        Auditorium auditorium = new Auditorium();
        auditorium.setName("Left Auditorium");
        auditorium.setNumberOfSeats(50);
        auditorium.setVipSeats(new HashSet<>(
                Arrays.asList(10L,11L,12L,13L,14L)
        ));
        return auditorium;
    }

    @Bean(name = "auditorium2")
    public static Auditorium createRightAuditorium(){
        Auditorium auditorium = new Auditorium();
        auditorium.setName("Right Auditorium");
        auditorium.setNumberOfSeats(20);
        auditorium.setVipSeats(new HashSet(
                Arrays.asList(1L,2L,3L,4L)
        ));
        return auditorium;
    }
}
