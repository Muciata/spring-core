package ua.epam.spring.hometask.dao.impl;

import java.util.Collection;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

final class DerbyEventDaoImpl implements EventDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DerbyEventDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDB(){

        try{
            jdbcTemplate.execute("DROP TABLE event_airtimes");
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            jdbcTemplate.execute("CREATE TABLE event_airTimes( id long, airdate long, aud_name varchar(120)");
        }

        try{
            jdbcTemplate.execute("DROP TABLE events");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            jdbcTemplate.execute("CREATE TABLE events (id long, name varchar(120),  base_price float, rating varchar(10))");
        }
    }

    @Override
    public Event getByName(String name) {
        return null;
    }

    @Override
    public Event saveEvent(Event event) {
        return null;
    }

    @Override
    public void removeEvent(Event event) {

    }

    @Override
    public Event getById(Long id) {
        return null;
    }

    @Override
    public Collection<Event> getAll() {
        return null;
    }
}
