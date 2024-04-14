package Manager;

import Entity.Guest;
import Entity.Hotel;
import Enum.GuestStatus;
import Enum.InputType;
import Enum.SetupMode;
import IO.Input;
import IO.Log;
import Menu.EmployeesMenu;
import Menu.RoomsMenu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class SimulationManager {

    private final Hotel hotel = new Hotel();
    private final List<Guest> guests = new ArrayList<>();
    private final RoomsMenu roomsMenu = new RoomsMenu();
    private final EmployeesMenu employeesMenu = new EmployeesMenu();

    private static final Random random = new Random();
    private static LocalDate gameDate = LocalDate.of (2024,1,1);
    private static final Double reservationRate = 0.15;
    private static SetupMode setupMode = SetupMode.AUTOMATIC;

    /**
     *
     */
    public SimulationManager() {

        Log.initLog();

        String hotelSetupString = Input.askQuestion("Setup mode - How do you want to insert your input", List.of("MANUALLY", "AUTOMATICALLY"), InputType.SINGLE_CHOICE_TEXT);

        if (hotelSetupString.equalsIgnoreCase("MANUALLY")) {
            setupMode = SetupMode.MANUAL;
        }

        hotel.initHotel(setupMode, gameDate, random);
    }

    /**
     *
     * @param gameDate
     */
    public static void setGameDate(LocalDate gameDate) {
        SimulationManager.gameDate = gameDate;
    }

    /**
     *
     */
    public void mainMenu() {

        Log.printColor(Log.WHITE_UNDERLINED, "MAIN MENU");
        String mainMenuChoice = Input.askQuestion("", List.of("GUESTS", "ROOMS", "EMPLOYEES", "ADVANCE DATE", "QUIT"), InputType.SINGLE_CHOICE_TEXT);

        if (mainMenuChoice.equalsIgnoreCase("GUESTS")) {
            Log.print("Page under construction (note: RESERVATIONS, CHECK-INS, CHECK-OUTS)");
            Log.print("");
            mainMenu();
        }
        else if (mainMenuChoice.equalsIgnoreCase("ROOMS")) {
            roomsMenuChoice();
        }
        else if (mainMenuChoice.equalsIgnoreCase("EMPLOYEES")) {
            employeesMenuChoice();
        }
        else if (mainMenuChoice.equalsIgnoreCase("ADVANCE DATE")) {
            advanceDate();
            mainMenu();
        }
        else if (mainMenuChoice.equalsIgnoreCase("QUIT")) {
            System.exit(0);
        }

    }

    /**
     *
     */
    public void roomsMenuChoice() {

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(hotel.reservationManager.roomsToString(hotel.reservationManager.getRooms()));

        String roomsMenuChoice = Input.askQuestion("", List.of("SET RATE", "SET TYPE", "SET SIZE", "BACK"), InputType.SINGLE_CHOICE_TEXT);

        if (roomsMenuChoice.equalsIgnoreCase("BACK")) {
            mainMenu();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("SET RATE")) {
            roomsMenu.roomRateMenuChoice(hotel.reservationManager);
            roomsMenuChoice();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("SET TYPE")) {
            roomsMenu.roomTypeMenuChoice(hotel.reservationManager);
            roomsMenuChoice();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("SET SIZE")) {
            roomsMenu.roomSizeMenuChoice(hotel.reservationManager);
            roomsMenuChoice();
        }

    }

    /**
     *
     */
    public void employeesMenuChoice() {

        Log.printColor(Log.WHITE_UNDERLINED, "EMPLOYEES");
        Log.print(hotel.employeeManager.employeesToString());

        String employeesMenuChoice = Input.askQuestion("", List.of("SHIFTS", "BACK"), InputType.SINGLE_CHOICE_TEXT);

        if (employeesMenuChoice.equalsIgnoreCase("BACK")) {
            mainMenu();
        }
        else if (employeesMenuChoice.equalsIgnoreCase("SHIFTS")) {
            employeesMenu.employeesShiftsMenu(hotel.employeeManager);
            employeesMenuChoice();
        }

    }

    /**
     *
     * @param days
     */
    public void simulate(int days) {

        for (int i = 0; i < days; i++) {

            setGameDate(gameDate.plusDays(1));
            Log.print("--------------------------------------\n");
            Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate + "\n");

            if (gameDate.getDayOfMonth() == gameDate.lengthOfMonth()) {
                hotel.financialManager.payRent(gameDate);
                hotel.financialManager.paySalaries(gameDate, hotel.employeeManager.getEmployees());
            }

            generateGuests();

            hotel.reservationManager.generateCheckouts(gameDate);

            Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
            Log.print(hotel.reservationManager.roomsToString(hotel.reservationManager.getRooms()));

            hotel.reservationManager.generateReservations(setupMode, gameDate, guests, hotel.financialManager);

            hotel.reservationManager.generateCheckins(gameDate);

            Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
            Log.print(hotel.reservationManager.roomsToString(hotel.reservationManager.getRooms()));

            hotel.financialManager.financialSummary();

        }

    }

    /**
     *
     */
    public void advanceDate() {

        setGameDate(gameDate.plusDays(1));
        Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate);
        Log.print("");

        hotel.financialManager.financialSummary();
        Log.print("");

        hotel.reservationManager.reservationSummary(gameDate);
        Log.print("");

        if (gameDate.getDayOfMonth() == 1) {
            hotel.financialManager.payRent(gameDate);
            hotel.financialManager.paySalaries(gameDate, hotel.employeeManager.getEmployees());
        }

    }

    /**
     *
     */
    public void generateGuests() {

        for (int i = 0; i < 10; i++) {

            if (random.nextDouble() <= reservationRate) {
                double gaussian = random.nextGaussian();

                while (gaussian < -1.0 || gaussian > 1.0) {
                    gaussian = random.nextGaussian();
                }

                int people = 1 + (int) Math.round((gaussian + 1.0) * 1.5);
                int nights = random.nextInt(1, 7);
                guests.add(new Guest(people, gameDate, gameDate.plusDays(nights)));
            }
        }

        if (setupMode == SetupMode.AUTOMATIC) {
            guestsToString();
        }

    }

    /**
     *
     */
    public void guestsToString() {

        String s = "";

        for(Guest item: guests) {
            if (item.getStatus() == GuestStatus.WAITING || item.getStatus() == GuestStatus.STAYING) {
                s = s + "\t" + item + "\n";
            }
        }

        Log.printColor(Log.WHITE_UNDERLINED, "GUESTS:");

        if (!s.isEmpty()) {
            Log.print(s);
        }
        else {
            Log.print("\t-\n");
        }

    }

}