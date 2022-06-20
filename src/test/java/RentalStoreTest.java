import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RentalStore} class.
 * @author Eli Charleville
 */
class RentalStoreTest
{
    /**
     * Tests that {@link RentalStore} will throw a {@link IllegalArgumentException} if the percentage value is not
     * within the range of 0 to 100, inclusive.
     * This is also known as scenario 1.
     */
    @Test
    public void test_Scenario1()
    {
        Tool toolJAKR = new Tool("JAKR", ToolType.JACKHAMMER, ToolBrand.RIDGID, BigDecimal.valueOf(2.99), true,
                false, false, true);

        final Map<String, Tool> toolsByToolCode = Map.of("JAKR", toolJAKR);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);

        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
                rentalStore.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3)));


        assertEquals("The discount percentage value must be a number from 0 to 100. " +
                "Discount percentage value: 101", exception.getMessage());
    }

    @Test
    public void test_Scenario2()
    {
        final Tool toolLADW = new Tool("LADW", ToolType.LADDER, ToolBrand.WERNER, BigDecimal.valueOf(1.99), true,
                true, false, true);

        final Map<String, Tool> toolsByToolCode = Map.of("LADW", toolLADW);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);

        final RentalAgreement rentalAgreement = rentalStore.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));

        assertTrue(rentalAgreement.getDiscountAmount().compareTo(BigDecimal.valueOf(0.40)) == 0);
        assertTrue(rentalAgreement.getPreDiscountCharge().compareTo(BigDecimal.valueOf(3.98)) == 0);
        assertTrue(rentalAgreement.getFinalCharge().compareTo(BigDecimal.valueOf(3.58)) == 0);
    }

    @Test
    public void test_Scenario3()
    {
        Tool toolCHNS = new Tool("CHNS", ToolType.CHAINSAW, ToolBrand.STIHL, BigDecimal.valueOf(1.49), true,
                false, true, true);

        final Map<String, Tool> toolsByToolCode = Map.of("CHNS", toolCHNS);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);

        final RentalAgreement rentalAgreement = rentalStore.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));

        assertTrue(rentalAgreement.getDiscountAmount().compareTo(BigDecimal.valueOf(1.12)) == 0);
        assertTrue(rentalAgreement.getPreDiscountCharge().compareTo(BigDecimal.valueOf(4.47)) == 0);
        assertTrue(rentalAgreement.getFinalCharge().compareTo(BigDecimal.valueOf(3.35)) == 0);
    }

    @Test
    public void test_Scenario4()
    {
        Tool toolJAKD = new Tool("JAKD", ToolType.JACKHAMMER, ToolBrand.DEWALT, BigDecimal.valueOf(2.99), true,
                false, false, true);

        final Map<String, Tool> toolsByToolCode = Map.of("JAKD", toolJAKD);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);

        final RentalAgreement rentalAgreement = rentalStore.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));

        assertTrue(rentalAgreement.getDiscountAmount().compareTo(BigDecimal.valueOf(0.0)) == 0);
        assertTrue(rentalAgreement.getPreDiscountCharge().compareTo(BigDecimal.valueOf(8.97)) == 0);
        assertTrue(rentalAgreement.getFinalCharge().compareTo(BigDecimal.valueOf(8.97)) == 0);
    }

    @Test
    public void test_Scenario5()
    {
        Tool toolJAKR = new Tool("JAKR", ToolType.JACKHAMMER, ToolBrand.RIDGID, BigDecimal.valueOf(2.99), true,
                false, false, true);

        final Map<String, Tool> toolsByToolCode = Map.of("JAKR", toolJAKR);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);

        final RentalAgreement rentalAgreement = rentalStore.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));

        assertTrue(rentalAgreement.getDiscountAmount().compareTo(BigDecimal.valueOf(0.0)) == 0);
        assertTrue(rentalAgreement.getPreDiscountCharge().compareTo(BigDecimal.valueOf(14.95)) == 0);
        assertTrue(rentalAgreement.getFinalCharge().compareTo(BigDecimal.valueOf(14.95)) == 0);
    }

    @Test
    public void test_Scenario6()
    {
        Tool toolJAKR = new Tool("JAKR", ToolType.JACKHAMMER, ToolBrand.RIDGID, BigDecimal.valueOf(2.99), true,
                false, false, true);

        final Map<String, Tool> toolsByToolCode = Map.of("JAKR", toolJAKR);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);

        final RentalAgreement rentalAgreement = rentalStore.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));

        assertTrue(rentalAgreement.getDiscountAmount().compareTo(BigDecimal.valueOf(1.50)) == 0);
        assertTrue(rentalAgreement.getPreDiscountCharge().compareTo(BigDecimal.valueOf(2.99)) == 0);
        assertTrue(rentalAgreement.getFinalCharge().compareTo(BigDecimal.valueOf(1.49)) == 0);
    }

    /**
     * Test for when an independence day is on sunday, then the customer will not be charged on the monday after.
     */
    @Test
    public void test_IndependenceDayOnSunday()
    {
        Tool toolJAKR = new Tool("JAKR", ToolType.JACKHAMMER, ToolBrand.RIDGID, BigDecimal.valueOf(2.99), true,
                false, false, true);

        final Map<String, Tool> toolsByToolCode = Map.of("JAKR", toolJAKR);

        final RentalStore rentalStore = new RentalStore(toolsByToolCode);


        final RentalAgreement rentalAgreement = rentalStore.checkout("JAKR", 4, 50, LocalDate.of(2021, 7, 2));
        
        assertTrue(rentalAgreement.getDiscountAmount().compareTo(BigDecimal.valueOf(1.50)) == 0);
        assertTrue(rentalAgreement.getPreDiscountCharge().compareTo(BigDecimal.valueOf(2.99)) == 0);
        assertTrue(rentalAgreement.getFinalCharge().compareTo(BigDecimal.valueOf(1.49)) == 0);
    }
}