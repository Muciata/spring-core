package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ua.epam.spring.hometask.domain.Auditorium;

import ua.epam.spring.hometask.helpers.AuditoriumHelper;

@Configuration
@PropertySource({"classpath:application.properties"})
public class AuditoriumConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "auditorium1")
    public  Auditorium createLeftAuditorium(){
        Auditorium auditorium = new Auditorium();
        auditorium.setName(env.getProperty("auditorium1.name"));
        auditorium.setNumberOfSeats(Long.parseLong(env.getProperty("auditorium1.seats")));
        auditorium.setVipSeats(
                AuditoriumHelper.seatsToString(env.getProperty("auditorium1.vipSeats")));
        return auditorium;
    }

    @Bean(name = "auditorium2")
    public  Auditorium createRightAuditorium(){
        Auditorium auditorium = new Auditorium();
        auditorium.setName(env.getProperty("auditorium2.name"));
        auditorium.setNumberOfSeats(Long.parseLong(env.getProperty("auditorium2.seats")));
        auditorium.setVipSeats(
                AuditoriumHelper.seatsToString(env.getProperty("auditorium2.vipSeats")));
        return auditorium;
    }
}
