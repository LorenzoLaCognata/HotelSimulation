import java.util.List;
import java.util.ArrayList;
import Enum.ReservationStatus;

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

    // Setter

    void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    // Printer

    public void printRooms() {
        String s = "";
        for(Room item: this.rooms) {
            s = s + "\t" + item + "\n";
        }

        System.out.println("\nROOMS:\n" + s);
    }

    public void printEmployees() {
        String s = "";
        for(Employee item: getEmployees()) {
            s = s + "\t" + item + "\n";
        }

        System.out.println("EMPLOYEES:\n" + s);
    }

    public void printReservations() {
        String s = "";
        for(Reservation item: getReservations()) {
            if (item.getStatus() != ReservationStatus.CHECKED_OUT){
                s = s + "\t" + item + "\n";
            }
        }

        System.out.println("RESERVATIONS:\n" + s);
    }


}