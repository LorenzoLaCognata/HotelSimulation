import java.time.LocalDate;

public class Reservation {
    private final Room room;
    private final Guest guest;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reservation | From " + this.startDate + " | To " + this.endDate + " | GUEST " + this.guest + " | ROOM " + this.room;
    }

    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

}

