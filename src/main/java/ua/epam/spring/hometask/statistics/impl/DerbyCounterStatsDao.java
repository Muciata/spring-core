package ua.epam.spring.hometask.statistics.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.statistics.EventStatistics;
import ua.epam.spring.hometask.statistics.CounterStatsDao;

@Repository("Derby")
public final class DerbyCounterStatsDao implements CounterStatsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DerbyCounterStatsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveStats(EventStatistics eventStatistics) {
        
    }

    @Override
    public EventStatistics getStatsForName(String name) {
        return null;
    }
}
