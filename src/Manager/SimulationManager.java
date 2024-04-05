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
    private static LocalDate gameDate = LocalDate.of (2023,12,31);
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

    // Methods / Hotel

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
        Log.print("\nHOTEL | " + hotel.getName() + "\n");

        int availableArea = 200;

        if (setupMode == SetupMode.MANUAL) {
            availableArea = Input.askQuestion("Choose the size of your hotel in square meters", 100, 500);
        }

        BigDecimal rent = new BigDecimal(availableArea).multiply(new BigDecimal(50));
        hotel.setRent(rent);
        Log.print("\nRent for your hotel will cost " + Log.currencyString(rent));
        Log.print("");

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

            if (setupMode == SetupMode.MANUAL) {

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

            hotel.reservationManager.addRoom(new Room(roomNumber, inputSize, inputType, area, rate));
            availableArea = availableArea - area;
            roomNumber = roomNumber + 1;

        }

        if (setupMode == SetupMode.MANUAL) {
            Log.print("All the square meters available were allocated to the rooms.\n");
        }

        Log.printColor(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(hotel.reservationManager.roomsString(hotel.reservationManager.getRooms()));

        hotel.employeeManager.initEmployees(setupMode);

    }

    // Methods / Employee

    public void advanceDate(int days) {

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

    // Methods / Reservation

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