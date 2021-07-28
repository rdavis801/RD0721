package toolsTests;

import org.junit.jupiter.api.Test;
import tech.ronalddavis.tools.ToolCode;
import tech.ronalddavis.tools.ToolType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ToolCodeTests {

    @Test
    public void getToolCodeCHNSTest() {
        ToolCode toolCode = ToolCode.CHNS;

        assertEquals("CHNS", toolCode.getToolCode());
    }

    @Test
    public void getToolCodeEnumTest() {
        ToolCode toolCode =  ToolCode.getToolCode("LADW");

        assertEquals(ToolCode.LADW, toolCode);
    }

    @Test
    public void getToolCodeEnumLowerCaseTest() {
        ToolCode toolCode =  ToolCode.getToolCode("chns");

        assertEquals(ToolCode.CHNS, toolCode);
    }

    @Test
    public void getToolCodeEnumBadStringTest() {
        ToolCode toolCode =  ToolCode.getToolCode("");

        assertNull(toolCode);
    }

    @Test
    public void getToolTypeCHNSTest() {
        ToolType toolType = ToolCode.getToolType(ToolCode.CHNS);

        assertEquals(ToolType.CHAINSAW, toolType);
    }

    @Test
    public void getToolTypeLADWTest() {
        ToolType toolType = ToolCode.getToolType(ToolCode.LADW);

        assertEquals(ToolType.LADDER, toolType);
    }

    @Test
    public void getToolTypeJAKDTest() {
        ToolType toolType = ToolCode.getToolType(ToolCode.JAKD);

        assertEquals(ToolType.JACKHAMMER, toolType);
    }

    @Test
    public void getToolTypeJAKRTest() {
        ToolType toolType = ToolCode.getToolType(ToolCode.JAKR);

        assertEquals(ToolType.JACKHAMMER, toolType);
    }
}
