import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import Entity.Employee;
import Entity.Reservation;
import Entity.Room;
import Entity.Transaction;
import Enum.ReservationStatus;
import Enum.RoomStatus;
import Enum.TransactionType;
import IO.Log;
import Manager.FinancialManager;

public class Hotel {
    private String name;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public final FinancialManager financialManager = new FinancialManager();

    // Constructor

    public Hotel() {
    }

    // Getter

    public String getName() {
        return name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    // Setter

    void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Printer

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

    public String roomsString(List<Room> rooms) {

        String s = "";

        for(Room item: rooms) {
            s = s + "\t" + item + "\n";
        }

        return s;

    }

    public String employeesString() {

        String s = "";

        for(Employee item: getEmployees()) {
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

    public void paySalaries(LocalDate date) {

        Log.printColor(Log.RED_UNDERLINED, "SALARIES:");

        for(Employee item: employees) {
            financialManager.addTransaction(new Transaction(TransactionType.SALARY, date, item.getSalary().negate()));
            Log.print("\tPaid salary of " + Log.currencyString(item.getSalary()) + " to " + item);
        }

        Log.print("");

    }
}