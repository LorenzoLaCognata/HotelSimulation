package Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import Enum.TransactionType;
import IO.Log;
import Manager.EmployeeManager;
import Manager.FinancialManager;
import Manager.ReservationManager;

public class Hotel {

    private String name;
    private BigDecimal rent;
    public final EmployeeManager employeeManager = new EmployeeManager();
    public final FinancialManager financialManager = new FinancialManager();
    public final ReservationManager reservationManager = new ReservationManager();

    // Constructor

    public Hotel() {
    }

    // Getter

    public String getName() {
        return name;
    }

    // Setter

    public void setName(String name) {
        this.name = name;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    // Methods

    public void payRent(LocalDate date) {

        Log.printColor(Log.RED_UNDERLINED, "RENT:");

        financialManager.addTransaction(new Transaction(TransactionType.RENT, date, rent.negate()));
        Log.print("\tPaid rent of " + Log.currencyString(rent));
        Log.print("");

    }

    public void paySalaries(LocalDate date) {

        Log.printColor(Log.RED_UNDERLINED, "SALARIES:");

        for(Employee item: employeeManager.getEmployees()) {
            financialManager.addTransaction(new Transaction(TransactionType.SALARY, date, item.getSalary().negate()));
            Log.print("\tPaid salary of " + Log.currencyString(item.getSalary()) + " to " + item);
        }

        Log.print("");

    }
}