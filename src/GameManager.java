import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import Entity.*;
import Enum.*;
import Record.TimePeriod;
import IO.Input;
import IO.Log;

public class GameManager {
    private final Hotel hotel = new Hotel();
    private final List<Guest> guests = new ArrayList<>();

    private static final Random rand = new Random();
    private static LocalDate gameDate = LocalDate.of (2023,12,31);
    private static final Double reservationRate = 0.15;
    private static SetupMode hotelSetupMode = SetupMode.AUTOMATIC;

    // Constructor

    public GameManager() {
            Log.print("\nWelcome to Hotel Simulation!\n");
    }

    // Getter

    public static LocalDate getGameDate() {
        return gameDate;
    }

    // Setter

    public static void setGameDate(LocalDate gameDate) {
        GameManager.gameDate = gameDate;
    }

    // Methods / Hotel

    public void initHotel() {

        String hotelSetupString = Input.askQuestion("Hotel Setup: do you want to insert your input", List.of("MANUALLY", "AUTOMATICALLY"), InputType.SINGLE_CHOICE_TEXT);

        if (hotelSetupString.equalsIgnoreCase("MANUALLY")) {
            hotelSetupMode = SetupMode.MANUAL;
        }

        /* Hotel Name */

        String hotelName = "My Hotel";

        if (hotelSetupMode == SetupMode.MANUAL) {
            hotelName = Input.askQuestion("Choose the name of your hotel", InputType.STRING);
        }

        hotel.setName(hotelName);
        Log.print("\nHOTEL | " + hotel.getName() + "\n");

        int availableArea = 200;
        int roomNumber = 1;

        RoomSize inputSize;
        RoomType inputType = RoomType.STANDARD;
        int area = 25;
        BigDecimal rate = new BigDecimal(100);

        while (availableArea >= Room.minArea(RoomType.STANDARD, RoomSize.SINGLE, HotelStars.ONE)) {

            /* Room Size */

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

            if (hotelSetupMode == SetupMode.MANUAL) {

                Log.print("You have " + availableArea + " square meters available for your rooms");
                Log.print("Room " + roomNumber);

                List<String> questionOptions = Room.allowedRoomSizes(Room.maxRoomSize(RoomType.STANDARD, HotelStars.ONE, availableArea));
                String answer = Input.askQuestion("Choose the size for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputSize = Room.stringToRoomSize(answer);

                /* Room Type */

                questionOptions = Room.allowedRoomTypes(Room.maxRoomType(inputSize));
                answer = Input.askQuestion("Choose the type for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputType = Room.stringToRoomType(answer);

                /* Room Area */

                Log.print("These are the requirements for the selected room size and type:");
                int area1 = Room.minArea(inputType, inputSize, HotelStars.ONE);
                int area2 = Room.minArea(inputType, inputSize, HotelStars.TWO);
                int area3 = Room.minArea(inputType, inputSize, HotelStars.THREE);
                int area4 = Room.minArea(inputType, inputSize, HotelStars.FOUR);
                int area5 = Room.minArea(inputType, inputSize, HotelStars.FIVE);
                Log.print(area1 + " m²: * | " + area2 + " m²: ** | " + area3 + " m²: *** | " + area4 + " m²: **** | " + area5 + " m²: *****");

                area = Input.askQuestion("Choose the number of square meters for this room", Room.minArea(inputType, inputSize, HotelStars.ONE), availableArea);

                /* Room Rate */

                rate = new BigDecimal(Input.askQuestion("Choose the daily rate in € for  b room", 0, Integer.MAX_VALUE));

            }

            hotel.addRoom(new Room(roomNumber, inputSize, inputType, area, rate));
            availableArea = availableArea - area;
            roomNumber = roomNumber + 1;

        }

        if (hotelSetupMode == SetupMode.MANUAL) {
            Log.print("All the square meters available were allocated to the rooms.\n");
        }

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(hotel.roomsString(hotel.getRooms()));

        initEmployees();

    }

    // Methods / Employee

    public void initEmployees() {

        Employee assistant = new Employee("Alex Goldner", EmployeeRole.ASSISTANT_GENERAL_MANAGER, new BigDecimal(2000));
        Log.print("Meet your new Assistant General Manager!");
        Log.print("\tEMPLOYEE " + assistant + "\n");

        if (hotelSetupMode == SetupMode.MANUAL) {

            for (DayOfWeek item: List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)) {
                String timePeriodString = Input.askQuestion("Choose the shift for " + item + " using the format hh:mm-hh:mm or leaving blank", InputType.TIME_INTERVAL);
                TimePeriod timePeriod = TimePeriod.parseTimePeriod(timePeriodString);
                if (timePeriod != null) {
                    assistant.addShift(new Shift(item, timePeriod));
                }
            }

            hotel.getEmployees().add(assistant);

        }

        else {
            assistant.addShift(new Shift(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(13, 0))));

            hotel.getEmployees().add(assistant);
            Log.printColor(Log.WHITE_UNDERLINED, "EMPLOYEES:");
            Log.print(hotel.employeesString());
            Log.print(hotel.getEmployees().getFirst().shiftsString());
        }

    }

