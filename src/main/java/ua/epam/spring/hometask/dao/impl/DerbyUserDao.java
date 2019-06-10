package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.helpers.DateHelper;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.helpers.DateHelper.toEpochTime;
import static ua.epam.spring.hometask.helpers.DateHelper.toLocalDate;

@Repository("DerbyUserDao")
public class DerbyUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private TicketDao ticketDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    @Qualifier("DerbyTicketDao")
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @PostConstruct
    public void initDB(){
        try{
            jdbcTemplate.execute("DROP TABLE users");
        }catch (Exception ex){

        }finally {
            jdbcTemplate.execute("CREATE TABLE users (id bigint, first_name varchar(120), last_name varchar (120)," +
                    "email varchar(120), birthday bigint)");
        }
    }

    @Override
    public Collection<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT id, first_name, last_name, email, birthday FROM users",
                (r, i) -> {
                    User user = new User();
                    user.setId(r.getLong(1));
                    user.setFirstName(r.getString(2));
                    user.setLastName(r.getString(3));
                    user.setEmail(r.getString(4));
                    user.setBirthDay(toLocalDate(r.getLong(5)));
                    return user;
                });
        users.forEach(this::enrichUser);
        return users;
    }

    private void enrichUser(User user) {
        Set<Ticket> tickets = ticketDao.getAll().stream()
                .filter(t -> t.getUser().getId() == user.getId())
                .collect(Collectors.toSet());
        user.setTickets(new TreeSet(tickets));
    }

    @Override
    public User getById(long id) {
        List<User> users = jdbcTemplate.query("SELECT id, first_name, last_name, email, birthday FROM users WHERE id=?",
                new Object[]{id},
                (r, i) -> {
                    User user = new User();
                    user.setId(r.getLong(1));
                    user.setFirstName(r.getString(2));
                    user.setLastName(r.getString(3));
                    user.setEmail(r.getString(4));
                    user.setBirthDay(toLocalDate(r.getLong(5)));
                    return user;
                });
        users.forEach(this::enrichUser);
        if(!users.isEmpty()) {
            return users.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT id, first_name, last_name, email, birthday FROM users WHERE email=?",
                new Object[]{email},
                (r, i) -> {
                    User user = new User();
                    user.setId(r.getLong(1));
                    user.setFirstName(r.getString(2));
                    user.setLastName(r.getString(3));
                    user.setEmail(r.getString(4));
                    user.setBirthDay(toLocalDate(r.getLong(5)));
                    return user;
                });
        users.forEach(this::enrichUser);
        return  users.get(0);
    }

    @Override
    public User saveUser(User user) {
        jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?)",new Object[]{
           user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(), toEpochTime(user.getBirthDay())
        });
        return getById(user.getId());
    }

    @Override
    public void removeUser(User user) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", user.getId());
    }
}
