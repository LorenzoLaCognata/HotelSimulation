package Entity;

import Enum.RoomSize;
import Enum.RoomType;
import Enum.RoomStatus;
import Enum.HotelStars;
import IO.Log;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        return "Room " + number + " | " + size + " | " + type + " | " + area + " m² | " + Log.currencyString(rate) + " | " + HotelStars.maxHotelStars(type, size, area) + " * | " + status;
    }

    @Override
    public int compareTo(@NotNull Room other) {

        int thisNumber = getSizeNumber()*100 + getTypeNumber()*10 + getNumber();
        int otherNumber = other.getSizeNumber()*100 + other.getTypeNumber()*10 + other.getNumber();
        return Integer.compare(thisNumber, otherNumber);

    }

    // Methods

    /**
     * Minimum allowed area for a room of a specific RoomType, RoomSize and HotelStars
     * @param roomType RoomType of the room
     * @param roomSize RoomSize of the room
     * @param hotelStars HotelStars of the room
     * @return Area in m2 for the room
     */
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

}