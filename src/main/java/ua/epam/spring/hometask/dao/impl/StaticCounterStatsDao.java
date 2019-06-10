package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.statistics.EventStatistics;
import ua.epam.spring.hometask.dao.CounterStatsDao;

import java.util.HashMap;
import java.util.Map;

@Repository("StaticCounterStatsDao")
public class StaticCounterStatsDao implements CounterStatsDao {

    private Map<String,EventStatistics> statsByName = new HashMap<>();

    @Override
    public void saveStats(EventStatistics eventStatistics) {
        statsByName.put(eventStatistics.getName(), eventStatistics);
    }

    @Override
    public EventStatistics getStatsForName(String name) {
        return statsByName.getOrDefault(name, new EventStatistics(name));
    }
}
