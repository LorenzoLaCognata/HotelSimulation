import Enum.EmployeeRole;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final String name;
    private final EmployeeRole role;
    private final List<Shift> shifts = new ArrayList<>();

    // Constructor

    public Employee(String name, EmployeeRole role) {
        this.name = name;
        this.role = role;
    }

    // Getter

    public String getName() {
        return name;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    // Setter

    public void addShift(Shift shift) {
        this.shifts.add(shift);
    }

    // Printer

    public String shiftsString() {

        String s = "SHIFTS:\n";

        for(Shift item: getShifts()) {
            s = s + "\t" + item + "\n";
        }

        return s;

    }

    // Override

    @Override
    public String toString() {
        return this.name + " | " + this.role;
    }

}

