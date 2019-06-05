package ua.epam.spring.hometask.service.impl;

import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Service("GroupTicketsDiscountStrategy")
public class GroupTicketsDiscountStrategy implements DiscountService {

    private static final int DISCOUNT_PER_TICKETS = 10;

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        double unpaidTickets = numberOfTickets / (double) DISCOUNT_PER_TICKETS / 2.0;

        return (byte) (100 * unpaidTickets / numberOfTickets);
    }

}
