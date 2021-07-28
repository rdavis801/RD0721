package tech.ronalddavis.rentals;

import tech.ronalddavis.tools.Tool;
import tech.ronalddavis.tools.ToolCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Checkout {

    private static final int MINIMUM_PERCENT = 0;
    private static final int MAX_PERCENT = 100;
    private static final int MINIMUM_DAYS = 1;
    private static final int MAX_DAYS = 365;

    /*
     * Gathers ToolCode, rentalDays, discountPercentage, and checkoutDate
     * from the user to construct a Rental agreement.
     * Will throw an exception if the input is invalid.
     */
    public static RentalAgreement start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int rentalDays;
        int discountPercentage;
        LocalDate checkoutDate;
        Tool tool;

        System.out.println("\n\n\n******************* Welcome to POS Checkout *******************\n");

        // ToolCode
        tool = getTool(scanner);

        // Rental Days
        String rentalMessage = "Please Enter the number of rental days: ";
        String rentalErrorMessage = "The number of rental days is invalid.";

        rentalDays = getNumber(scanner, rentalMessage, rentalErrorMessage);
        numberValid(rentalDays, MINIMUM_DAYS, MAX_DAYS);

        // Discount percentage
        String discountMessage = "Please Enter the discount percentage to be applied to the order: ";
        String discountErrorMessage = "The discount percentage is invalid.";

        discountPercentage = getNumber(scanner, discountMessage, discountErrorMessage);
        numberValid(discountPercentage, MINIMUM_PERCENT, MAX_PERCENT);

        // Checkout Date
        checkoutDate = getCheckoutDate(scanner);

        return new RentalAgreement(tool, rentalDays, discountPercentage, checkoutDate);
    }

    /*
     * Gathers and returns the checkout date from the user
     * Throws an exception if the input is incorrect.
     */
    private static LocalDate getCheckoutDate(Scanner scanner) throws Exception {
        int attemptCounter = 0;
        String input;
        LocalDate checkoutDate;
        do {
            System.out.print("Please Enter the checkout date (mm/dd/yy): ");
            input = scanner.nextLine();
            if (attemptCounter++ >= 3){
                exit("The checkout date is invalid. Good bye.");
            }
           checkoutDate = parseLocalDate(input);
        } while (checkoutDate == null);

        return checkoutDate;
    }

    /*
     * Gathers and returns a number from the user
     * Throws an exception if the input is incorrect.
     */
    private static int getNumber(Scanner scanner, String message, String errorMessage) throws Exception {
        boolean parsed = false;
        int number = 0;
        int attemptCounter = 0;
        do {
            try {
                System.out.print(message);
                number = Integer.parseInt(scanner.nextLine());
                parsed = true;
            }
            catch (Exception e) {
                if (attemptCounter++ >= 3) {
                    exit(errorMessage + " Good bye.");
                }
            }
        } while (!parsed);
        return number;
    }

    /*
     * Gathers a ToolCode from the user and returns the associated Tool object
     * Throws an exception if the input is incorrect.
     */
    private static Tool getTool(Scanner scanner) throws Exception {
        int attemptCounter = 0;
        String input;
        do {
            System.out.print("Please Enter the tool code: ");
            input = scanner.nextLine();
            if (attemptCounter++ >= 3){
                exit("The tool code is invalid. Good bye.");
            }
        } while (ToolCode.getToolCode(input) == null);
        return new Tool(ToolCode.valueOf(input.toUpperCase()));
    }

    /*
     * Throws an exception with the passed message.
     */
    private static void exit(String message) throws Exception {
        throw new Exception(message);
    }

    /*
     * Parses the given string into a LocalDate object.
     * Returns null if not possible
     */
    private static LocalDate parseLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        try {
            return LocalDate.parse(date, formatter);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }

    /*
     * Throws an exception if the number is not within the valid range
     */
    private static void numberValid(int check, int min, int max) throws Exception {
        if (min > max)
        {
            int temp = min;
            min = max;
            max = temp;
        }
        if (check < min || check > max) {
             String errorMessage =
                     String.format("The value %d is not within the acceptable range. The acceptable range is %d to %d",
                             check, min, max);
             exit(errorMessage);
        }
    }
}
