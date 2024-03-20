import java.util.List;
import java.util.ArrayList;

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
            s = s + item + "\n";
        }

        System.out.println("\nROOMS:\n" + s);
    }

}