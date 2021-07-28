package toolsTests;

import org.junit.jupiter.api.Test;
import tech.ronalddavis.tools.ToolType;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class ToolTypeTests {

    @Test
    public void getToolTypeTest() {
        ToolType toolType = ToolType.CHAINSAW;

        assertEquals("Chainsaw", toolType.getToolType());
    }

    @Test
    public void chargeOnWeekendsTrueTest() {
        ToolType toolType = ToolType.LADDER;

        assertTrue(ToolType.chargeOnWeekends(toolType));
    }

    @Test
    public void chargeOnWeekendsFalseChainsawTest() {
        ToolType toolType = ToolType.CHAINSAW;

        assertFalse(ToolType.chargeOnWeekends(toolType));
    }

    @Test
    public void chargeOnWeekendsFalseJackhammerTest() {
        ToolType toolType = ToolType.JACKHAMMER;

        assertFalse(ToolType.chargeOnWeekends(toolType));
    }

    @Test
    public void chargeOnHolidaysTrueTest() {
        ToolType toolType = ToolType.CHAINSAW;

        assertTrue(ToolType.chargeOnHolidays(toolType));
    }

    @Test
    public void chargeOnHolidaysTrueFalseLadderTest() {
        ToolType toolType = ToolType.LADDER;

        assertFalse(ToolType.chargeOnHolidays(toolType));
    }

    @Test
    public void chargeOnHolidaysTrueFalseJackhammerTest() {
        ToolType toolType = ToolType.JACKHAMMER;

        assertFalse(ToolType.chargeOnHolidays(toolType));
    }

    @Test
    public void dailyChargeJackhammerTest() {
        ToolType toolType = ToolType.JACKHAMMER;
        BigDecimal bigDecimal = BigDecimal.valueOf(2.99);

        assertEquals(bigDecimal, ToolType.dailyCharge(toolType));
    }

    @Test
    public void dailyChargeLadderTest() {
        ToolType toolType = ToolType.LADDER;
        BigDecimal bigDecimal = BigDecimal.valueOf(1.99);

        assertEquals(bigDecimal, ToolType.dailyCharge(toolType));
    }

    @Test
    public void dailyChargeChainsawTest() {
        ToolType toolType = ToolType.CHAINSAW;
        BigDecimal bigDecimal = BigDecimal.valueOf(1.49);

        assertEquals(bigDecimal, ToolType.dailyCharge(toolType));
    }
}
