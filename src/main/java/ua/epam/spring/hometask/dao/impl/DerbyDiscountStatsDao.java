package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.DiscountStatsDao;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository("DerbyDiscountStatsDao")
public class DerbyDiscountStatsDao implements DiscountStatsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initDB(){
        try {
            jdbcTemplate.execute("DROP TABLE discount_stats");
        }catch (Exception ex){

        }finally {
            jdbcTemplate.execute("CREATE TABLE discount_stats (user_id int,total int)");
        }
        try {
            jdbcTemplate.execute("DROP TABLE discount_total_stats");
        }catch (Exception ex){

        }finally {
            jdbcTemplate.execute("CREATE TABLE discount_total_stats (total int)");
            jdbcTemplate.execute("INSERT INTO discount_total_stats VALUES (0)");
        }
    }

    @Override
    public int getTotalDiscounts() {
        Integer total = jdbcTemplate.queryForObject("SELECT total from discount_total_stats", (r, i) ->
                r.getInt(1)
        );
        return total;
    }

    @Override
    public void saveTotalDiscount(int total) {
        jdbcTemplate.update("UPDATE discount_total_stats SET total=?", new Object[]{total});
    }

    @Override
    public int getUserDiscounts(User user) {
        List<Integer> total = jdbcTemplate.query("SELECT total FROM discount_stats WHERE user_id=?",
                new Object[]{user.getId()}, (r, i) -> r.getInt(1));
        total.add(0);
        return total.get(0);
    }

    @Override
    public void saveUserDiscount(User user, int discounts) {
        List<Integer> totalCurrent = jdbcTemplate.query("SELECT total FROM discount_stats WHERE user_id=?",
                new Object[]{user.getId()},  (r, i) -> r.getInt(1));

        if(totalCurrent.isEmpty()){
            jdbcTemplate.update("UPDATE discount_stats SET total=? WHERE user_id=?",new Object[]{discounts,user.getId()});
        }else{
            jdbcTemplate.update("INSERT INTO discount_stats VALUES (?,?)",new Object[]{user.getId(),discounts});
        }
    }
}
