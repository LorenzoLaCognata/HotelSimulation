package Enum;

import Entity.Room;

/**
 * Number of stars for hotels and its rooms and services
 */
public enum HotelStars {
    /**
     * One-star hotel
     */
    ONE,
    /**
     * Two-stars hotel
     */
    TWO,
    /**
     * Three-stars hotel
     */
    THREE,
    /**
     * Four-stars hotel
     */
    FOUR,
    /**
     * Five-stars hotel
     */
    FIVE;

    /** HotelStars rating assigned to a certain combination of RoomType, RoomSize and area
     *
     * @param roomType RoomType of the room
     * @param roomSize RoomSize of the room
     * @param area Area in m² of the room
     * @return HotelStars assigned to the room
     */
    public static HotelStars hotelStarsRating(RoomType roomType, RoomSize roomSize, int area) {
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