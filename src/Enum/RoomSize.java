package Enum;

/**
 * Size of the room in terms of number of guests that it can accomodate
 */
public enum RoomSize {
    /**
     * Single room
     */
    SINGLE,
    /**
     * Double room
     */
    DOUBLE,
    /**
     * Triple room
     */
    TRIPLE,
    /**
     * Quadruple room
     */
    QUADRUPLE;

    /** Converts a string to a RoomSize
     *
     * @param string String to convert
     * @return RoomSize or null if the string does not match to any RoomSize
     */
    public static RoomSize parseRoomSize(String string) {
        return switch (string) {
            case "SINGLE" -> SINGLE;
            case "DOUBLE" -> DOUBLE;
            case "TRIPLE" -> TRIPLE;
            case "QUADRUPLE" -> QUADRUPLE;
            default -> null;
        };
    }

    /** Maximum RoomSize possible for a certain combination of RoomType, HotelStars and area
     *
     * @param roomType RoomType of the room
     * @param hotelStars HotelStars of the room, use ONE in case there is no requirement of maintaining a specific HotelStars level
     * @param area Area in m² of the room
     * @return Maximum RoomSize possible for the room
     */
    public static RoomSize maxRoomSize(RoomType roomType, HotelStars hotelStars, int area) {

        if (roomType == RoomType.STANDARD || roomType == RoomType.SUPERIOR || roomType == RoomType.DELUXE) {
            if (hotelStars == HotelStars.ONE || hotelStars == HotelStars.TWO || hotelStars == HotelStars.THREE) {
                if (area >= 29) {
                    return QUADRUPLE;
                }
                else if (area >= 23) {
                    return TRIPLE;
                }
                else if (area >= 17) {
                    return DOUBLE;
                }
                else if (area >= 11) {
                    return SINGLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FOUR) {
                if (area >= 31) {
                    return QUADRUPLE;
                }
                else if (area >= 25) {
                    return TRIPLE;
                }
                else if (area >= 15) {
                    return DOUBLE;
                }
                else if (area >= 12) {
                    return SINGLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FIVE) {
                if (area >= 35) {
                    return QUADRUPLE;
                }
                else if (area >= 29) {
                    return TRIPLE;
                }
                else if (area >= 17) {
                    return DOUBLE;
                }
                else if (area >= 14) {
                    return SINGLE;
                }
                else {
                    return null;
                }
            }
        }

        else if (roomType == RoomType.JUNIOR_SUITE) {
            if (hotelStars == HotelStars.ONE || hotelStars == HotelStars.TWO || hotelStars == HotelStars.THREE) {
                if (area >= 33) {
                    return QUADRUPLE;
                }
                else if (area >= 27) {
                    return TRIPLE;
                }
                else if (area >= 21) {
                    return DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FOUR) {
                if (area >= 36) {
                    return QUADRUPLE;
                }
                else if (area >= 30) {
                    return TRIPLE;
                }
                else if (area >= 24) {
                    return DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FIVE) {
                if (area >= 37) {
                    return QUADRUPLE;
                }
                else if (area >= 31) {
                    return TRIPLE;
                }
                else if (area >= 25) {
                    return DOUBLE;
                }
                else {
                    return null;
                }
            }
        }

        else if (roomType == RoomType.SUITE) {
            if (hotelStars == HotelStars.ONE || hotelStars == HotelStars.TWO || hotelStars == HotelStars.THREE) {
                if (area >= 38) {
                    return QUADRUPLE;
                }
                else if (area >= 32) {
                    return TRIPLE;
                }
                else if (area >= 26) {
                    return DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FOUR) {
                if (area >= 44) {
                    return QUADRUPLE;
                }
                else if (area >= 38) {
                    return TRIPLE;
                }
                else if (area >= 32) {
                    return DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FIVE) {
                if (area >= 45) {
                    return QUADRUPLE;
                }
                else if (area >= 39) {
                    return TRIPLE;
                }
                else if (area >= 33) {
                    return DOUBLE;
                }
                else {
                    return null;
                }
            }
        }

        return null;
    }

}