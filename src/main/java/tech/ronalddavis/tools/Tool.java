package tech.ronalddavis.tools;

import lombok.Data;

@Data
public class Tool {
    ToolType toolType;
    String brand;
    ToolCode toolCode;

    /*
     * Constructor for Tool. Receives a ToolCode and populates the toolType, brand, and toolCode fields
     */
    public Tool(ToolCode toolCode) {
        switch (toolCode) {
            case CHNS:
                this.brand = "Stihl";
                break;
            case LADW:
                this.brand = "Werner";
                break;
            case JAKD:
                this.brand = "DeWalt";
                break;
            case JAKR:
                this.brand = "Ridgid";
                break;
        }
        this.toolType = ToolCode.getToolType(toolCode);
        this.toolCode = toolCode;
    }
}
