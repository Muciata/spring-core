package ua.epam.spring.hometask.statistics;

import ua.epam.spring.hometask.domain.User;

public interface DiscountStatsDao {

    int getTotalDiscounts();

    void saveTotalDiscount(int total);

    int getUserDiscounts(User user);

    void saveUserDiscount(User user, int discounts);
}
