import java.util.List;
import java.util.ArrayList;

public class Hotel {
    private String name;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();

    void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void printRooms() {
        String s = "";
        for(Room item: this.rooms) {
            s = s + item + "\n";
        }

        System.out.println("\nROOMS:\n" + s);
    }
}