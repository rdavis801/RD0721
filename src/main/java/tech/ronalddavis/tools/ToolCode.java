package tech.ronalddavis.tools;

public enum ToolCode {
    LADW ("LADW"),
    CHNS ("CHNS"),
    JAKR ("JAKR"),
    JAKD ("JAKD");

    private final String toolCode;

    ToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolCode() {
        return toolCode;
    }

    public static ToolCode getToolCode(String toolCodeValue) {
        for (ToolCode type : ToolCode.values()) {
            if(type.getToolCode().equals(toolCodeValue)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() { return toolCode; }
}
