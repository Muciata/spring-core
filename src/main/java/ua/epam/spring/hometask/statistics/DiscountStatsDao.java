package ua.epam.spring.hometask.statistics;

public interface DiscountStatsDao {

    int getTotalDiscounts();

    void saveTotalDiscount(int total);

    int getUserDiscounts(String userName);

    void saveUserDiscount(String userName, int discounts);
}
