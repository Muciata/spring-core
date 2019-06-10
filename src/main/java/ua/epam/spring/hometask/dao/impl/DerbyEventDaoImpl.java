package ua.epam.spring.hometask.dao.impl;

import java.time.LocalDateTime;
import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.helpers.DateHelper;

@Repository("DerbyEventDao")
final class DerbyEventDaoImpl implements EventDao {

    private static final RowMapper<Event> EVENT_ROW_MAPPER = (r, i) -> {
        Event event = new Event();
        event.setId(r.getLong(1));
        event.setName(r.getString(2));
        event.setBasePrice(r.getFloat(3));
        event.setRating(EventRating.valueOf(r.getString(4)));
        return event;
    };
    private JdbcTemplate jdbcTemplate;

    private AuditoriumDao auditoriumDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    @Qualifier("DerbyAuditoriumDao")
    public void setAuditoriumDao(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    @PostConstruct
    public void initDB(){

        try{
            jdbcTemplate.execute("DROP TABLE event_airtimes");
        }catch(Exception ex){
        }finally {
            jdbcTemplate.execute("CREATE TABLE event_airtimes( id BIGINT , airdate BIGINT , aud_name varchar(120))");
        }

        try{
            jdbcTemplate.execute("DROP TABLE events");
        }catch (Exception ex){
        }finally {
            jdbcTemplate.execute("CREATE TABLE events (id BIGINT , name varchar(120),  base_price float, rating varchar(10))");
        }
    }

    @Override
    public Event getByName(String name) {
        List<Event> events = jdbcTemplate.query("SELECT id,name,base_price,rating FROM events WHERE name=?",
                new Object[]{name},EVENT_ROW_MAPPER);

        Optional<Event> event = events.stream().findFirst();
        event.ifPresent(this::enrichEvent);
        return event.orElse(null);
    }

    @Override
    public Event saveEvent(Event event) {
        Map<LocalDateTime, Auditorium> auditoriums = event.getAuditoriums();
        for(Map.Entry<LocalDateTime,Auditorium> entry : auditoriums.entrySet()){
            LocalDateTime airDate = entry.getKey();
            Auditorium auditorium = entry.getValue();
            jdbcTemplate.update("INSERT INTO event_airtimes VALUES (?, ?, ?)",event.getId(),
                    DateHelper.toEpochTime(airDate), auditorium.getName());
        }

        jdbcTemplate.update("INSERT INTO events VALUES (?,?,?,?)", event.getId(), event.getName(),
                event.getBasePrice(), event.getRating().toString());

        return getById(event.getId());
    }

    @Override
    public void removeEvent(Event event) {
        jdbcTemplate.update("DELETE FROM events WHERE id=?",new Object[]{event.getId()});
        jdbcTemplate.update("DELETE FROM event_airtimes WHERE id=?",new Object[]{event.getId()});
    }

    @Override
    public Event getById(Long id) {
        List<Event> events = jdbcTemplate.query("SELECT id,name,base_price,rating FROM events WHERE id=?", new Object[]{id},
                EVENT_ROW_MAPPER);

        Optional<Event> event = events.stream().findFirst();
        event.ifPresent(this::enrichEvent);
        return event.orElse(null);
    }

    @Override
    public Collection<Event> getAll() {
        List<Event> events = jdbcTemplate.query("SELECT id,name,base_price,rating FROM events ", new Object[]{},
                EVENT_ROW_MAPPER);

        events.forEach(this::enrichEvent);
        return events;
    }

    private void enrichEvent(Event event) {
        Set<Auditorium> auditoriums = auditoriumDao.getAll();
        List<Object[]> entries = jdbcTemplate.query("SELECT airdate,aud_name FROM event_airtimes WHERE id=?",
                new Object[]{event.getId()}, (r, i) -> {
                    LocalDateTime localDateTime = DateHelper.toLocalDateTime(r.getLong(1));
                    String audName = r.getString(2);
                    Auditorium auditorium = auditoriums.stream()
                            .filter(a -> a.getName().equals(audName))
                            .findFirst()
                            .get();
                    return new Object[]{localDateTime, auditorium};
                });

        NavigableMap<LocalDateTime, Auditorium> auditories = new TreeMap<>();
        entries.forEach(e->auditories.put((LocalDateTime)e[0],(Auditorium)e[1]));
        event.setAirDates(new TreeSet<>(auditories.keySet()));
        event.setAuditoriums(auditories);
    }

}
