package ua.epam.spring.hometask.dao.impl;

import static ua.epam.spring.hometask.helpers.AuditoriumHelper.seatsToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

final class DerbyAuditoriumDaoImpl implements AuditoriumDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DerbyAuditoriumDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDB(){
        try{
            jdbcTemplate.execute("DROP TABLE auditoriums");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            jdbcTemplate.execute("CREATE TABLE auditoriums (name varchar(120), number_of_seats int, vip_seats varchar(255))");
        }
    }

    @Override
    public Set<Auditorium> getAll() {
        List<Auditorium> auditoriums = jdbcTemplate
                .query("SELECT name, number_of_seats, vip_seats from auditoriums", (r, index) -> {
                    Auditorium auditorium = new Auditorium();
                    auditorium.setName(r.getString(1));
                    auditorium.setNumberOfSeats(r.getInt(2));
                    auditorium.setVipSeats(seatsToString(r.getString(3)));
                    return auditorium;
                });
        return new HashSet<>(auditoriums);
    }

    @Override
    public void addAuditorium(Auditorium auditorium) {
        StringJoiner seatJoiner = new StringJoiner(",");
        auditorium.getVipSeats().stream().map(s->String.valueOf(s)).forEach(seatJoiner::add);
        jdbcTemplate.update("INSERT INTO auditoriums VALUES (?,?,?)", auditorium.getName(),
                auditorium.getNumberOfSeats(), seatJoiner.toString());

    }
}
