package Entity;

import IO.Input;

import java.time.LocalTime;

/**
 * TimePeriod corresponding to the time between a start time and an end time
 * @param startTime Start time of the TimePeriod
 * @param endTime End time of the TimePeriod
 */
public record TimePeriod(LocalTime startTime, LocalTime endTime) {

    /** Converts a string to a TimePeriod
     *
     * @param string String with the format hh:mm-hh:mm
     * @return TimePeriod corresponding to the string, or null if the string is not in the right format
     */
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