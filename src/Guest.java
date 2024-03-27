import Enum.GuestStatus;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Guest {
    private final int number;
    private final int people;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private GuestStatus status = GuestStatus.WAITING;

    private static int guestCounter = 1;

    // Constructor

    public Guest(int people, LocalDate startDate, LocalDate endDate) {
        this.number = guestCounter;
        this.people = people;
        this.startDate = startDate;
        this.endDate = endDate;
        guestCounter = guestCounter + 1;
    }

    // Getter

    public int getNumber() {
        return number;
    }

    public int getPeople() {
        return people;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getNights() {
        return (int) ChronoUnit.DAYS.between(getStartDate(), getEndDate());
    }

    public GuestStatus getStatus() {
        return status;
    }

    // Setter

    public void setStatus(GuestStatus status) {
        this.status = status;
    }

    // Override

    @Override
    public String toString() {

        String peopleString;
        if (this.people == 1) {
            peopleString = "person";
        }
        else {
            peopleString = "people";
        }

        return "Guest " + this.number + " | "  + this.people + " " + peopleString + " | From " + this.startDate + " | To " + this.endDate + " | " + this.status;
    }

}
