package tech.ronalddavis.tools;

public enum ToolCode {
    LADW ("LADW"),
    CHNS ("CHNS"),
    JAKR ("JAKR"),
    JAKD ("JAKD");

    private final String toolCode;

    /*
     * Constructor for ToolCode
     */
    ToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    /*
     * Returns String value of ToolCode
     */
    public String getToolCode() {
        return toolCode;
    }

    /*
     * Returns the associated ToolType for the given ToolCode
     */
    public static ToolType getToolType(ToolCode toolCode) {
        switch (toolCode){
            case LADW:
                return ToolType.LADDER;
            case CHNS:
                return ToolType.CHAINSAW;
            case JAKD:
            case JAKR:
                return ToolType.JACKHAMMER;
            default:
                return null;
        }
    }

    /*
     * Returns the Enum value associated with the given String
     */
    public static ToolCode getToolCode(String toolCodeValue) {
        for (ToolCode type : ToolCode.values()) {
            if(type.getToolCode().equals(toolCodeValue.toUpperCase())) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() { return toolCode; }
}
