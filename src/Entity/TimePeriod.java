package Entity;

import IO.Input;

import java.time.LocalTime;

public record TimePeriod(LocalTime startTime, LocalTime endTime) {

    // Getter

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
        return startTime() + "-" + endTime();
    }

}