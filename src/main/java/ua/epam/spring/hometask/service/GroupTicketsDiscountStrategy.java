package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class GroupTicketsDiscountStrategy implements DiscountService {

    private static final int DISCOUNT_PER_TICKETS = 10;

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        double unpaidTickets = numberOfTickets / (double)DISCOUNT_PER_TICKETS /2.0;

        return (byte)(100*unpaidTickets/numberOfTickets);
    }

}
