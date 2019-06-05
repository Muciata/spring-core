package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.impl.DiscountServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceImplTest {

    @Mock
    DiscountService lowDiscountStrategy;

    @Mock
    DiscountService highDiscountStrategy;

    @Mock
    Event event;

    @Mock
    User user;

    @Test
    public void shouldReturnHigherDiscount(){
        when(lowDiscountStrategy.getDiscount(any(User.class), any(Event.class), any(LocalDateTime.class), anyLong()))
                .thenReturn((byte) 10);
        when(highDiscountStrategy.getDiscount(any(User.class), any(Event.class), any(LocalDateTime.class), anyLong()))
                .thenReturn((byte) 40);
        DiscountServiceImpl discountService = new DiscountServiceImpl(
                Arrays.asList(lowDiscountStrategy, highDiscountStrategy));

        byte discount = discountService.getDiscount(user,event,LocalDateTime.now(),0L);

        assertEquals((byte)40,discount);
    }

}