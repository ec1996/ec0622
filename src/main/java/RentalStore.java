import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.time.DayOfWeek.*;

/**
 * Represents a store where one can rent tools from and where tools are stored.
 * @author Eli Charleville
 */
public class RentalStore
{
    /**
     * The {@link Map} of {@link Tool}s by String tool code.
     */
    final private Map<String, Tool> toolsByToolCode;

    /**
     * Constructor for {@link RentalStore}.
     * @param toolsByToolCode
     *         The {@link Map} of {@link Tool}s by String tool code.
     */
    public RentalStore(final Map<String, Tool> toolsByToolCode)
    {
        this.toolsByToolCode = toolsByToolCode;
    }

    /**
     * Checks out a tool with a given {@link String} tool code for rental and returns a generated
     * {@link RentalAgreement}.
     * @param toolCode
     *         The {@link String} tool identifier.
     * @param rentalDays
     *         The number of requested days to rent the tool.
     * @param discountPercentage
     *         The discount percent in this format: %<Discount Percent>
     * @param checkoutDate
     *         The {@link LocalDate}.
     * @return A potentially null {@link RentalAgreement}.
     * @throws IllegalArgumentException
     *         If rentalDays < 1.
     * @throws IllegalArgumentException
     *         If the discount percentage is not between 0 and 100 (inclusive).
     */
    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercentage, LocalDate checkoutDate) throws IllegalArgumentException
    {
        if (rentalDays < 1)
        {
            throw new IllegalArgumentException("The rental day count must be greater than or equal to 1. Rental day " + "count: " + rentalDays);
        }

        if (discountPercentage > 100 || discountPercentage < 0)
        {
            throw new IllegalArgumentException("The discount percentage value must be a number from 0 to 100. " +
                    "discount percentage value: " + discountPercentage);
        }

        final Tool tool = toolsByToolCode.get(toolCode);

        if (!tool.isAvailable())
        {
            System.out.println("Tool with tool code: " + toolCode + " is not available to rent.");
            return null;
        }

        final boolean weekdayCharge = tool.isWeekdayCharge();
        final boolean weekendCharge = tool.isWeekendCharge();
        final boolean holidayCharge = tool.isHolidayCharge();

        final LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        final int numberOfDaysToCharge = calculateNumberOfDaysToCharge(checkoutDate, weekdayCharge, weekendCharge,
                holidayCharge, dueDate);

        final BigDecimal dailyCharge = tool.getDailyCharge();

        final BigDecimal discountPercentageBigDecimal = BigDecimal.valueOf(discountPercentage / 100.0f);

        final BigDecimal preDiscountCharge =
                BigDecimal.valueOf(numberOfDaysToCharge).multiply(dailyCharge).setScale(2, RoundingMode.HALF_UP);

        final BigDecimal discountAmount =
                preDiscountCharge.subtract(preDiscountCharge.multiply(BigDecimal.ONE.subtract(discountPercentageBigDecimal))).setScale(2, RoundingMode.HALF_UP);

        final BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        tool.setAvailability(false);

        return new RentalAgreement(toolCode, tool.getToolType(), tool.getToolBrand(), rentalDays,
                numberOfDaysToCharge, checkoutDate, dueDate, dailyCharge, preDiscountCharge, discountAmount,
                finalCharge, discountPercentage);
    }

    /**
     * Calculates the number of days to charge the daily charge value for the tool.
     * @param checkoutDate
     *         The {@link LocalDate} check out date. Must be before dueDate.
     * @param weekdayCharge
     *         True if the daily rental fee applies on weekdays, false otherwise.
     * @param weekendCharge
     *         True if the daily rental fee applies on weekends, false otherwise.
     * @param holidayCharge
     *         True if the daily rental fee applies on holidays, false otherwise.
     * @param dueDate
     *         The last day that the tool is rented for. Must be after checkoutDate.
     * @return The number of days that are chargeable.
     */
    private int calculateNumberOfDaysToCharge(final LocalDate checkoutDate, final boolean weekdayCharge,
                                              final boolean weekendCharge, final boolean holidayCharge,
                                              final LocalDate dueDate)
    {
        assert dueDate.isAfter(checkoutDate) : "dueDate must be after checkOutDate";

        final Set<DayOfWeek> daysToCheck = new HashSet<>();

        if (weekdayCharge)
        {
            daysToCheck.addAll(Set.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));
        }
        if (weekendCharge)
        {
            daysToCheck.addAll(Set.of(SATURDAY, SUNDAY));
        }

        int chargeDays =
                (int) checkoutDate.plusDays(1L).datesUntil(dueDate.plusDays(1L)).filter(d -> daysToCheck.contains(d.getDayOfWeek())).count();

        if (!holidayCharge)
        {
            final Set<LocalDate> holidays = calculateHolidays(checkoutDate, dueDate);

            if (!holidays.isEmpty())
            {
                for (LocalDate holiday : holidays)
                {
                    if (holiday.isAfter(checkoutDate) && holiday.compareTo(dueDate) <= 0)
                    {
                        chargeDays--;
                    }
                }
            }
        }

        return chargeDays;
    }

    /**
     * Calculates the Independence day and labor day holidays Based on the checkout date and due date.
     * @param checkoutDate
     *         The {@link LocalDate} checkout date. Must be before dueDate.
     * @param dueDate
     *         The {@link LocalDate} due date. Must be after checkoutDate.
     * @return The potentially empty {@link Set} of {@link LocalDate} holidays of independence day and labor day.
     */
    private Set<LocalDate> calculateHolidays(LocalDate checkoutDate, LocalDate dueDate)
    {
        assert dueDate.isAfter(checkoutDate) : "dueDate must be after checkOutDate";

        final Set<LocalDate> holidays = new HashSet<>();

        final int checkoutDateYear = checkoutDate.getYear();
        final int dueDateYear = dueDate.getYear();

        for (int year = checkoutDateYear; year <= dueDateYear; year++)
        {
            holidays.add(LocalDate.of(year, Month.SEPTEMBER, 1).with(TemporalAdjusters.dayOfWeekInMonth(1,
                    DayOfWeek.MONDAY)));

            LocalDate independenceDay = LocalDate.of(year, Month.JULY, 4);
            if (independenceDay.getDayOfWeek() == SATURDAY)
            {
                independenceDay = independenceDay.minusDays(1);
            } else if (independenceDay.getDayOfWeek() == SUNDAY)
            {
                independenceDay = independenceDay.plusDays(1);
            }

            holidays.add(independenceDay);
        }

        return holidays;
    }
}
