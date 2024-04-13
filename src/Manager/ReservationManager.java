package Manager;

import KPIs.ReservationKPIs;
import Entity.Reservation;
import Entity.Room;
import Enum.ReservationStatus;
import Enum.RoomStatus;
import Enum.GuestStatus;
import IO.Log;

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
        Log.print("\tRevPAR: " + Log.currencyString(reservationKPIs.revenuePerAvailableRoom(gameDate, reservations, rooms)));
        Log.print("\tADR: " + Log.currencyString(reservationKPIs.averageDailyRate(gameDate, reservations)));
    }

}