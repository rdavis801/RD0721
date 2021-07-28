package toolsTests;

import org.junit.jupiter.api.Test;
import tech.ronalddavis.tools.Tool;
import tech.ronalddavis.tools.ToolCode;
import tech.ronalddavis.tools.ToolType;

import static org.junit.jupiter.api.Assertions.*;

public class ToolTests {

    @Test
    public void constructorCHNSTest() {
        Tool tool = new Tool(ToolCode.CHNS);

        assertEquals("Stihl", tool.getBrand());
        assertEquals(ToolType.CHAINSAW, tool.getToolType());
        assertEquals(ToolCode.CHNS, tool.getToolCode());
    }

    @Test
    public void constructorLADWTest() {
        Tool tool = new Tool(ToolCode.LADW);

        assertEquals("Werner", tool.getBrand());
        assertEquals(ToolType.LADDER, tool.getToolType());
        assertEquals(ToolCode.LADW, tool.getToolCode());
    }

    @Test
    public void constructorJAKDTest() {
        Tool tool = new Tool(ToolCode.JAKD);

        assertEquals("DeWalt", tool.getBrand());
        assertEquals(ToolType.JACKHAMMER, tool.getToolType());
        assertEquals(ToolCode.JAKD, tool.getToolCode());
    }

    @Test
    public void constructorJAKRTest() {
        Tool tool = new Tool(ToolCode.JAKR);

        assertEquals("Ridgid", tool.getBrand());
        assertEquals(ToolType.JACKHAMMER, tool.getToolType());
        assertEquals(ToolCode.JAKR, tool.getToolCode());
    }
}
