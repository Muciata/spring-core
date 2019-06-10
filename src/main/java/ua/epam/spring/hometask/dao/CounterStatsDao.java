package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.statistics.EventStatistics;

public interface CounterStatsDao {

    void saveStats(EventStatistics eventStatistics);

    EventStatistics getStatsForName(String name);

}
