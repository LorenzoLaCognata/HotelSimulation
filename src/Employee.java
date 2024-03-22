import Enum.EmployeeRole;

public class Employee {
    private final String name;
    private final EmployeeRole role;

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

    // Override

    @Override
    public String toString() {
        return "Employee " + this.name + " | " + this.role;
    }

}

