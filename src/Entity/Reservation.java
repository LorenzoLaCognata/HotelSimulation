package Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import Enum.ReservationStatus;
import IO.Log;

/**
 *
 */
public class Reservation {
    private final Room room;
    private final Guest guest;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal rate;
    private ReservationStatus status = ReservationStatus.CONFIRMED;

    /**
     *
     * @param room
     * @param guest
     * @param startDate
     * @param endDate
     * @param rate
     */
    public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal rate) {
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    public Room getRoom() {
        return room;
    }

    /**
     *
     * @return
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     *
     * @return
     */
    public ReservationStatus getStatus() {
        return status;
    }

    /**
     *
     * @return
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     *
     * @return
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     *
     * @return
     */
    public int getNights() {
        return Period.between(startDate, endDate).getDays();
    }

    /**
     *
     * @return
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     *
     * @return
     */
    public BigDecimal getPrice() {
        return rate.multiply(new BigDecimal(getNights()));
    }

    /**
     *
     */
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation | From " + startDate + " | To " + endDate + " | " + Log.currencyToString(getRate()) + " x " + getNights() + " = " + Log.currencyToString(getPrice()) + " | GUEST (" + guest + ") | ROOM (" + room + ")";
    }

}