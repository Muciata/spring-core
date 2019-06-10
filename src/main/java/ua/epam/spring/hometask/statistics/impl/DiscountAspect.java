package ua.epam.spring.hometask.statistics.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dao.DiscountStatsDao;

@Aspect
@Component
final class DiscountAspect {

    private DiscountStatsDao discountStatsDao;

    @Autowired
    @Qualifier("DerbyDiscountStatsDao")
    public void setDiscountStatsDao(DiscountStatsDao discountStatsDao) {
        this.discountStatsDao = discountStatsDao;
    }

    @Before("execution(* ua.epam.spring.hometask.service.impl.DiscountServiceImpl.getDiscount(..))")
    public void logAllDiscountsAspect(JoinPoint joinPoint){
        int currentTotalDiscount = discountStatsDao.getTotalDiscounts();
        discountStatsDao.saveTotalDiscount(currentTotalDiscount + 1);
    }

    @Before("execution(* ua.epam.spring.hometask.service.impl.DiscountServiceImpl.getDiscount(..))")
    public void logUserDiscountAspect(JoinPoint joinPoint){
        User user = (User)joinPoint.getArgs()[0];
        int currentTotalDiscount = discountStatsDao.getUserDiscounts(user);
        discountStatsDao.saveUserDiscount(user,currentTotalDiscount + 1);
    }

}
