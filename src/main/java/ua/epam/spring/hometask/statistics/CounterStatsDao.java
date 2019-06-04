package ua.epam.spring.hometask.statistics;

import ua.epam.spring.hometask.domain.statistics.EventStatistics;

public interface CounterStatsDao {

    void saveStats(EventStatistics eventStatistics);

    EventStatistics getStatsForName(String name);

}
