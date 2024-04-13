package Enum;

import java.util.ArrayList;

public enum RoomStatus {
    /**
     * Room that is not reserved by any guest
     */
    FREE,
    /**
     * Room that is reserved by some guest
     */
    RESERVED;

    /**
     * List of status that correspond to non-reserved rooms
     */
    public static final ArrayList<RoomStatus> freeStatus = new ArrayList<>() {
        {add(FREE);}
    };

    /**
     * List of status that correspond to reserved rooms
     */
    public static final ArrayList<RoomStatus> reservedStatus = new ArrayList<>() {
        {add(RESERVED);}
    };

}