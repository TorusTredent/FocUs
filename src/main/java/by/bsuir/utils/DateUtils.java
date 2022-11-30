package by.bsuir.utils;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static java.util.Calendar.DECEMBER;

@Component
public class DateUtils {

    public static LocalDateTime getStartOfCurrentWeek(LocalDateTime date) {
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        return date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    }

    public static LocalDateTime getEndOfWeek(LocalDateTime date) {
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        DayOfWeek lastDayOfWeek = firstDayOfWeek.plus(6);
        return date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
    }

    public static LocalDateTime getStartOfMonth(LocalDateTime date) {
        YearMonth month = YearMonth.from(date);
        return month.atDay(1).atStartOfDay();
    }

    public static LocalDateTime getEndOfMonth(LocalDateTime date) {
        YearMonth month = YearMonth.from(date);
        return month.atEndOfMonth().atTime(23, 59);
    }

    public static LocalDateTime getStartOfYear(LocalDateTime date) {
        Year year = Year.from(date);
        return year.atDay(1).atStartOfDay();
    }

    public static LocalDateTime getEndOfYear(LocalDateTime date) {
        Year year = Year.from(date);
        return year.atMonth(DECEMBER).atEndOfMonth().atTime(23, 59);
    }
}
