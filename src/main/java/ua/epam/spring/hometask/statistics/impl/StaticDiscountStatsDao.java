package ua.epam.spring.hometask.statistics.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.statistics.DiscountStatsDao;

@Repository
final class StaticDiscountStatsDao implements DiscountStatsDao {

    private int totalDiscount = 0;
    private Map<User,Integer> userDiscounts = new HashMap<>();

    @Override
    public int getTotalDiscounts() {
        return totalDiscount;
    }

    @Override
    public void saveTotalDiscount(int total) {
        totalDiscount = total;
    }

    @Override
    public int getUserDiscounts(User userName) {
        return userDiscounts.getOrDefault(userName,0);
    }

    @Override
    public void saveUserDiscount(User userName, int discounts) {
        userDiscounts.put(userName, discounts);
    }
}
