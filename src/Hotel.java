import java.util.List;
import java.util.ArrayList;

public class Hotel {
    private String name;
    private final List<Room> rooms = new ArrayList<>();
    private List<Employee> employees;
    private List<Guest> guests;
//    private FinancialManager financialManager;
//    private MarketingManager marketingManager;
//    private CompetitorManager competitorManager;

    void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public String getRooms() {
//        return this.rooms.toString();
        String s = "";
        for(Room item: this.rooms) {
            s = s + item + "\n";
        }

        return s;
    }
}