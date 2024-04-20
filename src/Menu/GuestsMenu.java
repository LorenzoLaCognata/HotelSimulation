package Menu;

import Entity.Reservation;
import Enum.InputType;
import Enum.RoomStatus;
import Enum.GuestStatus;
import Enum.ReservationStatus;
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
                                reservationManager.reserveRoom(room, item, item.getStartDate(), item.getEndDate(), room.getRate(), financialManager);
                            }
                        }

                    }

                }

            }
        }

        Collections.sort(guests);
        Collections.sort(reservationManager.getRooms());

    }

    /**
     *
     */
    public void guestCheckInsMenuChoice(List<Guest> guests, SimulationManager simulationManager, ReservationManager reservationManager) {

        String guestCheckInsChoice = Input.askQuestion("Select a guest", simulationManager.subsetGuestOptions(Integer.MAX_VALUE, GuestStatus.reservedStatus), InputType.SINGLE_CHOICE_NUMBER);

        for (Guest g: guests) {

            if (String.valueOf(g.getNumber()).equalsIgnoreCase(guestCheckInsChoice)) {

                if (g.getStatus() == GuestStatus.RESERVED) {

                    for (Reservation r: reservationManager.getReservations()) {

                        if (r.getGuest().equals(g)) {

                            if (r.getStatus() == ReservationStatus.CONFIRMED && (r.getStartDate().isEqual(simulationManager.getGameDate()) || r.getStartDate().isBefore(simulationManager.getGameDate()))) {
                                reservationManager.checkinGuest(r);
                            }

                            else {
                                Log.printColor(Log.RED, "Cannot check in this guest\n");
                            }
                        }

                    }

                }

            }
        }

        Collections.sort(guests);
        Collections.sort(reservationManager.getRooms());

    }

    /**
     *
     */
    public void guestCheckOutsMenuChoice(List<Guest> guests, SimulationManager simulationManager, ReservationManager reservationManager) {

        String guestCheckOutsChoice = Input.askQuestion("Select a guest", simulationManager.subsetGuestOptions(Integer.MAX_VALUE, GuestStatus.checkedInStatus), InputType.SINGLE_CHOICE_NUMBER);

        for (Guest g: guests) {

            if (String.valueOf(g.getNumber()).equalsIgnoreCase(guestCheckOutsChoice)) {

                if (g.getStatus() == GuestStatus.CHECKED_IN) {

                    for (Reservation r: reservationManager.getReservations()) {

                        if (r.getGuest().equals(g)) {

                            if (r.getStatus() == ReservationStatus.CHECKED_IN && (r.getEndDate().isEqual(simulationManager.getGameDate()) || r.getEndDate().isAfter(simulationManager.getGameDate()))) {
                                reservationManager.checkoutGuest(r);
                            }

                            else {
                                Log.printColor(Log.RED, "Cannot check out this guest\n");
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
