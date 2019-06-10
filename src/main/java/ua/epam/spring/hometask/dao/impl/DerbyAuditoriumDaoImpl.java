package ua.epam.spring.hometask.dao.impl;

import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

final class DerbyAuditoriumDaoImpl implements AuditoriumDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DerbyAuditoriumDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDB(){
        try{
            jdbcTemplate.execute("DROP TABLE auditorium");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            jdbcTemplate.execute("CREATE TABLE auditorium (name varchar(120), seats int, vipSeats string");
        }

    }

    @Override
    public Set<Auditorium> getAll() {

        return null;
    }
}
