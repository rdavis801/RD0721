package rentalsTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import tech.ronalddavis.rentals.Checkout;
import tech.ronalddavis.rentals.RentalAgreement;
import tech.ronalddavis.tools.Tool;
import tech.ronalddavis.tools.ToolCode;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTests {

    @Mock
    Checkout checkout = new Checkout();

    @Test
    public void getNumberTest() throws Exception {
        String message = "";
        String errorMessage = "";
        String data = "5";

        Scanner scanner = mockUserInput(data);

        int output = Whitebox.invokeMethod(checkout, "getNumber", scanner, message, errorMessage);
        assertEquals(5, output);
    }

    @Test
    public void getNumberInvalidTest() {
        String message = "";
        String errorMessage = "n is not a number";
        String data = "n";

        Scanner scanner = mockUserInput(data);
        Exception exception = assertThrows( Exception.class, () -> {
            Whitebox.invokeMethod(checkout, "getNumber", scanner, message, errorMessage);
        });

        assertEquals(exception.getMessage(), errorMessage + " Good bye.");
    }

    @Test
    public void getToolTest() throws Exception {
        String data = "CHNS";
        Tool tool = new Tool(ToolCode.CHNS);

        Scanner scanner = mockUserInput(data);
        Tool output = Whitebox.invokeMethod(checkout, "getTool", scanner);

        assertEquals(tool, output);
    }

    @Test
    public void getToolInvalidTest() {
        String data = "n\rn\rn\rn\r";

        Scanner scanner = mockUserInput(data);
        Exception exception = assertThrows( Exception.class, () -> {
            Whitebox.invokeMethod(checkout, "getTool", scanner);
        });

        assertTrue(exception.getMessage().contains("The tool code is invalid. Good bye."));
    }

    @Test
    public void getCheckoutDateTest() throws Exception {
        String data = "07/01/21";
        LocalDate expected = LocalDate.of(2021,7,1);

        Scanner scanner = mockUserInput(data);
        LocalDate output = Whitebox.invokeMethod(checkout, "getCheckoutDate", scanner);

        assertEquals(expected, output);
    }

    @Test
    public void getCheckoutDateInvalidTest() {
        String data = "n\r" +
                      "n\r" +
                      "n\r" +
                      "n\r";

        Scanner scanner = mockUserInput(data);
        Exception exception = assertThrows( Exception.class, () -> {
            Whitebox.invokeMethod(checkout, "getCheckoutDate", scanner);
        });

        assertTrue(exception.getMessage().contains("The checkout date is invalid. Good bye."));
    }

    @Test
    public void parseLocalDateTest() throws Exception {
        String data = "07/01/21";
        LocalDate expected = LocalDate.of(2021, 7, 1);

        LocalDate output = Whitebox.invokeMethod(checkout, "parseLocalDate", data);

        assertEquals(expected, output);
    }

    @Test
    public void parseLocalDateInvalidTest() throws Exception {
        String data = "07";

        LocalDate output = Whitebox.invokeMethod(checkout, "parseLocalDate", data);

        assertNull(output);
    }

    @Test
    public void numberValidTest() throws Exception {
        int checked = 5;
        int max = 365;
        int min = 1;

        assertNull(Whitebox.invokeMethod(checkout, "numberValid", checked, min, max));
    }

    @Test
    public void numberValidReversedTest() throws Exception {
        int checked = 5;
        int max = 365;
        int min = 1;

        assertNull(Whitebox.invokeMethod(checkout, "numberValid", checked, max, min));
    }

    @Test
    public void numberValidInvalidTest() {
        int checked = -3;
        int max = 365;
        int min = 1;

        Exception exception = assertThrows( Exception.class, () -> {
            Whitebox.invokeMethod(checkout, "numberValid", checked, min, max);
        });

        assertTrue(exception.getMessage().contains("The value -3 is not within the acceptable range. The acceptable range is 1 to 365"));
    }

    @Test
    public void exitTest() {
        String message = "errorMessage";

        Exception exception = assertThrows( Exception.class, () -> {
            Whitebox.invokeMethod(checkout, "exit", message);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    public void startTest() throws Exception {
        String data = "CHNS\n" +
                      "5\n" +
                      "0\n" +
                      "07/01/21";

        Scanner scanner = mockUserInput(data);
        RentalAgreement output = Checkout.start();

        assertNotNull(output);
    }

    @Test
    public void startInvalidTest() throws Exception {
        String errorMessage = "The value -5 is not within the acceptable range. The acceptable range is 1 to 365";
        String data = "CHNS\n" +
                      "-5";

        mockUserInput(data);
        Exception exception = assertThrows( Exception.class, Checkout::start);

        assertEquals(errorMessage, exception.getMessage());
    }

    public static Scanner mockUserInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        return new Scanner(System.in);

    }
}
