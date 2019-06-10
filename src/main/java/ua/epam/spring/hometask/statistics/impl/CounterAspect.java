package ua.epam.spring.hometask.statistics.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.statistics.EventStatistics;
import ua.epam.spring.hometask.dao.CounterStatsDao;

import java.util.Optional;
import java.util.Set;

@Aspect
@Component
public class CounterAspect {

    private CounterStatsDao counterStatsDao;

    @Autowired
    @Qualifier("DerbyCounterStatsDao")
    public void setCounterStatsDao(CounterStatsDao counterStatsDao) {
        this.counterStatsDao = counterStatsDao;
    }

    @Before(value = "execution(* ua.epam.spring.hometask.service.EventService.getByName(*))")
    public void logEventByName(JoinPoint joinPoint){
        String name = (String) joinPoint.getArgs()[0];
        EventStatistics statsForName = counterStatsDao.getStatsForName(name);
        if(statsForName==null){
            statsForName = new EventStatistics(name);
        }
        statsForName.setCallsByName(statsForName.getCallsByName()+1);
        counterStatsDao.saveStats(statsForName);
    }

    @Before(value = "execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..))")
    public void logEventByPriceCheck(JoinPoint joinPoint){
        String name = ((Event) joinPoint.getArgs()[0]).getName();
        EventStatistics statsForName = counterStatsDao.getStatsForName(name);
        if(statsForName==null){
            statsForName = new EventStatistics(name);
        }
        statsForName.setCallsByPriceCheck(statsForName.getCallsByPriceCheck()+1);
        counterStatsDao.saveStats(statsForName);
    }

    @Before(value = "execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..))")
    public void logEventByBookTickets(JoinPoint joinPoint){
        Set<Ticket> ticketList = (Set<Ticket>) joinPoint.getArgs()[0];
        Optional<Ticket> firstTicket = ticketList.stream().findFirst();
        if(firstTicket.isPresent()) {
            String name = firstTicket.get().getEvent().getName();
            EventStatistics statsForName = counterStatsDao.getStatsForName(name);
            if(statsForName==null){
                statsForName = new EventStatistics(name);
            }
            statsForName.setCallsByTicketsBooked(statsForName.getCallsByTicketsBooked() + 1);
            counterStatsDao.saveStats(statsForName);
        }
    }
}
