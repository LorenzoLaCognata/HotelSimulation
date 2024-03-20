public class Guest {
    private final int number;
    private final int people;
    private boolean accommodated = false;

    private static int guestCounter = 1;

    // Constructor

    public Guest(int people) {
        this.number = guestCounter;
        this.people = people;
        guestCounter = guestCounter + 1;
    }

    // Getter

    public int getNumber() {
        return number;
    }

    public int getPeople() {
        return people;
    }

    public boolean isAccommodated() {
        return accommodated;
    }

    // Setter

    public void setAccommodated(boolean accommodated) {
        this.accommodated = accommodated;
    }

    // Override

    @Override
    public String toString() {
        String people;
        if (this.people == 1) {
            people = "person";
        }
        else {
            people = "people";
        }
        return "Guest " + this.number + " | "  + this.people + " " + people;
    }

}
