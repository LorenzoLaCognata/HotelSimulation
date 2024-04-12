package Entity;

import Enum.RoomSize;
import Enum.RoomType;
import Enum.RoomStatus;
import Enum.HotelStars;
import IO.Log;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Room implements Comparable<Room> {
    private final int number;
    private RoomSize size;
    private RoomType type;
    private final int area;
    private BigDecimal rate;
    private RoomStatus status = RoomStatus.FREE;

    public static final ArrayList<RoomStatus> freeStatus = new ArrayList<>() {
        {add(RoomStatus.FREE);}
    };

    public static final ArrayList<RoomStatus> reservedStatus = new ArrayList<>() {
        {add(RoomStatus.RESERVED);}
    };

    // Constructor

    public Room(int number, RoomSize roomSize, RoomType roomType, int area, BigDecimal rate) {
        this.number = number;
        this.size = roomSize;
        this.type = roomType;
        this.area = area;
        this.rate = rate;
    }

    // Getter

    public int getNumber() {
        return number;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public RoomSize getSize() {
        return size;
    }
    
    public int getArea() {
        return area;
    }

    public RoomStatus getStatus() {
        return status;
    }

    // Setter

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setSize(RoomSize size) {
        this.size = size;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    // Override

    @Override
    public String toString() {
        return "Room " + number + " | " + size + " | " + type + " | " + area + " m² | " + Log.currencyString(rate) + " | " + maxHotelStars(type, size, area) + " * | " + status;
    }

    @Override
    public int compareTo(@NotNull Room other) {

        int thisNumber = getSizeNumber()*100 + getTypeNumber()*10 + getNumber();
        int otherNumber = other.getSizeNumber()*100 + other.getTypeNumber()*10 + other.getNumber();
        return Integer.compare(thisNumber, otherNumber);

    }

    // Methods

    public int getSizeNumber() {
        if (size == RoomSize.SINGLE) {
            return 1;
        }
        else if (size == RoomSize.DOUBLE) {
            return 2;
        }
        else if (size == RoomSize.TRIPLE) {
            return 3;
        }
        else if (size == RoomSize.QUADRUPLE) {
            return 4;
        }
        else {
            return 0;
        }
    }

    public int getTypeNumber() {
        if (type == RoomType.STANDARD) {
            return 1;
        }
        else if (type == RoomType.SUPERIOR) {
            return 2;
        }
        else if (type == RoomType.DELUXE) {
            return 3;
        }
        else if (type == RoomType.JUNIOR_SUITE) {
            return 4;
        }
        else if (type == RoomType.SUITE) {
            return 5;
        }
        else {
            return 0;
        }
    }

    public static RoomType stringToRoomType(String string) {
        return switch (string) {
            case "STANDARD" -> RoomType.STANDARD;
            case "SUPERIOR" -> RoomType.SUPERIOR;
            case "DELUXE" -> RoomType.DELUXE;
            case "JUNIOR_SUITE" -> RoomType.JUNIOR_SUITE;
            case "SUITE" -> RoomType.SUITE;
            default -> RoomType.STANDARD;
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

    public static List<String> allowedRoomSizes(RoomSize maxRoomSize) {

        List<String> allowedRoomSizes = new ArrayList<>();
        if (maxRoomSize == RoomSize.SINGLE || maxRoomSize == RoomSize.DOUBLE || maxRoomSize == RoomSize.TRIPLE || maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("SINGLE");
        }
        if (maxRoomSize == RoomSize.DOUBLE || maxRoomSize == RoomSize.TRIPLE || maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("DOUBLE");
        }
        if (maxRoomSize == RoomSize.TRIPLE || maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("TRIPLE");
        }
        if (maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("QUADRUPLE");
        }
        return allowedRoomSizes;

    }

    public static RoomType maxRoomType(RoomSize roomSize) {

        if (roomSize == RoomSize.SINGLE) {
            return RoomType.DELUXE;
        }
        else {
            return RoomType.SUITE;
        }
    }

    public static List<String> allowedRoomTypes(RoomType maxRoomType) {

        List<String> allowedRoomTypes = new ArrayList<>();
        if (maxRoomType == RoomType.STANDARD || maxRoomType == RoomType.SUPERIOR || maxRoomType == RoomType.DELUXE || maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("STANDARD");
        }
        if (maxRoomType == RoomType.SUPERIOR || maxRoomType == RoomType.DELUXE || maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("SUPERIOR");
        }
        if (maxRoomType == RoomType.DELUXE || maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("DELUXE");
        }
        if (maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("JUNIOR_SUITE");
        }
        if (maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("SUITE");
        }
        return allowedRoomTypes;

    }

    public static RoomSize maxRoomSize(RoomType roomType, HotelStars hotelStars, int area) {

        if (roomType == RoomType.STANDARD || roomType == RoomType.SUPERIOR || roomType == RoomType.DELUXE) {
            if (hotelStars == HotelStars.ONE || hotelStars == HotelStars.TWO || hotelStars == HotelStars.THREE) {
                if (area >= 29) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 23) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 17) {
                    return RoomSize.DOUBLE;
                }
                else if (area >= 11) {
                    return RoomSize.SINGLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FOUR) {
                if (area >= 31) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 25) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 15) {
                    return RoomSize.DOUBLE;
                }
                else if (area >= 12) {
                    return RoomSize.SINGLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FIVE) {
                if (area >= 35) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 29) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 17) {
                    return RoomSize.DOUBLE;
                }
                else if (area >= 14) {
                    return RoomSize.SINGLE;
                }
                else {
                    return null;
                }
            }
        }

        else if (roomType == RoomType.JUNIOR_SUITE) {
            if (hotelStars == HotelStars.ONE || hotelStars == HotelStars.TWO || hotelStars == HotelStars.THREE) {
                if (area >= 33) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 27) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 21) {
                    return RoomSize.DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FOUR) {
                if (area >= 36) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 30) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 24) {
                    return RoomSize.DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FIVE) {
                if (area >= 37) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 31) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 25) {
                    return RoomSize.DOUBLE;
                }
                else {
                    return null;
                }
            }
        }

        else if (roomType == RoomType.SUITE) {
            if (hotelStars == HotelStars.ONE || hotelStars == HotelStars.TWO || hotelStars == HotelStars.THREE) {
                if (area >= 38) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 32) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 26) {
                    return RoomSize.DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FOUR) {
                if (area >= 44) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 38) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 32) {
                    return RoomSize.DOUBLE;
                }
                else {
                    return null;
                }
            }
            else if (hotelStars == HotelStars.FIVE) {
                if (area >= 45) {
                    return RoomSize.QUADRUPLE;
                }
                else if (area >= 39) {
                    return RoomSize.TRIPLE;
                }
                else if (area >= 33) {
                    return RoomSize.DOUBLE;
                }
                else {
                    return null;
                }
            }
        }

        return null;
    }

    public static RoomType maxRoomType(RoomSize roomSize, int area) {

        if (roomSize == RoomSize.SINGLE) {
            if (area >= 11) {
                return RoomType.DELUXE;
            }
        }
        else if (roomSize == RoomSize.DOUBLE) {
            if (area >= 26) {
                return RoomType.SUITE;
            } else if (area >= 21) {
                return RoomType.JUNIOR_SUITE;
            } else if (area >= 17) {
                return RoomType.DELUXE;
            }
        }
        else if (roomSize == RoomSize.TRIPLE) {
            if (area >= 32) {
                return RoomType.SUITE;
            } else if (area >= 27) {
                return RoomType.JUNIOR_SUITE;
            } else if (area >= 23) {
                return RoomType.DELUXE;
            }
        }
        else if (roomSize == RoomSize.QUADRUPLE) {
            if (area >= 38) {
                return RoomType.SUITE;
            } else if (area >= 33) {
                return RoomType.JUNIOR_SUITE;
            } else if (area >= 29) {
                return RoomType.DELUXE;
            }
        }

        return null;
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