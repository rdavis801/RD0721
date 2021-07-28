package datesTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.ronalddavis.dates.DatesHelper;

import java.time.LocalDate;
import java.util.List;
import org.powermock.reflect.Whitebox;

import static org.junit.jupiter.api.Assertions.*;

public class DatesHelperTests {

    // Even though every function is static, this need to be declared to test private methods
    @Mock
    private final DatesHelper datesHelper = new DatesHelper();

    @Test
    public void testFindWeekendsOneWeek() {
        LocalDate startDate = LocalDate.of(2021, 7, 1);

        assertEquals(2, DatesHelper.findWeekends(startDate, startDate.plusDays(7)));
    }

    @Test
    public void testFindWeekendsNoWeeks() {
        LocalDate startDate = LocalDate.of(2021, 7, 1);

        assertEquals(0, DatesHelper.findWeekends(startDate, startDate));
    }

    @Test
    public void testFindWeekendsOnlyWeekdays() {
        LocalDate startDate = LocalDate.of(2021, 7, 4);

        assertEquals(0, DatesHelper.findWeekends(startDate, startDate.plusDays(5)));
    }

    @Test
    public void testFindWeekendsPartialWeek() {
        LocalDate startDate = LocalDate.of(2021, 7, 1);

        assertEquals(1, DatesHelper.findWeekends(startDate, startDate.plusDays(2)));
    }

    @Test
    public void testFindWeekdaysOneWeek() {
        LocalDate startDate = LocalDate.of(2021, 7, 1);

        assertEquals(5, DatesHelper.findWeekdays(startDate, startDate.plusDays(7)));
    }

    @Test
    public void testFindWeekdaysNoWeeks() {
        LocalDate startDate = LocalDate.of(2021, 7, 1);

        assertEquals(0, DatesHelper.findWeekdays(startDate, startDate));
    }

    @Test
    public void testFindWeekdaysOnlyWeekends() {
        LocalDate startDate = LocalDate.of(2021, 7, 2);

        assertEquals(0, DatesHelper.findWeekdays(startDate, startDate.plusDays(2)));
    }

    @Test
    public void testFindWeekdaysPartialWeek() {
        LocalDate startDate = LocalDate.of(2021, 7, 4);

        assertEquals(3, DatesHelper.findWeekdays(startDate, startDate.plusDays(3)));
    }

    @Test
    public void testGetIndependenceDaysOneYear() throws Exception {
        int year = 2021;
        List<LocalDate> ls = Whitebox.invokeMethod(datesHelper, "getIndependenceDays", year, year);
        assertEquals(LocalDate.of(2021, 7, 5), ls.get(0));
    }

    @Test
    public void testGetIndependenceDaysTwoYears() throws Exception {
        int year = 2021;
        List<LocalDate> ls = Whitebox.invokeMethod(datesHelper, "getIndependenceDays", year, year + 1);

        assertEquals(2, ls.size());
        assertEquals(LocalDate.of(2021, 7, 5), ls.get(0));
        assertEquals(LocalDate.of(2022, 7, 4), ls.get(1));
    }

    @Test
    public void testGetIndependenceDaysBadYears() throws Exception {
        int year = 2021;
        List<LocalDate> ls = Whitebox.invokeMethod(datesHelper, "getIndependenceDays", year, year - 1);
        assertNull(ls);
    }

    @Test
    public void testFallsOnIndependenceDayTrue() throws Exception {
        LocalDate startDate = LocalDate.of(2021, 7, 3);

        assertTrue(DatesHelper.fallsOnIndependenceDay(startDate, startDate.plusDays(2)));
    }

    @Test
    public void testFallsOnIndependenceDayFalse() throws Exception {
        LocalDate startDate = LocalDate.of(2021, 7, 9);

        assertFalse(DatesHelper.fallsOnIndependenceDay(startDate, startDate.plusDays(2)));
    }

    @Test
    public void testFallsOnIndependenceDayBadYears() {
        LocalDate startDate = LocalDate.of(2021, 7, 3);

        Exception exception = assertThrows( Exception.class, () -> {
            DatesHelper.fallsOnIndependenceDay(startDate, startDate.minusYears(1));
        });

        assertTrue(exception.getMessage().contains("Start date is after end date."));
    }

    @Test
    public void testGetLaborDaysOneYear() {
        int year = 2021;
        try {
            List<LocalDate> ls = Whitebox.invokeMethod(datesHelper, "getLaborDays", year, year);
            assertEquals(LocalDate.of(2021, 9, 6), ls.get(0));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLaborDaysTwoYears() {
        int year = 2021;
        try {
            List<LocalDate> ls = Whitebox.invokeMethod(datesHelper, "getLaborDays", 2021, 2022);

            assertEquals(2, ls.size());
            assertEquals(LocalDate.of(2021, 9, 6), ls.get(0));
            assertEquals(LocalDate.of(2022, 9, 5), ls.get(1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetLaborDaysBadYears() {
        int year = 2021;
        try {
            List<LocalDate> ls = Whitebox.invokeMethod(datesHelper, "getLaborDays", 2021, 2020);
            assertNull(ls);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFallsOnLaborDayTrue() throws Exception {
        LocalDate startDate = LocalDate.of(2021, 9, 4);

        assertTrue(DatesHelper.fallsOnLaborDay(startDate, startDate.plusDays(3)));
    }

    @Test
    public void testFallsOnLaborDayFalse() throws Exception {
        LocalDate startDate = LocalDate.of(2021, 9, 9);

        assertFalse(DatesHelper.fallsOnLaborDay(startDate, startDate.plusDays(2)));
    }

    @Test
    public void testFallsOnLaborDayBadYears() {
        LocalDate startDate = LocalDate.of(2021, 7, 3);

        Exception exception = assertThrows( Exception.class, () -> {
            DatesHelper.fallsOnIndependenceDay(startDate, startDate.minusYears(1));
        });

        assertTrue(exception.getMessage().contains("Start date is after end date."));
    }
}
