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

    ToolType(String toolType) { this.toolType = toolType; }

    public String getToolType() { return toolType; }

    public static ToolType getChargeMaster(String chargeMasterValue) {
        for (ToolType toolType : ToolType.values()) {
            if (toolType.getToolType().equals(chargeMasterValue)) {
                return toolType;
            }
        }
        return null;
    }

    public static boolean weekendCharge(ToolType toolType) {
        return toolType == LADDER;
    }

    public static boolean holidayCharge(ToolType toolType) {
        return toolType == CHAINSAW;
    }

    public static boolean weekdayCharge(ToolType toolType) {
        return toolType == CHAINSAW || toolType == LADDER || toolType == JACKHAMMER;
    }

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