    public void advanceDate() {

        setGameDate(gameDate.plusDays(1));
        Log.print("--------------------------------------\n");
        Log.printColor(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate + "\n");

        if (gameDate.getDayOfMonth() == 1) {
            hotel.paySalaries(gameDate);
        }

        generateGuests();

        generateCheckouts();

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(hotel.roomsString(hotel.getRooms()));

        generateReservations();

        generateCheckins();

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(hotel.roomsString(hotel.getRooms()));

    }

    // Methods / Guest

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

        if (hotelSetupMode == SetupMode.AUTOMATIC) {
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

    // Methods / Reservation

    public void reserveRoom(Room room, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal rate) {
        room.setStatus(RoomStatus.RESERVED);
        guest.setStatus(GuestStatus.STAYING);
        Reservation reservation = new Reservation(room, guest, startDate, endDate, rate);
        hotel.addReservation(reservation);
        hotel.financialManager.addTransaction(new Transaction(TransactionType.RESERVATION, startDate, reservation.calculatePrice()));
    }

    public void generateReservations() {

        if (hotelSetupMode == SetupMode.MANUAL) {
            Log.printColor(Log.WHITE_UNDERLINED, "GUESTS:");
        }

        for(Guest guest: guests) {

            if (guest.getStatus() == GuestStatus.WAITING) {

                if (hotelSetupMode == SetupMode.MANUAL) {

                    Log.print("\t" + guest);

                    ArrayList<Room> rooms = hotel.subsetRooms(guest.getPeople(), Room.freeStatus);
                    List<String> roomsOptions = hotel.subsetRoomOptions(guest.getPeople(), Room.freeStatus);

                    if (rooms.isEmpty()) {
                        Log.print("\tThere are no available rooms\n");
                    }

                    else {
                        Log.print("\n\tAvailable Rooms:");
                        Log.print(hotel.roomsString(rooms));

                        int roomNumber = Input.parseNumber(Input.askQuestion("\tChoose Room number to assign to t", roomsOptions, InputType.SINGLE_CHOICE_NUMBER));

                        boolean roomFound = false;
                        for (Room room : hotel.getRooms()) {
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
                        for (Room room : hotel.getRooms()) {
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
        Log.printColor(Log.BLUE, hotel.reservationsString());

    }

    public void checkinGuest(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        Log.printColor(Log.GREEN, "\tGuest (" + reservation.getGuest() + ") checked in to Room (" + reservation.getRoom() + ")");
    }

    public void checkoutGuest(Reservation reservation) {
        reservation.getRoom().setStatus(RoomStatus.FREE);
        reservation.getGuest().setStatus((GuestStatus.STAYED));
        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        Log.printColor(Log.RED, "\tGuest (" + reservation.getGuest() + ") checked out from Room (" + reservation.getRoom() + ")");
    }

    public void generateCheckins() {

        Log.printColor(Log.GREEN_UNDERLINED, "CHECK-INS:");

        int checkInsCount = 0;

        for(Reservation reservation: hotel.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED && reservation.getStartDate().isEqual(getGameDate())) {
                checkinGuest(reservation);
                checkInsCount++;
            }
        }

        if (checkInsCount == 0) {
            Log.printColor(Log.GREEN, "\t-");
        }
        Log.print("");

    }

    public void generateCheckouts() {

        Log.printColor(Log.RED_UNDERLINED, "CHECK-OUTS:");

        int checkOutsCount = 0;
        for(Reservation reservation: hotel.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CHECKED_IN && reservation.getEndDate().isEqual(getGameDate())) {
                checkoutGuest(reservation);
                checkOutsCount++;
            }
        }

        if (checkOutsCount == 0) {
            Log.printColor(Log.RED, "\t-");
        }
        Log.print("");

    }

    public void financialSummary() {
        Log.printColor(Log.WHITE_UNDERLINED, "FINANCIAL SUMMARY");
        Log.print("\tReservations: " + Log.currencyString(hotel.financialManager.getTransactionsAmount(TransactionType.RESERVATION)));
        Log.print("\tSalaries: " + Log.currencyString(hotel.financialManager.getTransactionsAmount(TransactionType.SALARY)));
        Log.print("\tBalance: " + Log.currencyString(hotel.financialManager.getBalance()));
    }

}