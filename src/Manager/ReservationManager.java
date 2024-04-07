package Manager;

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

public class ReservationManager {

    private final List<Room> rooms = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    // Getter

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    // Setter

    public void addRoom(Room room) {
        rooms.add(room);
        Collections.sort(rooms);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Methods

    public void checkinGuest(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        Log.printColor(Log.GREEN, "\tGuest (" + reservation.getGuest() + ") checked in to Room (" + reservation.getRoom() + ")");
    }

    public void checkoutGuest(Reservation reservation) {
        reservation.getRoom().setStatus(RoomStatus.FREE);
        reservation.getGuest().setStatus((GuestStatus.STAYED));
        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        Log.printColor(Log.RED, "\tGuest (" + reservation.getGuest() + ") checked out from Room (" + reservation.getRoom() + ")");
    }

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

    public ArrayList<String> subsetRoomOptions(int size, ArrayList<RoomStatus> status) {

        ArrayList<String> roomOptions = new ArrayList<>();

        for(Room item: subsetRooms(size, status)) {
            roomOptions.add(String.valueOf(item.getNumber()));
        }

        return roomOptions;

    }

    public ArrayList<String> subsetRoomOptions() {

        ArrayList<String> roomOptions = new ArrayList<>();

        for(Room item: rooms) {
            roomOptions.add(String.valueOf(item.getNumber()));
        }

//        rooms.sort();

        return roomOptions;

    }

    public String roomsString(List<Room> rooms) {

        String s = "";

        for(Room item: rooms) {
            s = s + "\t" + item + "\n";
        }

        return s;

    }

    public String reservationsString() {

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

}
