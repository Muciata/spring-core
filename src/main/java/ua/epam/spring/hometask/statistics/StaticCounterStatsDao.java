package ua.epam.spring.hometask.statistics;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.statistics.EventStatistics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository("StaticCounterStatsDao")
public class StaticCounterStatsDao implements CounterStatsDao{

    private static Map<String,EventStatistics> statsByName = new HashMap<>();

    @Override
    public void saveStats(EventStatistics eventStatistics) {
        statsByName.put(eventStatistics.getName(), eventStatistics);
    }

    @Override
    public EventStatistics getStatsForName(String name) {
        return statsByName.getOrDefault(name, new EventStatistics(name));
    }
}
