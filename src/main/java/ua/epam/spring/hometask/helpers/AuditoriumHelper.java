package ua.epam.spring.hometask.helpers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class AuditoriumHelper {

    public static Set<Long> seatsToString(String s) {
        return Arrays.stream(s
                .split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }


}
