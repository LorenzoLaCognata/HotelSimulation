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

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    // Setter

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    // Methods

    public String toShiftsString() {

        String s = "";

        for(Shift item: shifts) {
            s = s + "\t" + item + "\n";
        }

        if (s.isEmpty()) {
            s = "-\n";
        }

        return s;

    }

    // Override

    @Override
    public String toString() {
        return "Employee | " + name + " | " + role;
    }

}

