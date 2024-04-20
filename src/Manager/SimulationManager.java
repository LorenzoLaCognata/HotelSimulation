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

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manage the hotel simulation part
 */
public class SimulationManager {

    private final Hotel hotel = new Hotel();
    private final List<Guest> guests = new ArrayList<>();
    private final RoomsMenu roomsMenu = new RoomsMenu();
    private final EmployeesMenu employeesMenu = new EmployeesMenu();

    private SetupMode setupMode = SetupMode.AUTOMATIC;
    private LocalDate gameDate = LocalDate.of (2024,1,1);
    private static final Random random = new Random();
    private static final Double reservationRate = 0.15;

    /**
     * Constructor, initializing the log, asking for the SetupMode and initialiting the hotel
     */
    public SimulationManager() {

        Log.initLog();

        String hotelSetupString = Input.askQuestion("Setup mode - How do you want to insert your input", List.of("MANUALLY", "AUTOMATICALLY"), InputType.SINGLE_CHOICE_TEXT);

        if (hotelSetupString.equalsIgnoreCase("MANUALLY")) {
            setupMode = SetupMode.MANUAL;
        }

        hotel.initHotel(setupMode, gameDate, random);

        generateGuests();

    }

    /**
     * Sets the game date
     * @param gameDate Date to set the Game Date to
     */
    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    /**
     * Collects user input from the main menu and executes the appropriate sub-menu
     */
    public void mainMenu() {

        Log.print("MAIN MENU",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
        String mainMenuChoice = Input.askQuestion("", List.of("GUESTS", "ROOMS", "EMPLOYEES", "ADVANCE DATE", "QUIT"), InputType.SINGLE_CHOICE_TEXT);

        if (mainMenuChoice.equalsIgnoreCase("GUESTS")) {
            guestsMenuChoice();
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
     * Collects user input from the guests menu and executes the appropriate sub-menu
     */
    private void guestsMenuChoice() {

        Log.print("GUESTS:",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
        Log.print(guestsToString());

        String roomsMenuChoice = Input.askQuestion("", List.of("RESERVATIONS", "CHECK-INS", "CHECK-OUTS", "BACK"), InputType.SINGLE_CHOICE_TEXT);

        if (roomsMenuChoice.equalsIgnoreCase("BACK")) {
            mainMenu();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("RESERVATIONS")) {
            //guestsMenu.guestReservationsMenuChoice();
            guestsMenuChoice();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("CHECK-INS")) {
            //guestsMenu.guestCheckInsMenuChoice();
            guestsMenuChoice();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("CHECK-OUTS")) {
            //guestsMenu.guestCheckOutsMenuChoice();
            guestsMenuChoice();
        }

    }

    /**
     * Collects user input from the rooms menu and executes the appropriate sub-menu
     */
    public void roomsMenuChoice() {

        Log.print("ROOMS:",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
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
     * Collects user input from the employees menu and executes the appropriate sub-menu
     */
    public void employeesMenuChoice() {

        Log.print("EMPLOYEES",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
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
     * Advance the Game Date by a certain amount of dates simulating all the events for those days
     * @param days Number of days to simulate
     */
    public void simulate(int days) {

        for (int i = 0; i < days; i++) {

            setGameDate(gameDate.plusDays(1));
            Log.print("--------------------------------------\n");
            Log.print("GAME DATE | " + gameDate + "\n",  Color.WHITE, Color.BLACK, StyleConstants.Bold, new Object());

            if (gameDate.getDayOfMonth() == gameDate.lengthOfMonth()) {
                hotel.financialManager.payRent(gameDate);
                hotel.financialManager.paySalaries(gameDate, hotel.employeeManager.getEmployees());
            }

            generateGuests();

            hotel.reservationManager.generateCheckouts(gameDate);

            Log.print("ROOMS:",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
            Log.print(hotel.reservationManager.roomsToString(hotel.reservationManager.getRooms()));

            hotel.reservationManager.generateReservations(setupMode, gameDate, guests, hotel.financialManager);

            hotel.reservationManager.generateCheckins(gameDate);

            Log.print("ROOMS:",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
            Log.print(hotel.reservationManager.roomsToString(hotel.reservationManager.getRooms()));

            hotel.financialManager.financialSummary();

        }

    }

    /**
     * Executing end-of-day routines and advancing the Game Date by one day
     */
    public void advanceDate() {

        hotel.financialManager.financialSummary();
        Log.print("");

        hotel.reservationManager.reservationSummary(gameDate);
        Log.print("");

        setGameDate(gameDate.plusDays(1));
        Log.print("GAME DATE | " + gameDate,  Color.WHITE, Color.BLACK, StyleConstants.Bold, new Object());
        Log.print("");

        if (gameDate.getDayOfMonth() == 1) {
            hotel.financialManager.payRent(gameDate);
            hotel.financialManager.paySalaries(gameDate, hotel.employeeManager.getEmployees());
        }

        generateGuests();

    }

    /**
     * Randomly generate guests with Start Date on the current date
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
            Log.print("GUESTS",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
            Log.print(guestsToString());
        }

    }

    private String guestsToString() {

        String s = "";

        for(Guest item: guests) {
            if (item.getStatus() == GuestStatus.WAITING || item.getStatus() == GuestStatus.STAYING) {
                s = s + "\t" + item + "\n";
            }
        }

        if (!s.isEmpty()) {
            return s;
        }
        else {
            return "\t-\n";
        }

    }

}