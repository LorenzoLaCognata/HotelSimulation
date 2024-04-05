package Manager;

import Entity.Employee;
import Entity.Shift;
import IO.Input;
import IO.Log;
import Enum.EmployeeRole;
import Enum.SetupMode;
import Enum.InputType;
import Record.TimePeriod;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private final List<Employee> employees = new ArrayList<>();

    // Getter

    public List<Employee> getEmployees() {
        return employees;
    }

    // Methods

    public void initEmployees(SetupMode setupMode) {

        Employee assistant = new Employee("Alex Goldner", EmployeeRole.ASSISTANT_GENERAL_MANAGER, new BigDecimal(2000));
        Log.print("Meet your new Assistant General Manager!");
        Log.print("\tEMPLOYEE " + assistant + "\n");

        if (setupMode == SetupMode.MANUAL) {

            for (DayOfWeek item: List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)) {
                String timePeriodString = Input.askQuestion("Choose the shift for " + item + " using the format hh:mm-hh:mm or leaving blank", InputType.TIME_INTERVAL);
                TimePeriod timePeriod = TimePeriod.parseTimePeriod(timePeriodString);
                if (timePeriod != null) {
                    assistant.addShift(new Shift(item, timePeriod));
                }
            }

            getEmployees().add(assistant);

        }

        else {
            assistant.addShift(new Shift(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(13, 0))));

            getEmployees().add(assistant);
            Log.printColor(Log.WHITE_UNDERLINED, "EMPLOYEES:");
            Log.print(employeesString());
            Log.print(getEmployees().getFirst().shiftsString());
        }

    }

    public String employeesString() {

        String s = "";

        for(Employee item: getEmployees()) {
            s = s + "\t" + item + "\n";
        }

        return s;

    }
}
