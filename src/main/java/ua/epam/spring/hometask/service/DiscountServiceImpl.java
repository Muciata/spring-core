package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private List<DiscountService> discountStrategies;

    public DiscountServiceImpl(List<DiscountService> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return (byte)discountStrategies.stream()
                .mapToInt(strategy -> strategy.getDiscount(user, event, airDateTime, numberOfTickets))
                .max()
                .orElse(0);
    }
}
