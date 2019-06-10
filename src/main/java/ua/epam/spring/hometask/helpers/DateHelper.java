package ua.epam.spring.hometask.helpers;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class DateHelper {
    public static LocalDateTime toLocalDateTime(long aLong)  {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(aLong),
                                TimeZone.getDefault().toZoneId());
    }
    public static LocalDate toLocalDate(long aLong)  {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(aLong),
                                TimeZone.getDefault().toZoneId()).toLocalDate();
    }

    public static long toEpochTime(LocalDateTime airDate) {
        return airDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static long toEpochTime(LocalDate date) {
        return toEpochTime(date.atStartOfDay());
    }
}
