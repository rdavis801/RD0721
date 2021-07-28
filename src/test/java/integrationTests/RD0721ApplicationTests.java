package integrationTests;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import tech.ronalddavis.RD0721Application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class RD0721ApplicationTests {

    private static final PrintStream standardOut = System.out;
    private static final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    public RD0721ApplicationTests() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterClass
    public static void afterClass() {
        System.setOut(standardOut);
    }

    @Test
    public void Scenario1() {
        String expectedError = "The value 101 is not within the acceptable range. The acceptable range is 0 to 100";
        String data = "JAKR\n" +
                      "5\n" +
                      "101\n" +
                      "09/03/15";

        mockUserInput(data);
        RD0721Application.main((String []) null);

        assertTrue(outputStreamCaptor.toString().contains(expectedError));
    }

    @Test
    public void Scenario2() {
        String expectedOutput = "Tool code: LADW\nTool type: Ladder\nTool brand: Werner\nCheckout date: 07/02/20\n" +
                "Due date: 07/05/20\nNumber of total rental days: 3\nNumber of days charged: 2\nDaily charge: $1.99\n" +
                "Charge before discount: $3.98\nDiscount percentage: 10%\nDiscount amount: $0.40\nActual charges: $3.58";

        String data = "LADW\n" +
                      "3\n" +
                      "10\n" +
                      "07/02/20";

        mockUserInput(data);
        RD0721Application.main((String []) null);

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    @Test
    public void Scenario3() {
        String expectedOutput = "Tool code: CHNS\nTool type: Chainsaw\nTool brand: Stihl\nCheckout date: 07/02/15\n" +
                "Due date: 07/07/15\nNumber of total rental days: 5\nNumber of days charged: 3\nDaily charge: $1.49\n" +
                "Charge before discount: $4.47\nDiscount percentage: 25%\nDiscount amount: $1.12\nActual charges: $3.35";
        String data = "CHNS\n" +
                      "5\n" +
                      "25\n" +
                      "07/02/15";

        mockUserInput(data);
        RD0721Application.main((String []) null);

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    @Test
    public void Scenario4() {
        String expectedOutput = "Tool code: JAKD\nTool type: Jackhammer\nTool brand: DeWalt\nCheckout date: 09/03/15\n" +
                "Due date: 09/09/15\nNumber of total rental days: 6\nNumber of days charged: 4\nDaily charge: $2.99\n" +
                "Charge before discount: $11.96\nDiscount percentage: 0%\nDiscount amount: $0.00\nActual charges: $11.96";
        String data = "JAKD\n" +
                      "6\n" +
                      "0\n" +
                      "09/03/15";

        mockUserInput(data);
        RD0721Application.main((String []) null);

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    @Test
    public void Scenario5() {
        String expectedOutput = "Tool code: JAKR\nTool type: Jackhammer\nTool brand: Ridgid\nCheckout date: 07/02/15\n" +
                "Due date: 07/11/15\nNumber of total rental days: 9\nNumber of days charged: 5\nDaily charge: $2.99\n" +
                "Charge before discount: $14.95\nDiscount percentage: 0%\nDiscount amount: $0.00\nActual charges: $14.95";
        String data = "JAKR\n" +
                      "9\n" +
                      "0\n" +
                      "07/02/15";

        mockUserInput(data);
        RD0721Application.main((String []) null);

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    @Test
    public void Scenario6() {
        String expectedOutput = "Tool code: JAKR\nTool type: Jackhammer\nTool brand: Ridgid\nCheckout date: 07/02/20\n" +
                "Due date: 07/06/20\nNumber of total rental days: 4\nNumber of days charged: 1\nDaily charge: $2.99\n" +
                "Charge before discount: $2.99\nDiscount percentage: 50%\nDiscount amount: $1.50\nActual charges: $1.49";
        String data = "JAKR\n" +
                      "4\n" +
                      "50\n" +
                      "07/02/20";

        mockUserInput(data);
        RD0721Application.main((String []) null);

        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    public static void mockUserInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner scanner = new Scanner(System.in);
    }
}
