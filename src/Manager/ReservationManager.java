package Manager;

import Entity.Guest;
import Entity.Transaction;
import IO.Input;
import KPIs.ReservationKPIs;
import Entity.Reservation;
import Entity.Room;
import Enum.ReservationStatus;
import Enum.RoomStatus;
import Enum.GuestStatus;
import Enum.SetupMode;
import Enum.InputType;
import Enum.TransactionType;
import IO.Log;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ReservationManager {

    private final List<Room> rooms = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final ReservationKPIs reservationKPIs = new ReservationKPIs();

    /**
     *
     * @return
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     *
     * @return
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     *
     * @param room
     */
    public void addRoom(Room room) {
        rooms.add(room);
        Collections.sort(rooms);
    }

    /**
     *
     * @param reservation
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     *
     * @param reservation
     */
    public void checkinGuest(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        Log.printColor(Log.GREEN, "\tGuest (" + reservation.getGuest() + ") checked in to Room (" + reservation.getRoom() + ")");
    }

    /**
     *
     * @param reservation
     */
    public void checkoutGuest(Reservation reservation) {
        reservation.getRoom().setStatus(RoomStatus.FREE);
        reservation.getGuest().setStatus((GuestStatus.STAYED));
        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        Log.printColor(Log.RED, "\tGuest (" + reservation.getGuest() + ") checked out from Room (" + reservation.getRoom() + ")");
    }

    /**
     *
     * @param gameDate
     */
    public void generateCheckins(LocalDate gameDate) {

        Log.printColor(Log.GREEN_UNDERLINED, "CHECK-INS:");

        int checkInsCount = 0;

        for(Reservation reservation: getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED && reservation.getStartDate().isEqual(gameDate)) {
                checkinGuest(reservation);
                checkInsCount++;
            }
        }

        if (checkInsCount == 0) {
            Log.printColor(Log.GREEN, "\t-");
        }
        Log.print("");

    }

    /**
     *
     * @param gameDate
     */
    public void generateCheckouts(LocalDate gameDate) {

        Log.printColor(Log.RED_UNDERLINED, "CHECK-OUTS:");

        int checkOutsCount = 0;
        for(Reservation reservation: getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CHECKED_IN && reservation.getEndDate().isEqual(gameDate)) {
                checkoutGuest(reservation);
                checkOutsCount++;
            }
        }

        if (checkOutsCount == 0) {
            Log.printColor(Log.RED, "\t-");
        }
        Log.print("");

    }

    /**
     *
     * @param room
     * @param guest
     * @param startDate
     * @param endDate
     * @param rate
     */
    public void reserveRoom(Room room, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal rate, FinancialManager financialManager) {
        room.setStatus(RoomStatus.RESERVED);
        guest.setStatus(GuestStatus.STAYING);
        Reservation reservation = new Reservation(room, guest, startDate, endDate, rate);
        addReservation(reservation);
        financialManager.addTransaction(new Transaction(TransactionType.RESERVATION, startDate, reservation.getPrice()));
    }

    /**
     *
     */
    public void generateReservations(SetupMode setupMode, LocalDate gameDate, List<Guest> guests, FinancialManager financialManager) {

        if (setupMode == SetupMode.MANUAL) {
            Log.printColor(Log.WHITE_UNDERLINED, "GUESTS:");
        }

        for(Guest guest: guests) {

            if (guest.getStatus() == GuestStatus.WAITING) {

                if (setupMode == SetupMode.MANUAL) {

                    Log.print("\t" + guest);

                    ArrayList<Room> rooms = subsetRooms(guest.getPeople(), RoomStatus.freeStatus);
                    List<String> roomsOptions = subsetRoomOptions(guest.getPeople(), RoomStatus.freeStatus);

                    if (rooms.isEmpty()) {
                        Log.print("\tThere are no available rooms\n");
                    }

                    else {
                        Log.print("\n\tAvailable Rooms:");
                        Log.print(roomsToString(rooms));

                        int roomNumber = Input.parseNumber(Input.askQuestion("\tChoose Room number to assign to", roomsOptions, InputType.SINGLE_CHOICE_NUMBER));

                        boolean roomFound = false;
                        for (Room room : rooms) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getNumber() == roomNumber) {
                                roomFound = true;
                                reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()), room.getRate(), financialManager);
                            }
                        }
                    }
                }

                else {
                    boolean roomFound = false;
                    for (int size = guest.getPeople(); size <= 4; size++) {
                        for (Room room : getRooms()) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getSizeNumber() == size) {
                                roomFound = true;
                                reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()), room.getRate(), financialManager);
                            }
                        }
                    }

                    if (!roomFound) {
                        guest.setStatus(GuestStatus.LOST);
                    }
                }
            }
        }

        Log.printColor(Log.BLUE_UNDERLINED, "RESERVATIONS:");
        Log.printColor(Log.BLUE, reservationsToString());

    }

    /**
     *
     * @param size
     * @param status
     * @return
     */
    public ArrayList<Room> subsetRooms(int size, ArrayList<RoomStatus> status) {

        ArrayList<Room> subset = new ArrayList<>();

        for(Room item: rooms) {
            if (item.getSizeNumber() >= size) {
                if (status.isEmpty() || status.contains(item.getStatus())) {
                    subset.add(item);
                }
            }
        }

        return subset;

    }

    /**
     *
     * @param size
     * @param status
     * @return
     */
    public ArrayList<String> subsetRoomOptions(int size, ArrayList<RoomStatus> status) {

        ArrayList<String> roomOptions = new ArrayList<>();

        for(Room item: subsetRooms(size, status)) {
            roomOptions.add(String.valueOf(item.getNumber()));
        }

        return roomOptions;

    }

    /**
     *
     * @return
     */
    public ArrayList<String> subsetRoomOptions() {

        ArrayList<String> roomOptions = new ArrayList<>();

        for(Room item: rooms) {
            roomOptions.add(String.valueOf(item.getNumber()));
        }

        return roomOptions;

    }

    /**
     *
     * @param rooms
     * @return
     */
    public String roomsToString(List<Room> rooms) {

        String s = "";

        for(Room item: rooms) {
            s = s + "\t" + item + "\n";
        }

        return s;

    }

    /**
     *
     * @return
     */
    public String reservationsToString() {

        String s = "";

        for(Reservation item: reservations) {
            if (item.getStatus() != ReservationStatus.CHECKED_OUT){
                s = s + "\t" + item + "\n";
            }
        }

        if (s.isEmpty()) {
            s = s + "\t-\n";
        }

        return s;

    }

    /**
     *
     * @param gameDate
     */
    public void reservationSummary(LocalDate gameDate) {
        Log.printColor(Log.WHITE_UNDERLINED, "RESERVATION SUMMARY");
        Log.print("\tOccupancy: " + subsetRooms(0, RoomStatus.reservedStatus).size() + "/" + rooms.size() + " (" + subsetRooms(0, RoomStatus.reservedStatus).size() / rooms.size()+ "%)");
        Log.print("\tRevPAR: " + Log.currencyToString(reservationKPIs.revenuePerAvailableRoom(gameDate, reservations, rooms)));
        Log.print("\tADR: " + Log.currencyToString(reservationKPIs.averageDailyRate(gameDate, reservations)));
    }

}