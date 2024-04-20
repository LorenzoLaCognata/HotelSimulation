package Entity;

import Enum.GuestStatus;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Possible guest of the hotel
 */
public class Guest implements Comparable<Guest> {

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

    /**
     * Returns the guest identifier
     * @return Identifier of the guest
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the number of people staying with the guest
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

    /**
     * Calculate a number associated to the GuestStatus for sorting
     * @return Number associated to the GuestStatus
     */
    public int getStatusNumber() {
        if (status == GuestStatus.LOST) {
            return 1;
        }
        else if (status == GuestStatus.CHECKED_OUT) {
            return 2;
        }
        else if (status == GuestStatus.CHECKED_IN) {
            return 3;
        }
        else if (status == GuestStatus.RESERVED) {
            return 4;
        }
        else if (status == GuestStatus.WAITING) {
            return 5;
        }
        else {
            return 0;
        }
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

    @Override
    public int compareTo(@NotNull Guest other) {

        int thisNumber = getStatusNumber() * 1000000 + (int) getStartDate().toEpochDay();
        int otherNumber = other.getStatusNumber() * 1000000 + (int) other.getStartDate().toEpochDay();
        return Integer.compare(thisNumber, otherNumber);

    }

}
