package rentalsTests;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import tech.ronalddavis.rentals.RentalAgreement;
import tech.ronalddavis.tools.Tool;
import tech.ronalddavis.tools.ToolCode;
import tech.ronalddavis.tools.ToolType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalAgreementTests {

    @Mock
    private final RentalAgreement agreement;

    private static final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    public RentalAgreementTests() throws Exception {
        // Used to test helper methods
        Tool tool = new Tool(ToolCode.CHNS);
        int rentalDays = 1;
        int discountPercentage = 0;
        LocalDate checkoutDate = LocalDate.now();
        agreement = new RentalAgreement(tool, rentalDays, discountPercentage, checkoutDate);

        // Used to test println output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterClass
    public static void afterClass() {
        System.setOut(standardOut);
    }

    @Test
    public void currencyFormatWholeNumberTest() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(100);

        String output = Whitebox.invokeMethod(agreement, "currencyFormat", bigDecimal);

        assertEquals("$100.00", output);
    }

    @Test
    public void currencyFormatRoundingDownTest() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(100.023);

        String output = Whitebox.invokeMethod(agreement, "currencyFormat", bigDecimal);

        assertEquals("$100.02", output);
    }

    @Test
    public void currencyFormatRoundingUpTest() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(100.025);

        String output = Whitebox.invokeMethod(agreement, "currencyFormat", bigDecimal);

        assertEquals("$100.03", output);
    }

    @Test
    public void percentageFormatTest() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(.10);

        String output = Whitebox.invokeMethod(agreement, "percentageFormat", bigDecimal);

        assertEquals("10%", output);
    }

    @Test
    public void percentageFormatExtraDecimalsTest() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(.105);

        String output = Whitebox.invokeMethod(agreement, "percentageFormat", bigDecimal);

        assertEquals("10%", output);
    }

    @Test
    public void dateFormatTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 1);

        String output = Whitebox.invokeMethod(agreement, "dateFormat", localDate);

        assertEquals("07/01/21", output);
    }

    @Test
    public void getChargedDaysChainsawOneWeekHolidayTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 4);

        int output = Whitebox.invokeMethod(agreement,
                "getChargedDays", localDate, localDate.plusDays(7),
                ToolType.CHAINSAW);

        assertEquals(5, output);
    }

    @Test
    public void getChargedDaysChainsawOneWeekNoHolidayTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 9);

        int output = Whitebox.invokeMethod(agreement,
                "getChargedDays", localDate, localDate.plusDays(7),
                ToolType.CHAINSAW);

        assertEquals(5, output);
    }

    @Test
    public void getChargedDaysJackhammerOneWeekHolidayTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 4);

        int output = Whitebox.invokeMethod(agreement,
                "getChargedDays", localDate, localDate.plusDays(7),
                ToolType.JACKHAMMER);

        assertEquals(4, output);
    }

    @Test
    public void getChargedDaysJackhammerOneWeekNoHolidayTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 9);

        int output = Whitebox.invokeMethod(agreement,
                "getChargedDays", localDate, localDate.plusDays(7),
                ToolType.JACKHAMMER);

        assertEquals(5, output);
    }

    @Test
    public void getChargedDaysLadderOneWeekHolidayTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 4);

        int output = Whitebox.invokeMethod(agreement,
                "getChargedDays", localDate, localDate.plusDays(7),
                ToolType.LADDER);

        assertEquals(6, output);
    }

    @Test
    public void getChargedDaysLadderOneWeekNoHolidayTest() throws Exception {
        LocalDate localDate = LocalDate.of(2021, 7, 9);

        int output = Whitebox.invokeMethod(agreement,
                "getChargedDays", localDate, localDate.plusDays(7),
                ToolType.LADDER);

        assertEquals(7, output);
    }

    @Test
    public void printReportToolIsInvalidTest() throws Exception {
        Tool tool = new Tool(ToolCode.CHNS);
        tool.setToolCode(null);
        int rentalDays = 1;
        int discountPercentage = 0;
        LocalDate checkoutDate = LocalDate.now();

        Exception exception = assertThrows( Exception.class, () -> {
            new RentalAgreement(tool, rentalDays, discountPercentage, checkoutDate);
        });

        assertTrue(exception.getMessage().contains("All tool fields must be non null"));
    }

    @Test
    public void printReportTest() throws Exception {
        RentalAgreement rentalAgreement;
        Tool tool = new Tool(ToolCode.CHNS);
        int rentalDays = 1;
        int discountPercentage = 0;
        LocalDate checkoutDate = LocalDate.of(2023, 7,17);
        rentalAgreement = new RentalAgreement(tool, rentalDays, discountPercentage, checkoutDate);
        rentalAgreement.printReport();

        String expectedOutput = "Tool code: CHNS\nTool type: Chainsaw\nTool brand: Stihl\nCheckout date: 07/17/23\n" +
        "Due date: 07/18/23\nNumber of total rental days: 1\nNumber of days charged: 1\nDaily charge: $1.49\n" +
        "Charge before discount: $1.49\nDiscount percentage: 0%\nDiscount amount: $0.00\nActual charges: $1.49";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }
}
