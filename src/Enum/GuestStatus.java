package Enum;

import java.util.ArrayList;

/**
 * Status for a guest and his hotel reservation
 */
public enum GuestStatus {
    /**
     * Guest that is waiting to make a hotel reservation
     */
    WAITING,
    /**
     * Guest that has a hotel reservation but has still to check-in
     */
    RESERVED,
    /**
     * Guest that has a hotel reservation and has checked-in
     */
    CHECKED_IN,
    /**
     * Guest that has a hotel reservation that has been completed
     */
    CHECKED_OUT,
    /**
     * Guest that decided not to make a reservation at the hotel
     */
    LOST;

    /**
     * List of status that correspond to waiting guests
     */
    public static final ArrayList<GuestStatus> waitingStatus = new ArrayList<>() {
        {add(WAITING);}
    };

    /**
     * List of status that correspond to guests with a reservation but not checked-in
     */
    public static final ArrayList<GuestStatus> reservedStatus = new ArrayList<>() {
        {add(RESERVED);}
    };

    /**
     * List of status that correspond to guests that checked-in
     */
    public static final ArrayList<GuestStatus> checkedInStatus = new ArrayList<>() {
        {add(CHECKED_IN);}
    };

}