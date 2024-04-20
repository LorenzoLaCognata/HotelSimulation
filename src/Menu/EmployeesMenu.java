package Menu;

import Enum.InputType;
import Entity.Employee;
import IO.Input;
import IO.Log;
import Manager.EmployeeManager;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.List;

/**
 *
 */
public class EmployeesMenu {

    /**
     *
     */
    public void employeesShiftsMenu(EmployeeManager employeeManager) {

        String employeesMenuChoice = Input.askQuestion("Select an employee", employeeManager.employeesQuestionChoices(), InputType.SINGLE_CHOICE_TEXT);

        for (Employee item: employeeManager.getEmployees()) {
            if (item.getName().equalsIgnoreCase(employeesMenuChoice)) {
                Log.print("SHIFTS",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
                Log.print(item.toShiftsString());

                String employeeShiftMenuChoice = Input.askQuestion("", List.of("SET SHIFT", "BACK"), InputType.SINGLE_CHOICE_TEXT);

//                if (employeeShiftMenuChoice.equalsIgnoreCase("BACK")) {
//
//                }
                if (employeeShiftMenuChoice.equalsIgnoreCase("SET SHIFT")) {
                    employeeManager.setShift(item);
                    Log.print("SHIFTS",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
                    Log.print(item.toShiftsString());
                }

            }
        }

    }

}
