package Entity;

import Manager.EmployeeManager;
import Manager.FinancialManager;
import Manager.ReservationManager;

/**
 *
 */
public class Hotel {

    private String name;
    /**
     *
     */
    public final EmployeeManager employeeManager = new EmployeeManager();
    /**
     *
     */
    public final FinancialManager financialManager = new FinancialManager();
    /**
     *
     */
    public final ReservationManager reservationManager = new ReservationManager();

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}