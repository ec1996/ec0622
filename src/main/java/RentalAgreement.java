import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a rental agreement between a customer and a {@link RentalStore} to rent a given {@link Tool}.
 * @author Eli Charleville
 */
public class RentalAgreement
{
    /**
     * The {@link String} identifier for the tool.
     */
    final private String toolCode;

    /**
     * The {@link ToolType} for the tool.
     */
    final private ToolType toolType;

    /**
     * The {@link ToolBrand} for the tool.
     */
    final private ToolBrand toolBrand;

    /**
     * The number of days to rent the tool.
     */
    final private int rentalDays;

    /**
     * The number of days that the customer will be charged for.
     */
    final private int chargeDays;

    /**
     * The {@link LocalDate} check out date.
     */
    final private LocalDate checkoutDate;

    /**
     * The {@link LocalDate} due date.
     */
    final private LocalDate dueDate;

    /**
     * The {@link BigDecimal} daily charge in US dollars for the renting the tool.
     */
    final private BigDecimal dailyCharge;

    /**
     * The {@link BigDecimal} pre-discount charge. Calculated as charge days multiplied by daily charge.
     * Rounded half up to cents.
     */
    final private BigDecimal preDiscountCharge;

    /**
     * The {@link BigDecimal} discount amount Calculated from discountPercent and pre-discount charge. Resulting amount
     * rounded half up to cents.
     */
    final private BigDecimal discountAmount;

    /**
     * The {@link BigDecimal} Calculated as pre-discount charge - discount amount.
     */
    final private BigDecimal finalCharge;

    /**
     * The discount percent in this format: %<Discount Percent>
     */
    final private int discountPercent;

    /**
     * @param toolCode
     *         The {@link String} identifier for the tool.
     * @param toolType
     *         The {@link ToolType} for the tool.
     * @param toolBrand
     *         The {@link ToolBrand} for the tool.
     * @param rentalDays
     *         The number of days to rent the tool.
     * @param chargeDays
     *         The number of days that the customer will be charged for.
     * @param checkoutDate
     *         The {@link LocalDate} check out date.
     * @param dueDate
     *         The {@link LocalDate} due date.
     * @param dailyCharge
     *         The {@link BigDecimal} daily charge in US dollars for the renting the tool.
     * @param preDiscountCharge
     *         The {@link BigDecimal} pre-discount charge.
     * @param discountAmount
     *         The {@link BigDecimal} discount amount.
     * @param finalCharge
     *         The {@link BigDecimal} final charge.
     * @param discountPercent
     *         The discount percent in this format: %<Discount Percent>
     */
    public RentalAgreement(String toolCode, ToolType toolType, ToolBrand toolBrand, int rentalDays, int chargeDays,
                           LocalDate checkoutDate, LocalDate dueDate, BigDecimal dailyCharge,
                           BigDecimal preDiscountCharge,
                           BigDecimal discountAmount, BigDecimal finalCharge, int discountPercent)
    {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
        this.rentalDays = rentalDays;
        this.chargeDays = chargeDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.dailyCharge = dailyCharge;
        this.preDiscountCharge = preDiscountCharge;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
        this.discountPercent = discountPercent;
    }

    /**
     * Formats a {@link BigDecimal} into a US Dollar currency format.
     * @param bigDecimal
     *         The {@link BigDecimal}.
     * @return The formatted {@link String} in US Dollar currency format.
     */
    public static String currencyFormat(BigDecimal bigDecimal)
    {
        return NumberFormat.getInstance(Locale.US).format(bigDecimal);
    }

    @Override
    public String toString()
    {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        return
                "Tool code: " + toolCode + "\n" +
                        "Tool type: " + toolType + "\n" +
                        "Tool brand: " + toolBrand + "\n" +
                        "Rental days: " + rentalDays + "\n" +
                        "Checkout date: " + checkoutDate.format(dateTimeFormatter) + "\n" +
                        "Due date: " + dueDate.format(dateTimeFormatter) + "\n" +
                        "Daily rental charge: $" + currencyFormat(dailyCharge) + "\n" +
                        "Charge days: " + chargeDays + "\n" +
                        "Pre-discount charge: $" + currencyFormat(preDiscountCharge) + "\n" +
                        "Discount percent: " + discountPercent + "%" + "\n" +
                        "Discount amount: $" + currencyFormat(discountAmount) + "\n" +
                        "Final charge: $" + currencyFormat(finalCharge) + "\n";
    }
}
