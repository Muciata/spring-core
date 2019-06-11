package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.helpers.DateHelper.toEpochTime;
import static ua.epam.spring.hometask.helpers.DateHelper.toLocalDateTime;

@Repository("DerbyTicketDao")
public class DerbyTicketDaoImpl implements TicketDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private EventDao eventDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    @Qualifier("DerbyUserDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("DerbyEventDao")
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @PostConstruct
    public void initDB(){
        try{
            jdbcTemplate.execute("DROP TABLE tickets");
        }catch(Exception ex){

        }finally {
            jdbcTemplate.execute("CREATE TABLE tickets(user_id bigint, event_name varchar(120), airtime bigint, seat bigint)");
        }
    }


    @Override
    public void addAll(Set<Ticket> tickets) {
        jdbcTemplate.batchUpdate("INSERT INTO tickets VALUES (?,?,?,?) ",
                tickets.stream().map(t->new Object[]{ t.getUser().getId(), t.getEvent().getName(),
                        toEpochTime(t.getDateTime()), t.getSeat()})
                        .collect(Collectors.toList()) );
    }

    @Override
    public Collection<Ticket> getAll() {
        List<Object[]> data = jdbcTemplate.query("SELECT user_id,event_name, airtime, seat FROM tickets", (r, i) -> {

            return new Object[]{r.getInt(1), r.getString(2), r.getLong(3), r.getInt(4)};
        });
        List<Ticket> tickets = data.stream().map(t -> {
            User user = userDao.getById((int) t[0]);
            Event event = eventDao.getByName((String) t[1]);
            LocalDateTime airTime = toLocalDateTime((long) t[2]);
            return new Ticket(user, event, airTime, (int) t[3]);
        }).collect(Collectors.toList());

        return tickets;
    }

    @Override
    public Collection<Ticket> getAllWithoutUser() {
        List<Ticket> data = jdbcTemplate.query("SELECT user_id,event_name, airtime, seat FROM tickets", (r, i) -> {
            User user = new User();
            user.setId(r.getLong(1));
            Event event = eventDao.getByName(r.getString(2));
            LocalDateTime airTime = toLocalDateTime(r.getLong(3));
            return new Ticket(user, event, airTime,r.getInt(4));
        });
        return data;
    }
}
