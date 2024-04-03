import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import Enum.ReservationStatus;
import Enum.RoomStatus;

public class Hotel {
    private String name;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

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

    public Room getRoomByNumber(int number) {
        for(Room room : this.rooms) {
            if(room.getNumber() == number) {
                return room;
            }
        }
        return null;
    }

    public List<Reservation> getReservations() {
        return this.reservations;
    }

    public BigDecimal getRevenues() {
        BigDecimal revenues = new BigDecimal(0);

        for (Reservation item: this.getReservations()) {
            revenues = revenues.add(item.calculatePrice());
        }

        return revenues;
    }

    // Setter

    void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    // Printer

    public ArrayList<Room> subsetRooms(int size, ArrayList<RoomStatus> status) {

        ArrayList<Room> rooms = new ArrayList<Room>();

        for(Room item: this.rooms) {
            if (item.getSizeNumber() >= size) {
                if (status.isEmpty() || status.contains(item.getStatus())) {
                    rooms.add(item);
                }
            }
        }

        return rooms;

    }

    public ArrayList<String> subsetRoomOptions(int size, ArrayList<RoomStatus> status) {

        ArrayList<String> roomOptions = new ArrayList<String>();

        for(Room item: this.subsetRooms(size, status)) {
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

        for(Reservation item: getReservations()) {
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