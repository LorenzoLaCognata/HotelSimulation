import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class TimePeriod {
    private final LocalTime startTime;
    private final LocalTime endTime;

    // Constructor

    public TimePeriod(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public static TimePeriod parseTimePeriod(String string) {
        try {
            LocalTime startTime = Input.parseTime(string.substring(0, 5));
            LocalTime endTime = Input.parseTime(string.substring(6, 11));
            if (startTime == null || endTime == null) {
                throw new Exception();
            }
            return new TimePeriod(startTime, endTime);
        } catch (Exception e) {
            return null;
        }
    }

    // Override

    @Override
    public String toString() {
        return this.getStartTime() + "-" + this.getEndTime();
    }

}

