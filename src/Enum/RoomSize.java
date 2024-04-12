package Enum;

public enum RoomSize {
    SINGLE,
    DOUBLE,
    TRIPLE,
    QUADRUPLE;

    public static RoomSize parseRoomSize(String string) {
        return switch (string) {
            case "SINGLE" -> SINGLE;
            case "DOUBLE" -> DOUBLE;
            case "TRIPLE" -> TRIPLE;
            case "QUADRUPLE" -> QUADRUPLE;
            default -> null;
        };
    }

    /**
     *
     * @param roomType
     * @param hotelStars
     * @param area
     * @return
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