package Manager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import Entity.*;
import Enum.*;
import IO.Input;
import IO.Log;

public class SimulationManager {
    private final Hotel hotel = new Hotel();
    private final List<Guest> guests = new ArrayList<>();

    private static final Random rand = new Random();
    private static LocalDate gameDate = LocalDate.of (2024,1,1);
    private static final Double reservationRate = 0.15;
    private static SetupMode setupMode = SetupMode.AUTOMATIC;

    // Constructor

    public SimulationManager() {
        Log.initLog();
    }

    // Getter

    // Setter

    public static void setGameDate(LocalDate gameDate) {
        SimulationManager.gameDate = gameDate;
    }

    // Methods

    public void initHotel() {

        String hotelSetupString = Input.askQuestion("Hotel Setup: do you want to insert your input", List.of("MANUALLY", "AUTOMATICALLY"), InputType.SINGLE_CHOICE_TEXT);

        if (hotelSetupString.equalsIgnoreCase("MANUALLY")) {
            setupMode = SetupMode.MANUAL;
        }

        /* Hotel Name */

        String hotelName = "My Hotel";

        if (setupMode == SetupMode.MANUAL) {
            hotelName = Input.askQuestion("Choose the name of your hotel", InputType.STRING);
        }

        hotel.setName(hotelName);
        Log.printColor(Log.WHITE_UNDERLINED, "HOTEL");
        Log.print("\t" + hotel.getName());

        int availableArea = 200;

        if (setupMode == SetupMode.MANUAL) {
            availableArea = Input.askQuestion("\nChoose the size of your hotel in m²", 100, 500);
        }

        Log.print("\tAvailable area for your hotel will be " + availableArea + " m²");

        BigDecimal rent = new BigDecimal(availableArea).multiply(new BigDecimal(50));
        hotel.setRent(rent);
        Log.print("\tRent for your hotel will cost " + Log.currencyString(rent));
        Log.print("");

        int roomNumber = 1;

        RoomSize inputSize;
        RoomType inputType = RoomType.STANDARD;
        int area = 25;
        BigDecimal rate = new BigDecimal(100);

        while (availableArea >= Room.minArea(RoomType.STANDARD, RoomSize.SINGLE, HotelStars.ONE)) {

            double roomRandom = rand.nextDouble();

            if (roomRandom < 0.25) {
                inputSize = RoomSize.SINGLE;
            }
            else if (roomRandom < 0.65) {
                inputSize = RoomSize.DOUBLE;
            }
            else if (roomRandom < 0.90) {
                inputSize = RoomSize.TRIPLE;
            }
            else {
                inputSize = RoomSize.QUADRUPLE;
            }

            if (setupMode == SetupMode.MANUAL) {

                Log.print("Room " + roomNumber);
                Log.print("");

                List<String> questionOptions = Room.allowedRoomSizes(Room.maxRoomSize(RoomType.STANDARD, HotelStars.ONE, availableArea));
                String answer = Input.askQuestion("Choose the size for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputSize = Room.stringToRoomSize(answer);

                questionOptions = Room.allowedRoomTypes(Room.maxRoomType(inputSize));
                answer = Input.askQuestion("Choose the type for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputType = Room.stringToRoomType(answer);

                Log.print("These are the requirements for the selected room size and type:");
                int area1 = Room.minArea(inputType, inputSize, HotelStars.ONE);
                int area2 = Room.minArea(inputType, inputSize, HotelStars.TWO);
                int area3 = Room.minArea(inputType, inputSize, HotelStars.THREE);
                int area4 = Room.minArea(inputType, inputSize, HotelStars.FOUR);
                int area5 = Room.minArea(inputType, inputSize, HotelStars.FIVE);
                Log.print(area1 + " m²: * | " + area2 + " m²: ** | " + area3 + " m²: *** | " + area4 + " m²: **** | " + area5 + " m²: *****");
                Log.print("");

                area = Input.askQuestion("Choose the number of m² for this room", Room.minArea(inputType, inputSize, HotelStars.ONE), availableArea);

                rate = new BigDecimal(Input.askQuestion("Choose the daily rate in € for this room", 0, Integer.MAX_VALUE));

            }

            hotel.reservationManager.addRoom(new Room(roomNumber, inputSize, inputType, area, rate));
            availableArea = availableArea - area;
            roomNumber = roomNumber + 1;

        }

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS");
        Log.print(hotel.reservationManager.roomsString(hotel.reservationManager.getRooms()));

        hotel.employeeManager.initEmployees();

        Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate);
        Log.print("");

    }

    public void mainMenu() {

        Log.printColor(Log.WHITE_UNDERLINED, "MAIN MENU");
        String mainMenuChoice = Input.askQuestion("", List.of("GUESTS", "ROOMS", "EMPLOYEES", "ADVANCE DATE"), InputType.SINGLE_CHOICE_TEXT);

        if (mainMenuChoice.equalsIgnoreCase("GUESTS")) {
            Log.print("Page under construction (note: RESERVATIONS, CHECK-INS, CHECK-OUTS)");
            Log.print("");
            mainMenu();
        }
        else if (mainMenuChoice.equalsIgnoreCase("ROOMS")) {
            roomsMenu();
        }
        else if (mainMenuChoice.equalsIgnoreCase("EMPLOYEES")) {
            employeesMenu();
        }
        else if (mainMenuChoice.equalsIgnoreCase("ADVANCE DATE")) {
            advanceDate();
            mainMenu();
        }

    }

    public void roomsMenu() {

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(hotel.reservationManager.roomsString(hotel.reservationManager.getRooms()));

        String roomsMenuChoice = Input.askQuestion("", List.of("SET RATE", "SET TYPE", "SET SIZE", "BACK"), InputType.SINGLE_CHOICE_TEXT);

        if (roomsMenuChoice.equalsIgnoreCase("BACK")) {
            mainMenu();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("SET RATE")) {
            roomRateMenu();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("SET TYPE")) {
            roomTypeMenu();
        }
        else if (roomsMenuChoice.equalsIgnoreCase("SET SIZE")) {
            roomSizeMenu();
        }

    }

    public void roomRateMenu() {

        String roomRateMenuChoice = Input.askQuestion("Select a room", hotel.reservationManager.subsetRoomOptions(), InputType.SINGLE_CHOICE_NUMBER);

        for (Room item: hotel.reservationManager.getRooms()) {
            if (String.valueOf(item.getNumber()).equalsIgnoreCase(roomRateMenuChoice)) {

                BigDecimal rate = new BigDecimal(Input.askQuestion("Choose the daily rate in € for this room", 0, Integer.MAX_VALUE));
                item.setRate(rate);
            }
        }

        Collections.sort(hotel.reservationManager.getRooms());
        roomsMenu();

    }

    public void roomTypeMenu() {

        String roomRateMenuChoice = Input.askQuestion("Select a room", hotel.reservationManager.subsetRoomOptions(), InputType.SINGLE_CHOICE_NUMBER);

        for (Room item: hotel.reservationManager.getRooms()) {

            if (String.valueOf(item.getNumber()).equalsIgnoreCase(roomRateMenuChoice)) {

                List<String> questionOptions = Room.allowedRoomTypes(Room.maxRoomType(item.getSize(), item.getArea()));
                String answer = Input.askQuestion("Choose the type for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                RoomType type = Room.stringToRoomType(answer);
                item.setType(type);

            }
        }

        Collections.sort(hotel.reservationManager.getRooms());
        roomsMenu();

    }

    public void roomSizeMenu() {

        String roomRateMenuChoice = Input.askQuestion("Select a room", hotel.reservationManager.subsetRoomOptions(), InputType.SINGLE_CHOICE_NUMBER);

        for (Room item: hotel.reservationManager.getRooms()) {

            if (String.valueOf(item.getNumber()).equalsIgnoreCase(roomRateMenuChoice)) {

                List<String> questionOptions = Room.allowedRoomSizes(Room.maxRoomSize(RoomType.STANDARD, HotelStars.ONE, item.getArea()));
                String answer = Input.askQuestion("Choose the size for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                RoomSize size = Room.stringToRoomSize(answer);
                item.setSize(size);

            }
        }

        Collections.sort(hotel.reservationManager.getRooms());
        roomsMenu();

    }

    public void employeesMenu() {

        Log.printColor(Log.WHITE_UNDERLINED, "EMPLOYEES");
        Log.print(hotel.employeeManager.employeesString());

        String employeesMenuChoice = Input.askQuestion("", List.of("SHIFTS", "BACK"), InputType.SINGLE_CHOICE_TEXT);

        if (employeesMenuChoice.equalsIgnoreCase("BACK")) {
            mainMenu();
        }
        else if (employeesMenuChoice.equalsIgnoreCase("SHIFTS")) {
            employeesShiftsMenu();
        }

    }

    public void employeesShiftsMenu() {

        String employeesMenuChoice = Input.askQuestion("Select an employee", hotel.employeeManager.employeesQuestionChoices(), InputType.SINGLE_CHOICE_TEXT);

        for (Employee item: hotel.employeeManager.getEmployees()) {
            if (item.getName().equalsIgnoreCase(employeesMenuChoice)) {
                Log.printColor(Log.WHITE_UNDERLINED, "SHIFTS");
                Log.print(item.shiftsString());

                String employeeShiftMenuChoice = Input.askQuestion("", List.of("SET SHIFT", "BACK"), InputType.SINGLE_CHOICE_TEXT);

                if (employeeShiftMenuChoice.equalsIgnoreCase("BACK")) {
                    employeesMenu();
                }
                else if (employeeShiftMenuChoice.equalsIgnoreCase("SET SHIFT")) {
                    hotel.employeeManager.setShift(item);
                    Log.printColor(Log.WHITE_UNDERLINED, "SHIFTS");
                    Log.print(item.shiftsString());
                    employeesMenu();
                }

            }
        }

    }

    public void simulate(int days) {

        for (int i = 0; i < days; i++) {

            setGameDate(gameDate.plusDays(1));
            Log.print("--------------------------------------\n");
            Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate + "\n");

            if (gameDate.getDayOfMonth() == gameDate.lengthOfMonth()) {
                hotel.payRent(gameDate);
                hotel.paySalaries(gameDate);
            }

            generateGuests();

            hotel.reservationManager.generateCheckouts(gameDate);

            Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
            Log.print(hotel.reservationManager.roomsString(hotel.reservationManager.getRooms()));

            generateReservations();

            hotel.reservationManager.generateCheckins(gameDate);

            Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
            Log.print(hotel.reservationManager.roomsString(hotel.reservationManager.getRooms()));

            hotel.financialManager.financialSummary();

        }

    }

    public void advanceDate() {

        setGameDate(gameDate.plusDays(1));
        Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate);
        Log.print("");

        hotel.financialManager.financialSummary();
        Log.print("");

        if (gameDate.getDayOfMonth() == 1) {
            hotel.payRent(gameDate);
            hotel.paySalaries(gameDate);
        }

    }

    public void generateGuests() {

        for (int i = 0; i < 10; i++) {

            if (rand.nextDouble() <= reservationRate) {
                double gaussian = rand.nextGaussian();

                while (gaussian < -1.0 || gaussian > 1.0) {
                    gaussian = rand.nextGaussian();
                }

                int people = 1 + (int) Math.round((gaussian + 1.0) * 1.5);
                int nights = rand.nextInt(1, 7);
                guests.add(new Guest(people, gameDate, gameDate.plusDays(nights)));
            }
        }

        if (setupMode == SetupMode.AUTOMATIC) {
            printGuests();
        }

    }

    public void printGuests() {

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

    public void generateReservations() {

        if (setupMode == SetupMode.MANUAL) {
            Log.printColor(Log.WHITE_UNDERLINED, "GUESTS:");
        }

        for(Guest guest: guests) {

            if (guest.getStatus() == GuestStatus.WAITING) {

                if (setupMode == SetupMode.MANUAL) {

                    Log.print("\t" + guest);

                    ArrayList<Room> rooms = hotel.reservationManager.subsetRooms(guest.getPeople(), Room.freeStatus);
                    List<String> roomsOptions = hotel.reservationManager.subsetRoomOptions(guest.getPeople(), Room.freeStatus);

                    if (rooms.isEmpty()) {
                        Log.print("\tThere are no available rooms\n");
                    }

                    else {
                        Log.print("\n\tAvailable Rooms:");
                        Log.print(hotel.reservationManager.roomsString(rooms));

                        int roomNumber = Input.parseNumber(Input.askQuestion("\tChoose Room number to assign to", roomsOptions, InputType.SINGLE_CHOICE_NUMBER));

                        boolean roomFound = false;
                        for (Room room : rooms) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getNumber() == roomNumber) {
                                roomFound = true;
                                reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()), room.getRate());
                            }
                        }
                    }
                }

                else {
                    boolean roomFound = false;
                    for (int size = guest.getPeople(); size <= 4; size++) {
                        for (Room room : hotel.reservationManager.getRooms()) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getSizeNumber() == size) {
                                roomFound = true;
                                reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()), room.getRate());
                            }
                        }
                    }

                    if (!roomFound) {
                        guest.setStatus(GuestStatus.LOST);
                    }
                }
            }
        }

        Log.printColor(Log.BLUE_UNDERLINED, "RESERVATIONS:");
        Log.printColor(Log.BLUE, hotel.reservationManager.reservationsString());

    }

    public void reserveRoom(Room room, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal rate) {
        room.setStatus(RoomStatus.RESERVED);
        guest.setStatus(GuestStatus.STAYING);
        Reservation reservation = new Reservation(room, guest, startDate, endDate, rate);
        hotel.reservationManager.addReservation(reservation);
        hotel.financialManager.addTransaction(new Transaction(TransactionType.RESERVATION, startDate, reservation.calculatePrice()));
    }


}