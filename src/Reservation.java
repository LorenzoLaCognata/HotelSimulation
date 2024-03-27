import java.time.LocalDate;
import Enum.ReservationStatus;

public class Reservation {
    private final Room room;
    private final Guest guest;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    // Constructor

    public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter

    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Setter

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    // Override

    @Override
    public String toString() {
        return "Reservation | From " + this.startDate + " | To " + this.endDate + " | GUEST (" + this.guest + ") | ROOM (" + this.room + ")";
    }

}

