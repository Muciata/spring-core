package ua.epam.spring.hometask.statistics;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
final class StaticDiscountStatsDao implements DiscountStatsDao {

    private int totalDiscount = 0;
    private Map<String,Integer> userDiscounts = new HashMap<>();

    @Override
    public int getTotalDiscounts() {
        return totalDiscount;
    }

    @Override
    public void saveTotalDiscount(int total) {
        totalDiscount = total;
    }

    @Override
    public int getUserDiscounts(String userName) {
        return userDiscounts.getOrDefault(userName,0);
    }

    @Override
    public void saveUserDiscount(String userName, int discounts) {
        userDiscounts.put(userName, discounts);
    }
}
