package Enum;

/**
 * Status for a guest and his hotel reservation
 */
public enum GuestStatus {
    /**
     * Guest that is waiting to make a hotel reservation
     */
    WAITING,
    /**
     * Guest that has a hotel reservation that is in progress
     */
    STAYING,
    /**
     * Guest that has a hotel reservation that has been completed
     */
    STAYED,
    /**
     * Guest that decided not to make a reservation at the hotel
     */
    LOST
}