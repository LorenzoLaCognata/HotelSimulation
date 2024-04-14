package Menu;

import Enum.InputType;
import Enum.RoomType;
import Enum.RoomSize;
import Enum.HotelStars;
import Entity.Room;
import IO.Input;
import Manager.ReservationManager;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class RoomsMenu {

    /**
     *
     */
    public void roomSizeMenuChoice(ReservationManager reservationManager) {

        String roomRateMenuChoice = Input.askQuestion("Select a room", reservationManager.subsetRoomOptions(), InputType.SINGLE_CHOICE_NUMBER);

        for (Room item: reservationManager.getRooms()) {

            if (String.valueOf(item.getNumber()).equalsIgnoreCase(roomRateMenuChoice)) {

                List<String> questionOptions = Input.roomSizeOptions(RoomSize.maxRoomSize(RoomType.STANDARD, HotelStars.ONE, item.getArea()));
                String answer = Input.askQuestion("Choose the size for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                RoomSize size = RoomSize.parseRoomSize(answer);
                item.setSize(size);

            }
        }

        Collections.sort(reservationManager.getRooms());

    }

    /**
     *
     */
    public void roomRateMenuChoice(ReservationManager reservationManager) {

        String roomRateMenuChoice = Input.askQuestion("Select a room", reservationManager.subsetRoomOptions(), InputType.SINGLE_CHOICE_NUMBER);

        for (Room item: reservationManager.getRooms()) {
            if (String.valueOf(item.getNumber()).equalsIgnoreCase(roomRateMenuChoice)) {

                BigDecimal rate = new BigDecimal(Input.askQuestion("Choose the daily rate in € for this room", 0, Integer.MAX_VALUE));
                item.setRate(rate);
            }
        }

        Collections.sort(reservationManager.getRooms());

    }

    /**
     *
     */
    public void roomTypeMenuChoice(ReservationManager reservationManager) {

        String roomRateMenuChoice = Input.askQuestion("Select a room", reservationManager.subsetRoomOptions(), InputType.SINGLE_CHOICE_NUMBER);

        for (Room item: reservationManager.getRooms()) {

            if (String.valueOf(item.getNumber()).equalsIgnoreCase(roomRateMenuChoice)) {

                List<String> questionOptions = Input.roomTypeOptions(RoomType.maxRoomType(item.getSize(), item.getArea()));
                String answer = Input.askQuestion("Choose the type for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                RoomType type = RoomType.parseRoomType(answer);
                item.setType(type);

            }
        }

        Collections.sort(reservationManager.getRooms());

    }

}