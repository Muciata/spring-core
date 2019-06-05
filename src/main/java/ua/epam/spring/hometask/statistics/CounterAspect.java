package ua.epam.spring.hometask.statistics;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.statistics.EventStatistics;

import java.util.Optional;
import java.util.Set;

@Aspect
@Component
public class CounterAspect {

    private CounterStatsDao counterStatsDao;

    @Autowired
    public CounterAspect(CounterStatsDao counterStatsDao) {
        this.counterStatsDao = counterStatsDao;
    }

    @Before(value = "execution(* ua.epam.spring.hometask.service.EventService.getByName(*))")
    public void logEventByName(JoinPoint joinPoint){
        String name = (String) joinPoint.getArgs()[0];
        EventStatistics statsForName = counterStatsDao.getStatsForName(name);
        statsForName.setCallsByName(statsForName.getCallsByName()+1);
        counterStatsDao.saveStats(statsForName);
    }

    @Before(value = "execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..))")
    public void logEventByPriceCheck(JoinPoint joinPoint){
        String name = ((Event) joinPoint.getArgs()[0]).getName();
        EventStatistics statsForName = counterStatsDao.getStatsForName(name);
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
            statsForName.setCallsByTicketsBooked(statsForName.getCallsByTicketsBooked() + 1);
            counterStatsDao.saveStats(statsForName);
        }
    }
}
