import java.time.DayOfWeek;
import java.time.LocalTime;

public class Shift {
    private final DayOfWeek day;
    private final TimePeriod timePeriod;

    // Constructor

    public Shift(DayOfWeek day, TimePeriod timePeriod) {
        this.day = day;
        this.timePeriod = timePeriod;
    }

    // Getter

    public DayOfWeek getDay() {
        return day;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public LocalTime getStartTime() {
        return timePeriod.getStartTime();
    }

    public LocalTime getEndTime() {
        return timePeriod.getEndTime();
    }

    // Override

    @Override
    public String toString() {
        return "Shift | " + this.day + " | " + this.timePeriod;
    }

}

