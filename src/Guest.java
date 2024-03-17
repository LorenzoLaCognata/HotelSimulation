public class Guest {
    private final int number;
    private final int people;
    private static int guestCounter = 1;

    public Guest(int people) {
        this.number = guestCounter;
        this.people = people;
        guestCounter = guestCounter + 1;
    }

    @Override
    public String toString() {
        return "Guest " + this.number + " | "  + "X".repeat(this.people);
    }

}
