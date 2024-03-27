import java.time.DayOfWeek;
import java.time.LocalTime;

public class Shift {
    private final DayOfWeek day;
    private final LocalTime startHour;
    private final LocalTime endHour;

    // Constructor

    public Shift(DayOfWeek day, LocalTime startHour, LocalTime endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    // Getter

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    // Override

    @Override
    public String toString() {
        return "Shift | " + this.day + " | " + this.startHour + " to " + this.endHour;
    }

}

