public class Guest {
    private final int number;
    private final int people;
    private boolean accomodated;

    private static int guestCounter = 1;

    public Guest(int people) {
        this.number = guestCounter;
        this.people = people;
        guestCounter = guestCounter + 1;
    }

    @Override
    public String toString() {
        String people;
        if (this.people == 1) {
            people = "person";
        }
        else {
            people = "people";
        }
        return "Guest " + this.number + " | "  + this.people + " " + people + " | Accomodated " + this.accomodated;
    }

    public boolean isAccomodated() {
        return accomodated;
    }

    public void setAccomodated(boolean accomodated) {
        this.accomodated = accomodated;
    }

    public int getPeople() {
        return people;
    }

}
