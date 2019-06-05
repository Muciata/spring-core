package ua.epam.spring.hometask.statistics;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
final class DiscountAspect {

    private DiscountStatsDao discountStatsDao;

    @Autowired
    public DiscountAspect(DiscountStatsDao discountStatsDao) {
        this.discountStatsDao = discountStatsDao;
    }

    @Before("execution(* ua.epam.spring.hometask.service.DiscountService.getDiscount(..)")
    public void logAllDiscountsAspect(JoinPoint joinPoint){
        System.out.println("DiscountAspect");
        int currentTotalDiscount = discountStatsDao.getTotalDiscounts();
        discountStatsDao.saveTotalDiscount(currentTotalDiscount + 1);
    }

    @Before("execution(* ua.epam.spring.hometask.service.DiscountService.getDiscount(..)")
    public void logUserDiscountAspect(JoinPoint joinPoint){
        System.out.println("DiscountAspect");
        String userName = "";
        int currentTotalDiscount = discountStatsDao.getUserDiscounts(userName);
        discountStatsDao.saveUserDiscount(userName,currentTotalDiscount + 1);
    }

}
