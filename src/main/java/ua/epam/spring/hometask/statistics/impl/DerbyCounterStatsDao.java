package ua.epam.spring.hometask.statistics.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.statistics.EventStatistics;
import ua.epam.spring.hometask.statistics.CounterStatsDao;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("Derby")
public final class DerbyCounterStatsDao implements CounterStatsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DerbyCounterStatsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDB(){
        try{
            jdbcTemplate.execute("DROP TABLE counterstats");
        }finally {
            jdbcTemplate.execute("CREATE TABLE counterstats (name VARCHAR(100),callsByName int,callsByPriceCheck int,callsByTicketsBooked int)");
        }
    }

    @Override
    public void saveStats(EventStatistics eventStatistics) {
        jdbcTemplate.update("DELETE FROM counterstats WHERE name = ?", eventStatistics.getName());

        jdbcTemplate.update("INSERT INTO counterstats VALUES (?,?,?,?)",
                eventStatistics.getName(),eventStatistics.getCallsByName(),
                eventStatistics.getCallsByPriceCheck(),eventStatistics.getCallsByTicketsBooked());

        int count = jdbcTemplate.queryForObject("SELECT count(*) FROM counterstats",
                 (ResultSet resultSet, int i) -> resultSet.getInt(1)
                );


    }

    @Override
    public EventStatistics getStatsForName(String name) {
        List<EventStatistics> eventStatistics = jdbcTemplate.query("SELECT name,callsByName,callsByPriceCheck,callsByTicketsBooked FROM counterstats WHERE name=?",
                new Object[]{name},
                new RowMapper<EventStatistics>() {
                    @Override
                    public EventStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
                        EventStatistics es = new EventStatistics(resultSet.getString(1));
                        es.setCallsByName(resultSet.getInt(2));
                        es.setCallsByPriceCheck(resultSet.getInt(3));
                        es.setCallsByTicketsBooked(resultSet.getInt(4));
                        return es;
                    }
                });
        return eventStatistics.stream().findFirst().orElse(null);
    }
}
