package KPIs;

import Entity.Reservation;
import Entity.Room;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public class ReservationKPIs {

    /**
     *
     * @param gameDate
     * @param reservations
     * @return
     */
    public BigDecimal averageDailyRate(LocalDate gameDate, List<Reservation> reservations) {

        BigDecimal totalRevenue = new BigDecimal(0);
        int occupiedRooms = 0;

        for (Reservation item: reservations) {
            if (item.getStartDate().isBefore(gameDate) || item.getStartDate().isEqual(gameDate)) {
                if (item.getEndDate().isAfter(gameDate)) {
                    totalRevenue = totalRevenue.add(item.getRate());
                    occupiedRooms++;
                }
            }
        }

        if (occupiedRooms > 0) {
            return totalRevenue.divide(new BigDecimal(occupiedRooms), RoundingMode.DOWN);
        }
        else {
            return new BigDecimal(0);
        }

    }

    /**
     *
     * @param gameDate
     * @param reservations
     * @param rooms
     * @return
     */
    public BigDecimal revenuePerAvailableRoom(LocalDate gameDate, List<Reservation> reservations, List<Room> rooms) {

        BigDecimal totalRevenue = new BigDecimal(0);

        for (Reservation item: reservations) {
            if (item.getStartDate().isBefore(gameDate) || item.getStartDate().isEqual(gameDate)) {
                if (item.getEndDate().isAfter(gameDate)) {
                    totalRevenue = totalRevenue.add(item.getRate());
                }
            }
        }

        return totalRevenue.divide(new BigDecimal(rooms.size()), RoundingMode.DOWN);

    }

}