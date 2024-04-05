package Entity;

import Enum.EmployeeRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final String name;
    private final EmployeeRole role;
    private final BigDecimal salary;
    private final List<Shift> shifts = new ArrayList<>();

    // Constructor

    public Employee(String name, EmployeeRole role, BigDecimal salary) {
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    // Getter

    public List<Shift> getShifts() {
        return shifts;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    // Setter

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    // Methods

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
        return name + " | " + role;
    }

}

