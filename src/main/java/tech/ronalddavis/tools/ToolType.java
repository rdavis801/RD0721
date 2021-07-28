package tech.ronalddavis.tools;

import java.math.BigDecimal;

public enum ToolType {
    LADDER ("Ladder"),
    CHAINSAW ("Chainsaw"),
    JACKHAMMER ("Jackhammer");

    private final String toolType;
    private static final BigDecimal LADDER_CHARGE = BigDecimal.valueOf(1.99);
    private static final BigDecimal CHAINSAW_CHARGE = BigDecimal.valueOf(1.49);
    private static final BigDecimal JACKHAMMER_CHARGE = BigDecimal.valueOf(2.99);

    /*
     * Constructor for ToolType
     */
    ToolType(String toolType) { this.toolType = toolType; }

    /*
     * Returns the String value of ToolType
     */
    public String getToolType() { return toolType; }

    /*
     * Returns if the given ToolType incurs a charge on weekends
     */
    public static boolean chargeOnWeekends(ToolType toolType) {
        return toolType == LADDER;
    }

    /*
     * Returns if the given ToolType incurs a charge on holidays
     */
    public static boolean chargeOnHolidays(ToolType toolType) {
        return toolType == CHAINSAW;
    }

    /*
     * Returns if the dailyCharge associated with the given ToolType
     */
    public static BigDecimal dailyCharge(ToolType toolType) {
        switch (toolType){
            case LADDER:
                return LADDER_CHARGE;
            case CHAINSAW:
                return CHAINSAW_CHARGE;
            case JACKHAMMER:
                return JACKHAMMER_CHARGE;
            default:
                return null;
        }
    }

    @Override
    public String toString() { return toolType; }
}
