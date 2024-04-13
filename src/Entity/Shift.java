package Entity;

import java.time.DayOfWeek;

/**
 * Shift where an employee is working during a specific time period on a certain day of the week
 */
public class Shift {
    private final DayOfWeek day;
    private final TimePeriod timePeriod;

    /**
     * Constructor
     * @param day Day of the week
     * @param timePeriod TimePeriod during which the employee is working
     */
    public Shift(DayOfWeek day, TimePeriod timePeriod) {
        this.day = day;
        this.timePeriod = timePeriod;
    }

    // Override

    @Override
    public String toString() {
        return "Shift | " + day + " | " + timePeriod;
    }

}

