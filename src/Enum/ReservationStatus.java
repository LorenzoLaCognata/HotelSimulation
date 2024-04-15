package Enum;

/**
 * Status of a reservation for a room
 */
public enum ReservationStatus {
    /**
     * Confirmed reservation
     */
    CONFIRMED,
    /**
     * Reservation for which guests have completed the check-in
     */
    CHECKED_IN,
    /**
     * Reservation for which guests have completed the check-out
     */
    CHECKED_OUT
}

