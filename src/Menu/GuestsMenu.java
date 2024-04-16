package Menu;

import Enum.InputType;
import Enum.RoomStatus;
import Enum.GuestStatus;
import Entity.Room;
import Entity.Guest;
import IO.Input;
import IO.Log;
import Manager.FinancialManager;
import Manager.ReservationManager;
import Manager.SimulationManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuestsMenu {

    /**
     *
     */
    public void guestReservationsMenuChoice(List<Guest> guests, SimulationManager simulationManager, ReservationManager reservationManager, FinancialManager financialManager) {

        String guestReservationsChoice = Input.askQuestion("Select a guest", simulationManager.subsetGuestOptions(Integer.MAX_VALUE, GuestStatus.waitingStatus), InputType.SINGLE_CHOICE_NUMBER);

        for (Guest item: guests) {

            if (String.valueOf(item.getNumber()).equalsIgnoreCase(guestReservationsChoice)) {

                if (item.getStatus() == GuestStatus.WAITING) {

                    ArrayList<Room> freeRooms = reservationManager.subsetRooms(item.getPeople(), RoomStatus.freeStatus);
                    List<String> roomsOptions = reservationManager.subsetRoomOptions(item.getPeople(), RoomStatus.freeStatus);

                    if (freeRooms.isEmpty()) {
                        Log.print("There are no available rooms\n");
                    }

                    else {
                        Log.print("Available Rooms:");
                        Log.print(reservationManager.roomsToString(freeRooms));

                        int roomNumber = Input.parseNumber(Input.askQuestion("\tChoose Room number to assign the guest to", roomsOptions, InputType.SINGLE_CHOICE_NUMBER));

                        boolean roomFound = false;
                        for (Room room : freeRooms) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getNumber() == roomNumber) {
                                roomFound = true;
                                reservationManager.reserveRoom(room, item, simulationManager.getGameDate(), simulationManager.getGameDate().plusDays(item.getNights()), room.getRate(), financialManager);
                            }
                        }

                    }

                }

            }
        }

        Collections.sort(guests);
        Collections.sort(reservationManager.getRooms());

    }

}
