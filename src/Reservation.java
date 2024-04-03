import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import Enum.ReservationStatus;

public class Reservation {
    private final Room room;
    private final Guest guest;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal rate;
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    // Constructor

    public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal rate) {
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
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

    public int getNights() {
        return Period.between(startDate, endDate).getDays();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal calculatePrice() {
        return rate.multiply(new BigDecimal(this.getNights()));
    }

    // Setter

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    // Override

    @Override
    public String toString() {
        return "Reservation | From " + this.startDate + " | To " + this.endDate + " | " + Log.currency.format(this.getRate()) + " x " + this.getNights() + " = " + Log.currency.format(this.calculatePrice()) + " | GUEST (" + this.guest + ") | ROOM (" + this.room + ")";
    }

}

