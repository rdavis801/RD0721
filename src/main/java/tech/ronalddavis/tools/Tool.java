package tech.ronalddavis.tools;

import lombok.Data;

@Data
public class Tool {
    ToolType toolType;
    String brand;
    ToolCode toolCode;
}
