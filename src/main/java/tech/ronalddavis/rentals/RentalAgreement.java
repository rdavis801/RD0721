package tech.ronalddavis.rentals;

import tech.ronalddavis.dates.DatesHelper;
import tech.ronalddavis.tools.Tool;
import tech.ronalddavis.tools.ToolCode;
import tech.ronalddavis.tools.ToolType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalAgreement {
    final private int rentalDays;
    final private int chargedDays;
    final private String toolBrand;
    final private ToolCode toolCode;
    final private ToolType toolType;
    final private LocalDate checkoutDate;
    final private LocalDate dueDate;
    final private BigDecimal dailyCharge;
    final private BigDecimal fullCharge;
    final private BigDecimal amountOff;
    final private BigDecimal finalCharges;
    final private BigDecimal discountPercentage;

    /*
     * Constructor for RentalAgreement. Receives a Tool, rentalDays, discountPercentage, and checkoutDate
     * Completes the necessary data fields to print the report
     */
    public RentalAgreement(Tool tool, int rentalDays,  int discountPercentage, LocalDate checkoutDate) throws Exception {
        if(tool == null || tool.getToolType() == null || tool.getToolCode() == null || tool.getBrand() == null)
        {
            throw new Exception("All tool fields must be non null");
        }
        this.toolCode = tool.getToolCode();
        this.toolType = tool.getToolType();
        this.toolBrand = tool.getBrand();
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;

        this.discountPercentage = BigDecimal.valueOf(discountPercentage).movePointLeft(2);
        dueDate = checkoutDate.plusDays(rentalDays);
        dailyCharge = ToolType.dailyCharge(toolType).setScale(2, RoundingMode.HALF_UP);
        chargedDays = getChargedDays(checkoutDate, dueDate, toolType);
        fullCharge = dailyCharge.multiply(BigDecimal.valueOf(chargedDays)).setScale(2, RoundingMode.HALF_UP);
        amountOff = fullCharge.multiply(this.discountPercentage).setScale(2, RoundingMode.HALF_UP);
        finalCharges = fullCharge.subtract(amountOff).setScale(2, RoundingMode.HALF_UP);
    }

    /*
     * Returns the number of charged days a given ToolType would accrue over the given range
     */
    private int getChargedDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolType) throws Exception {
        int cumulativeDays = 0;

        cumulativeDays += DatesHelper.findWeekdays(checkoutDate, dueDate);
        cumulativeDays += (ToolType.chargeOnWeekends(toolType)) ? DatesHelper.findWeekends(checkoutDate, dueDate) : 0;

        // If there is no charge to rent on holidays, subtract from cumulativeDays as the current set of holidays are
        // exclusively on weekdays
        if (!ToolType.chargeOnHolidays(toolType)) {
            cumulativeDays -= DatesHelper.fallsOnIndependenceDay(checkoutDate, dueDate) ? 1 : 0;
            cumulativeDays -= DatesHelper.fallsOnLaborDay(checkoutDate,dueDate) ? 1 : 0;
        }

        return cumulativeDays;
    }

    /*
     * Prints the RentalAgreement report
     */
    public void printReport() {
        System.out.print("\n\n\n");
        System.out.printf("Tool code: %s\n", toolCode.toString());
        System.out.printf("Tool type: %s\n", toolType.toString());
        System.out.printf("Tool brand: %s\n", toolBrand);
        System.out.printf("Checkout date: %s\n", dateFormat(checkoutDate));
        System.out.printf("Due date: %s\n", dateFormat(dueDate));
        System.out.printf("Number of total rental days: %d\n", rentalDays);
        System.out.printf("Number of days charged: %d\n", chargedDays);
        System.out.printf("Daily charge: %s\n", currencyFormat(dailyCharge));
        System.out.printf("Charge before discount: %s\n", currencyFormat(fullCharge));
        System.out.printf("Discount percentage: %s\n", percentageFormat(discountPercentage));
        System.out.printf("Discount amount: %s\n", currencyFormat(amountOff));
        System.out.printf("Actual charges: %s\n", currencyFormat(finalCharges));
        System.out.print("\n");
    }

    /*
     * Transform a BigDecimal object to the necessary String currency pattern
     */
    private static String currencyFormat(BigDecimal bigDecimal) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        return numberFormat.format(bigDecimal);
    }

    /*
     * Transform a BigDecimal object to the necessary String percentage pattern
     */
    private static String percentageFormat(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#%");
        return decimalFormat.format(bigDecimal);
    }

    /*
     * Transform a LocalDate object to the necessary String pattern
     */
    private static String dateFormat(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return formatter.format(localDate);
    }
}
