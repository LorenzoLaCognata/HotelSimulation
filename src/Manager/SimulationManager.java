package Manager;

import Entity.Guest;
import Entity.Hotel;
import Enum.GuestStatus;
import Enum.InputType;
import Enum.SetupMode;
import IO.Input;
import IO.Log;
import Menu.EmployeesMenu;
import Menu.GuestsMenu;
import Menu.RoomsMenu;

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
    private final GuestsMenu guestsMenu = new GuestsMenu();
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
     * Returns the game date
     * @return Game Date
     */
    public LocalDate getGameDate() {
        return gameDate;
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

        Log.printColor(Log.WHITE_UNDERLINED, "MAIN MENU");
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

        Log.printColor(Log.WHITE_UNDERLINED, "GUESTS:");
        Log.print(guestsToString());

        String roomsMenuChoice = Input.askQuestion("", List.of("RESERVATIONS", "CHECK-INS", "CHECK-OUTS", "BACK"), InputType.SINGLE_CHOICE_TEXT);

        if (roomsMenuChoice.equalsIgnoreCase("BACK")) {
            mainMenu();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("RESERVATIONS")) {

            List<Guest> waitingGuests = subsetGuests(Integer.MAX_VALUE, GuestStatus.waitingStatus);

            if (waitingGuests.isEmpty()) {
                Log.print("There are no waiting guests\n");
            }

            else {
                guestsMenu.guestReservationsMenuChoice(guests, this, hotel.reservationManager, hotel.financialManager);
            }

            guestsMenuChoice();
        }

        else if (roomsMenuChoice.equalsIgnoreCase("CHECK-INS")) {

            List<Guest> stayingGuests = subsetGuests(Integer.MAX_VALUE, GuestStatus.reservedStatus);

            if (stayingGuests.isEmpty()) {
                Log.print("There are no guests to check-in\n");
            }

            else {
                guestsMenu.guestCheckInsMenuChoice(guests, this, hotel.reservationManager);
            }

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
     * Collects user input from the employees menu and executes the appropriate sub-menu
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
     * Advance the Game Date by a certain amount of dates simulating all the events for those days
     * @param days Number of days to simulate
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
     * Executing end-of-day routines and advancing the Game Date by one day
     */
    public void advanceDate() {

        hotel.financialManager.financialSummary();
        Log.print("");

        hotel.reservationManager.reservationSummary(gameDate);
        Log.print("");

        setGameDate(gameDate.plusDays(1));
        Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate);
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
                int startDay = random.nextInt(0, 3);
                int endDay = random.nextInt(1, 8);
                guests.add(new Guest(people, gameDate.plusDays(startDay), gameDate.plusDays(endDay)));
            }
        }

        if (setupMode == SetupMode.AUTOMATIC) {
            Log.printColor(Log.WHITE_UNDERLINED, "GUESTS");
            Log.print(guestsToString());
        }

    }

    /**
     *
     * @param maxPeople
     * @param status
     * @return
     */
    public ArrayList<Guest> subsetGuests(int maxPeople, ArrayList<GuestStatus> status) {

        ArrayList<Guest> subset = new ArrayList<>();

        for(Guest item: guests) {
            if (item.getPeople() <= maxPeople) {
                if (status.isEmpty() || status.contains(item.getStatus())) {
                    subset.add(item);
                }
            }
        }

        return subset;

    }

    /**
     *
     * @param maxPeople
     * @param status
     * @return
     */
    public ArrayList<String> subsetGuestOptions(int maxPeople, ArrayList<GuestStatus> status) {

        ArrayList<String> GuestOptions = new ArrayList<>();

        for(Guest item: subsetGuests(maxPeople, status)) {
            GuestOptions.add(String.valueOf(item.getNumber()));
        }

        return GuestOptions;

    }

    /**
     *
     * @return
     */
    public ArrayList<String> subsetGuestOptions() {

        ArrayList<String> GuestOptions = new ArrayList<>();

        for(Guest item: guests) {
            GuestOptions.add(String.valueOf(item.getNumber()));
        }

        return GuestOptions;

    }

    private String guestsToString() {

        String s = "";

        for(Guest item: guests) {
            if (item.getStatus() == GuestStatus.WAITING || item.getStatus() == GuestStatus.RESERVED || item.getStatus() == GuestStatus.CHECKED_IN) {
                s = s + "\t" + item + "\n";
            }
        }

        return s;

    }

}