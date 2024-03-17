public class Room {
    private final int number;
    private final RoomSize size;
    private final RoomType type;
    private final int area;
    private boolean occupied = false;

    public Room(int number, RoomSize roomSize, RoomType roomType, int area) {
        this.number = number;
        this.size = roomSize;
        this.type = roomType;
        this.area = area;
    }

    @Override
    public String toString() {
        return "Room " + this.number + " | " + this.size + " | " + this.type + " | " + this.area + " m² | " + maxHotelStars(this.type, this.size, this.area) + " *";
    }

    public static RoomType stringToRoomType(String string) {
        return switch (string) {
            case "STANDARD" -> RoomType.STANDARD;
            case "SUPERIOR" -> RoomType.SUPERIOR;
            case "DELUXE" -> RoomType.DELUXE;
            case "JUNIOR_SUITE" -> RoomType.JUNIOR_SUITE;
            case "SUITE" -> RoomType.SUITE;
            default -> null;
        };
    }

    public static RoomSize stringToRoomSize(String string) {
        return switch (string) {
            case "SINGLE" -> RoomSize.SINGLE;
            case "DOUBLE" -> RoomSize.DOUBLE;
            case "TRIPLE" -> RoomSize.TRIPLE;
            case "QUADRUPLE" -> RoomSize.QUADRUPLE;
            default -> null;
        };
    }

    public static HotelStars stringToHotelStars(String string) {
        return switch (string) {
            case "ONE" -> HotelStars.ONE;
            case "TWO" -> HotelStars.TWO;
            case "THREE" -> HotelStars.THREE;
            case "FOUR" -> HotelStars.FOUR;
            case "FIVE" -> HotelStars.FIVE;
            default -> null;
        };
    }

    public static Integer parseArea(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int minArea(RoomType roomType, RoomSize roomSize, HotelStars hotelStars) {
        return switch (roomType) {
            case RoomType.STANDARD, RoomType.SUPERIOR, RoomType.DELUXE -> switch (roomSize) {
                case RoomSize.SINGLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 11;
                    case FOUR -> 12;
                    case FIVE -> 14;
                };
                case RoomSize.DOUBLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 17;
                    case FOUR -> 19;
                    case FIVE -> 21;
                };
                case RoomSize.TRIPLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 23;
                    case FOUR -> 25;
                    case FIVE -> 29;
                };
                case RoomSize.QUADRUPLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 29;
                    case FOUR -> 31;
                    case FIVE -> 37;
                };
            };
            case RoomType.JUNIOR_SUITE -> switch (roomSize) {
                case RoomSize.SINGLE -> -1;
                case RoomSize.DOUBLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 21;
                    case FOUR -> 24;
                    case FIVE -> 25;
                };
                case RoomSize.TRIPLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 27;
                    case FOUR -> 30;
                    case FIVE -> 31;
                };
                case RoomSize.QUADRUPLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 33;
                    case FOUR -> 36;
                    case FIVE -> 37;
                };
            };
            case RoomType.SUITE -> switch (roomSize) {
                case RoomSize.SINGLE -> -1;
                case RoomSize.DOUBLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 26;
                    case FOUR -> 32;
                    case FIVE -> 33;
                };
                case RoomSize.TRIPLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 32;
                    case FOUR -> 38;
                    case FIVE -> 39;
                };
                case RoomSize.QUADRUPLE -> switch (hotelStars) {
                    case ONE, TWO, THREE -> 38;
                    case FOUR -> 44;
                    case FIVE -> 45;
                };
            };
        };
    }

    public static HotelStars maxHotelStars(RoomType roomType, RoomSize roomSize, int area) {
        if (area >= minArea(roomType, roomSize, HotelStars.FIVE)) {
            return HotelStars.FIVE;
        }
        else if (area >= minArea(roomType, roomSize, HotelStars.FOUR)) {
            return HotelStars.FOUR;
        }
        else if (area >= minArea(roomType, roomSize, HotelStars.THREE)) {
            return HotelStars.THREE;
        }
        else if (area >= minArea(roomType, roomSize, HotelStars.TWO)) {
            return HotelStars.TWO;
        }
        else if (area >= minArea(roomType, roomSize, HotelStars.ONE)) {
            return HotelStars.ONE;
        }
        else {
            return null;
        }
    }

}