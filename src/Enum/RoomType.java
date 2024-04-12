package Enum;

public enum RoomType {
    STANDARD,
    SUPERIOR,
    DELUXE,
    JUNIOR_SUITE,
    SUITE;

    /**
     *
     * @param string
     * @return
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

    /**
     *
     * @param roomSize
     * @return
     */
    public static RoomType maxRoomType(RoomSize roomSize) {

        if (roomSize == RoomSize.SINGLE) {
            return DELUXE;
        }
        else {
            return SUITE;
        }
    }

    /**
     *
     * @param roomSize
     * @param area
     * @return
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