package Manager;

import Entity.Employee;
import Entity.Shift;
import IO.Input;
import IO.Log;
import Enum.EmployeeRole;
import Enum.InputType;
import Entity.TimePeriod;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class EmployeeManager {

    private final List<Employee> employees = new ArrayList<>();

    /**
     *
     * @return
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     *
     */
    public void defaultEmployee() {

        Log.print("EMPLOYEES",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);

        Employee assistant = new Employee("Alex Goldner", EmployeeRole.ASSISTANT_GENERAL_MANAGER, new BigDecimal(2000));
        Log.print("\tMeet your new Assistant General Manager!");
        Log.print("\t" + assistant);
        Log.print("");

        employees.add(assistant);

    }

    /**
     *
     * @param employee
     */
    public void setShift(Employee employee) {

        for (DayOfWeek item: List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)) {
            String timePeriodString = Input.askQuestion("Choose the shift for " + item + " using the format hh:mm-hh:mm or leaving blank", InputType.TIME_INTERVAL);
            TimePeriod timePeriod = TimePeriod.parseTimePeriod(timePeriodString);
            if (timePeriod != null) {
                employee.addShift(new Shift(item, timePeriod));
            }
        }

    }

    /**
     *
     * @return
     */
    public String employeesToString() {

        String s = "";

        for(Employee item: employees) {
            s = s + "\t" + item + "\n";
        }

        return s;

    }

    /**
     *
     * @return
     */
    public List<String> employeesQuestionChoices() {

        List<String> list = new ArrayList<>();

        for(Employee item: employees) {
            list.add(item.getName());
        }

        return list;

    }

}
