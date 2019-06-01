package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BirthdayDiscountStrategy implements DiscountService{

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        long timeDifference = ChronoUnit.DAYS.between(user.getBirthDay(), airDateTime.toLocalDate());
        return (byte)(Math.abs(timeDifference)<=5?5:0);
    }
}
