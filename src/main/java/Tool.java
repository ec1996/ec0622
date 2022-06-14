import java.math.BigDecimal;

/**
 * Represents a tool within the rental application.
 * @author Eli Charleville
 */
public class Tool
{
    /**
     * The String identifier for the tool.
     */
    private String toolCode;

    /**
     * The {@link ToolType} for the tool.
     */
    private ToolType toolType;

    /**
     * The {@link ToolBrand} for the tool.
     */
    private ToolBrand toolBrand;

    /**
     * The {@link BigDecimal} daily charge in US dollars for the renting the tool.
     */
    private BigDecimal dailyCharge;

    /**
     * True if the daily rental fee applies on weekdays, false otherwise.
     */
    private boolean weekdayCharge;

    /**
     * True if the daily rental fee applies on weekends, false otherwise.
     */
    private boolean weekendCharge;

    /**
     * True if the daily rental fee applies on holidays, false otherwise.
     */
    private boolean holidayCharge;

    /**
     * True if the tool is available to rent, false otherwise.
     */
    private boolean available;

    /**
     * @return True if the daily rental fee applies on holidays, false otherwise.
     */
    public boolean isHolidayCharge()
    {
        return holidayCharge;
    }

    /**
     * Sets whether the daily rental fee applies on holidays.
     * @param holidayCharge
     *         True if the daily rental fee applies on holidays, false otherwise.
     */
    public void setHolidayCharge(boolean holidayCharge)
    {
        this.holidayCharge = holidayCharge;
    }

    /**
     * @return the {@link BigDecimal} daily charge in US dollars for the renting the tool.
     */
    public BigDecimal getDailyCharge()
    {
        return dailyCharge;
    }

    /**
     * Sets the daily charge for renting the tool.
     * @param dailyCharge
     *         The {@link BigDecimal} daily charge.
     */
    public void setDailyCharge(BigDecimal dailyCharge)
    {
        this.dailyCharge = dailyCharge;
    }

    /**
     * @return the String identifier for the tool.
     */
    public String getToolCode()
    {
        return toolCode;
    }

    /**
     * Sets the String identifier for the tool.
     * @param toolCode
     *         The {@link String} tool code.
     */
    public void setToolCode(String toolCode)
    {
        this.toolCode = toolCode;
    }

    /**
     * @return The {@link ToolType} for the tool.
     */
    public ToolType getToolType()
    {
        return toolType;
    }

    /**
     * Sets the {@link ToolType}.
     * @param toolType
     *         The {@link ToolType}.
     */
    public void setToolType(ToolType toolType)
    {
        this.toolType = toolType;
    }

    /**
     * @return The {@link ToolBrand} for the tool.
     */
    public ToolBrand getToolBrand()
    {
        return toolBrand;
    }

    /**
     * Sets the {@link ToolBrand}.
     * @param toolBrand
     *         The {@link ToolBrand}.
     */
    public void setToolBrand(ToolBrand toolBrand)
    {
        this.toolBrand = toolBrand;
    }

    /**
     * @return true if the daily rental fee applies on weekdays, false otherwise.
     */
    public boolean isWeekdayCharge()
    {
        return weekdayCharge;
    }

    /**
     * Sets whether the daily rental fee applies on weekdays.
     * @param weekdayCharge
     *         True if the daily rental fee applies on weekdays, false otherwise.
     */
    public void setWeekdayCharge(boolean weekdayCharge)
    {
        this.weekdayCharge = weekdayCharge;
    }

    /**
     * @return True if the daily rental fee applies on weekdays, false otherwise.
     */
    public boolean isWeekendCharge()
    {
        return weekendCharge;
    }

    /**
     * Sets whether the daily rental fee applies on weekends.
     * @param weekendCharge
     *         True if the daily rental fee applies on weekends, false otherwise.
     */
    public void setWeekendCharge(boolean weekendCharge)
    {
        this.weekendCharge = weekendCharge;
    }

    /**
     * @return True if the tool is available for rent, false otherwise.
     */
    public boolean isAvailable()
    {
        return available;
    }

    /**
     * Sets the availability of the tool for rental.
     * @param available
     *         True if the tool is available for rent, false otherwise.
     */
    public void setAvailability(boolean available)
    {
        this.available = available;
    }
}
