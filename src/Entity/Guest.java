package Entity;

import Enum.GuestStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Possible guest of the hotel
 */
public class Guest {

    private final int number;
    private final int people;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private GuestStatus status = GuestStatus.WAITING;
    private static int guestCounter = 1;

    /**
     * Constructor
     * @param people Number of people looking for an accomodation at the hotel
     * @param startDate Start date of the desired hotel stay
     * @param endDate End date of the desired hotel stay
     */
    public Guest(int people, LocalDate startDate, LocalDate endDate) {
        this.number = guestCounter;
        this.people = people;
        this.startDate = startDate;
        this.endDate = endDate;
        guestCounter = guestCounter + 1;
    }

    // Getter

    /**
     * Returns the number of people
     * @return Number of people looking for an accomodation at the hotel
     */
    public int getPeople() {
        return people;
    }

    /**
     * Returns the start date
     * @return Start date of the desired hotel stay
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Returns the end date
     * @return End date of the desired hotel stay
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns the number of nights
     * @return Number of nights of the desired hotel stay
     */
    public int getNights() {
        return (int) ChronoUnit.DAYS.between(getStartDate(), getEndDate());
    }

    /**
     * Returns the status of the guest
     * @return Current GuestStatus of the guest
     */
    public GuestStatus getStatus() {
        return status;
    }

    // Setter

    /**
     * Sets the status of the guest
     * @param status GuestStatus to assign to the guest
     */
    public void setStatus(GuestStatus status) {
        this.status = status;
    }

    // Override

    @Override
    public String toString() {

        String peopleString;
        if (people == 1) {
            peopleString = "person";
        }
        else {
            peopleString = "people";
        }

        return "Guest " + number + " | "  + people + " " + peopleString + " | From " + startDate + " | To " + endDate + " | " + status;
    }

}
