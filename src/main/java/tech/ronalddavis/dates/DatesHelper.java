package tech.ronalddavis.dates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatesHelper {

    /*
     * Returns the number of weekends within the given dates
     * Start date exclusive and end date inclusive
     */
    public static int findWeekends(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.plusDays(1).datesUntil(endDate.plusDays(1))
                .filter(t -> (t.getDayOfWeek() == DayOfWeek.SATURDAY || t.getDayOfWeek() == DayOfWeek.SUNDAY))
                .count();
    }

    /*
     * Returns the number of weekdays within the given dates
     * Start date exclusive and end date inclusive
     */
    public static int findWeekdays(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.plusDays(1).datesUntil(endDate.plusDays(1))
                .filter(t -> (t.getDayOfWeek() != DayOfWeek.SATURDAY && t.getDayOfWeek() != DayOfWeek.SUNDAY))
                .count();
    }

    /*
     * Returns if Independence Day falls within the given range
     * Start date exclusive and end date inclusive
     * Assumes that only one Independence Day is present
     */
    public static boolean fallsOnIndependenceDay(LocalDate startDate, LocalDate endDate) throws Exception{

        if (startDate.isBefore(endDate)) {
            return startDate.plusDays(1).datesUntil(endDate.plusDays(1))
                    .filter(t -> Objects.requireNonNull(getIndependenceDays(startDate.getYear(), endDate.getYear())).contains(t))
                    .count() >= 1;
        }
        else
        {
            throw new Exception("Start date is after end date.");
        }
    }

    /*
     * Returns if Labor Day falls within the given range
     * Start date exclusive and end date inclusive
     * Assumes that only one Labor Day is present
     */
    public static boolean fallsOnLaborDay(LocalDate startDate, LocalDate endDate) throws Exception{

        if (startDate.isBefore(endDate)) {
            return startDate.plusDays(1).datesUntil(endDate.plusDays(1))
                    .filter(t -> Objects.requireNonNull(getLaborDays(startDate.getYear(), endDate.getYear())).contains(t))
                    .count() >= 1;
        }
        else
    {
        throw new Exception("Start date is after end date.");
    }
    }

    /*
     * Returns a list of zero or more LocalDate items referring to the observed Independence Days within the given years
     * Observed Independence Day is defined as July 4th, if it falls on a weekday, or if July 4th falls on a weekend,
     * it is observed on the closest weekday
     */
    private static List<LocalDate> getIndependenceDays(int startDate, int endDate) {
        if (startDate < 0 || endDate < 0 || startDate > endDate) {
            return null;
        }
        final int observedDay = 4;
        final int holidaysNeeded = 1 + (endDate - startDate);
        List<LocalDate> dates = new ArrayList<>(holidaysNeeded);

        for (int i = 0; i < holidaysNeeded; i++) {
            LocalDate independenceDay = LocalDate.of(i + startDate, Month.JULY, observedDay);

            switch (DayOfWeek.of(independenceDay.get(ChronoField.DAY_OF_WEEK))) {
                case SATURDAY:
                    dates.add(LocalDate.of(i + startDate, Month.JULY, observedDay - 1));
                    break;
                case SUNDAY:
                    dates.add(LocalDate.of(i + startDate, Month.JULY, observedDay + 1));
                    break;
                default:
                    dates.add(LocalDate.of(i + startDate, Month.JULY, observedDay));
                    break;
            }
        }
        return dates;
    }

    /*
     * Returns a list of zero or more LocalDate items referring to the observed Labor Days within the given years
     * Observed Labor Day is defined as the first Monday in September
     */
    private static List<LocalDate> getLaborDays(int startDate, int endDate) {
        if (startDate < 0 || endDate < 0 || startDate > endDate) {
            return null;
        }

        final int holidaysNeeded = 1 + (endDate - startDate);
        List<LocalDate> dates = new ArrayList<>(holidaysNeeded);

        for (int i = 0; i < holidaysNeeded; i++) {
            // Start at the first of the month and find the first Monday
            LocalDate beforeLaborDay = LocalDate.of(i + startDate, Month.AUGUST, 31);
            dates.add(beforeLaborDay.datesUntil(beforeLaborDay.plusDays(7))
                     .filter(t -> t.getDayOfWeek() == DayOfWeek.MONDAY)
                     .findFirst().orElse(null));
        }

        return dates;
    }
}
