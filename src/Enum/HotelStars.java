package Enum;

import Entity.Room;

public enum HotelStars {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    /**
     *
     * @param roomType
     * @param roomSize
     * @param area
     * @return
     */
    public static HotelStars maxHotelStars(RoomType roomType, RoomSize roomSize, int area) {
        if (area >= Room.minArea(roomType, roomSize, FIVE)) {
            return FIVE;
        }
        else if (area >= Room.minArea(roomType, roomSize, FOUR)) {
            return FOUR;
        }
        else if (area >= Room.minArea(roomType, roomSize, THREE)) {
            return THREE;
        }
        else if (area >= Room.minArea(roomType, roomSize, TWO)) {
            return TWO;
        }
        else if (area >= Room.minArea(roomType, roomSize, ONE)) {
            return ONE;
        }
        else {
            return null;
        }
    }

}