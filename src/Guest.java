import Enum.GuestStatus;

public class Guest {
    private final int number;
    private final int people;
    private final int nights;
    private GuestStatus status = GuestStatus.WAITING;

    private static int guestCounter = 1;

    // Constructor

    public Guest(int people, int nights) {
        this.number = guestCounter;
        this.people = people;
        this.nights = nights;
        guestCounter = guestCounter + 1;
    }

    // Getter

    public int getNumber() {
        return number;
    }

    public int getPeople() {
        return people;
    }

    public int getNights() {
        return nights;
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

        String nightString;
        if (this.nights == 1) {
            nightString = "night";
        }
        else {
            nightString = "nights";
        }

        return "Guest " + this.number + " | "  + this.people + " " + peopleString + " | " + this.nights + " " + nightString + " | " + this.status;
    }

}
