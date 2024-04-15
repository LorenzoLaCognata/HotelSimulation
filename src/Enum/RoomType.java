package Enum;

/**
 * Type of room from standard rooms to suites
 */
public enum RoomType {
    /**
     * Standard room
     */
    STANDARD,
    /**
     * Superior room
     */
    SUPERIOR,
    /**
     * Deluxe room
     */
    DELUXE,
    /**
     * Junior suite room
     */
    JUNIOR_SUITE,
    /**
     * Suite room
     */
    SUITE;

    /** Converts a string to a RoomType
     *
     * @param string String to convert
     * @return RoomType or null if the string does not match to any RoomType
     */
    public static RoomType parseRoomType(String string) {
        return switch (string) {
            case "STANDARD" -> STANDARD;
            case "SUPERIOR" -> SUPERIOR;
            case "DELUXE" -> DELUXE;
            case "JUNIOR_SUITE" -> JUNIOR_SUITE;
            case "SUITE" -> SUITE;
            default -> STANDARD;
        };
    }

    /** Maximum RoomType possible for a certain RoomSize
     *
     * @param roomSize RoomSize of the room
     * @return Maximum RoomType possible for the room
     */
    public static RoomType maxRoomType(RoomSize roomSize) {

        if (roomSize == RoomSize.SINGLE) {
            return DELUXE;
        }
        else {
            return SUITE;
        }
    }

    /** Maximum RoomType possible for a certain combination of RoomSize and area
     *
     * @param roomSize RoomSize of the room
     * @param area Area in m² of the room
     * @return Maximum RoomType possible for the room
     */
    public static RoomType maxRoomType(RoomSize roomSize, int area) {

        if (roomSize == RoomSize.SINGLE) {
            if (area >= 11) {
                return DELUXE;
            }
        }
        else if (roomSize == RoomSize.DOUBLE) {
            if (area >= 26) {
                return SUITE;
            } else if (area >= 21) {
                return JUNIOR_SUITE;
            } else if (area >= 17) {
                return DELUXE;
            }
        }
        else if (roomSize == RoomSize.TRIPLE) {
            if (area >= 32) {
                return SUITE;
            } else if (area >= 27) {
                return JUNIOR_SUITE;
            } else if (area >= 23) {
                return DELUXE;
            }
        }
        else if (roomSize == RoomSize.QUADRUPLE) {
            if (area >= 38) {
                return SUITE;
            } else if (area >= 33) {
                return JUNIOR_SUITE;
            } else if (area >= 29) {
                return DELUXE;
            }
        }

        return null;
    }

}