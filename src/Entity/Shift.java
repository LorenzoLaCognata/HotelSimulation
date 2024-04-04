package Entity;

import Record.TimePeriod;

import java.time.DayOfWeek;

public class Shift {
    private final DayOfWeek day;
    private final TimePeriod timePeriod;

    // Constructor

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

