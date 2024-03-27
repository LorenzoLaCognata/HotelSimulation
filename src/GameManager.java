import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Enum.*;

public class GameManager {
    private final Hotel hotel = new Hotel();
    private final List<Guest> guests = new ArrayList<>();

    private static final Random rand = new Random();
    private static LocalDate gameDate = LocalDate.now();
    private static final Double reservationRate = 0.15;
    private static SetupMode hotelSetupMode = SetupMode.AUTOMATIC;
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor

    public GameManager() {
            Log.print("\nWelcome to Hotel Simulation!\n");
    }

    // Getter

    public Hotel getHotel() {
        return hotel;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public static LocalDate getGameDate() {
        return gameDate;
    }

    // Setter

    public static void setGameDate(LocalDate gameDate) {
        GameManager.gameDate = gameDate;
    }

    // Methods / Hotel

    public void initHotel() {

        Log.print("Hotel Setup: do you want to insert your input (A) or setup the hotel automatically? (B)");

        if (scanner.nextLine().equalsIgnoreCase("A")) {
            hotelSetupMode = SetupMode.MANUAL;
        }

        /* Hotel Name */

        String hotelName = "My Hotel";
        if (hotelSetupMode == SetupMode.MANUAL) {
            Log.print("Choose the name of your hotel");
            hotelName = scanner.nextLine();
        }
        this.hotel.setName(hotelName);
        Log.print("\nHOTEL | " + this.hotel.getName() + "\n");

        int availableArea = 200;
        int roomNumber = 1;
        RoomSize inputSize = RoomSize.SINGLE;
        RoomType inputType;
        int area;

        while (availableArea >= Room.minArea(RoomType.STANDARD, RoomSize.SINGLE, HotelStars.ONE)) {

            /* Room Size */

            Double roomRandom = rand.nextDouble();

            if (roomRandom < 0.25) {
                inputSize = RoomSize.SINGLE;
            }
            else if (roomRandom >= 0.25 && roomRandom < 0.65) {
                inputSize = RoomSize.DOUBLE;
            }
            else if (roomRandom >= 0.65 && roomRandom < 0.90) {
                inputSize = RoomSize.TRIPLE;
            }
            else if (roomRandom >= 0.9) {
                inputSize = RoomSize.QUADRUPLE;
            }

            if (hotelSetupMode == SetupMode.MANUAL) {
                Log.print("You have " + availableArea + " square meters available for your rooms");
                Log.print("Room " + roomNumber);

                boolean correctSize = false;

                while (!correctSize) {
                    Log.print("Choose the size for this room (SINGLE, DOUBLE, TRIPLE or QUADRUPLE)");
                    String inputSizeString = scanner.nextLine();
                    inputSize = Room.stringToRoomSize(inputSizeString);

                    if (inputSize == null) {
                        Log.print("You have not chosen a size correctly, please enter SINGLE, DOUBLE, TRIPLE or QUADRUPLE");
                    }
                    else if (availableArea < Room.minArea(RoomType.STANDARD, inputSize, HotelStars.ONE)) {
                        Log.print("The minimum area for this room is higher than the square meters you have available for your rooms. Please choose another room size.");
                    }
                    else {
                        correctSize = true;
                    }
                }
            }

            /* Room Type */

            inputType = RoomType.STANDARD;

            if (hotelSetupMode == SetupMode.MANUAL) {
                boolean correctType = false;

                while (!correctType) {
                    Log.print("Choose the type for this room (STANDARD, SUPERIOR, DELUXE, JUNIOR_SUITE or SUITE)");
                    String inputTypeString = scanner.nextLine();
                    inputType = Room.stringToRoomType(inputTypeString);

                    if (inputType == null) {
                        Log.print("You have not chosen a type correctly, please enter STANDARD, SUPERIOR, DELUXE, JUNIOR_SUITE or SUITE");
                    } else if (Room.minArea(inputType, inputSize, HotelStars.ONE) == -1) {
                        Log.print("The room type selected is not compatible with the room size selected. Please choose another room type.");
                    } else if (availableArea < Room.minArea(inputType, inputSize, HotelStars.ONE)) {
                        Log.print("The minimum area for this room is higher than the square meters you have available for your rooms. Please choose another room type.");
                    } else {
                        correctType = true;
                    }
                }
            }

            /* Room Area */

            area = 25;

            if (hotelSetupMode == SetupMode.MANUAL) {
                boolean correctArea = false;

                while (!correctArea) {
                    Log.print("Choose the number of square meters for this room. These are the requirements for the selected room size and type:");
                    int area1 = Room.minArea(inputType, inputSize, HotelStars.ONE);
                    int area2 = Room.minArea(inputType, inputSize, HotelStars.TWO);
                    int area3 = Room.minArea(inputType, inputSize, HotelStars.THREE);
                    int area4 = Room.minArea(inputType, inputSize, HotelStars.FOUR);
                    int area5 = Room.minArea(inputType, inputSize, HotelStars.FIVE);
                    Log.print(area1 + " m²: * | " + area2 + " m²: ** | " + area3 + " m²: *** | " + area4 + " m²: **** | " + area5 + " m²: *****");
                    area = parseNumber(scanner.nextLine());

                    if (area <= 0) {
                        Log.print("You have not chosen an area correctly, please enter a positive number.");
                    }
                    else if (area < Room.minArea(inputType, inputSize, HotelStars.ONE)) {
                        Log.print("The minimum area for this room is higher. Please choose a bigger area.");
                    }
                    else if (availableArea < area) {
                        Log.print("The area selected is higher than the square meters you have available for your rooms. Please choose another area.");
                    }
                    else {
                        correctArea = true;
                    }
                }
            }

            this.hotel.addRoom(new Room(roomNumber, inputSize, inputType, area));
            availableArea = availableArea - area;
            roomNumber = roomNumber + 1;

        }

        if (hotelSetupMode == SetupMode.MANUAL) {
            Log.print("All the square meters available were allocated to the rooms.\n");
        }

        Log.printc(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(this.hotel.roomsString(this.hotel.getRooms()));

        initEmployees();

    }

    // Methods / Employee

    public void initEmployees() {

        Employee assistant = new Employee("Alex Goldner", EmployeeRole.ASSISTANT_GENERAL_MANAGER);
        Log.print("Meet your new Assistant General Manager!");
        Log.print("\tEMPLOYEE " + assistant + "\n");

        if (hotelSetupMode == SetupMode.MANUAL) {
            for (DayOfWeek item: List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)) {
                Log.print("Choose the shift for " + item + ", enter the start hour and hour with the format hh:mm-hh:mm");
                String time = scanner.nextLine();
                if (!time.isEmpty()) {
                    LocalTime start = LocalTime.parse(time.substring(0, 5), DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()));
                    LocalTime end = LocalTime.parse(time.substring(6, 11), DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()));
                    assistant.addShift(new Shift(item, start, end));
                }
            }

            this.hotel.getEmployees().add(assistant);

        }
        else {
            assistant.addShift(new Shift(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(18, 0)));
            assistant.addShift(new Shift(DayOfWeek.TUESDAY, LocalTime.of(9,0), LocalTime.of(18, 0)));
            assistant.addShift(new Shift(DayOfWeek.WEDNESDAY, LocalTime.of(9,0), LocalTime.of(18, 0)));
            assistant.addShift(new Shift(DayOfWeek.THURSDAY, LocalTime.of(9,0), LocalTime.of(18, 0)));
            assistant.addShift(new Shift(DayOfWeek.FRIDAY, LocalTime.of(9,0), LocalTime.of(18, 0)));
            assistant.addShift(new Shift(DayOfWeek.SATURDAY, LocalTime.of(9,0), LocalTime.of(13, 0)));

            this.hotel.getEmployees().add(assistant);
            Log.printc(Log.WHITE_UNDERLINED, "EMPLOYEES:");
            Log.print(this.hotel.employeesString());
            Log.print(this.hotel.getEmployees().getFirst().shiftsString());
        }

    }

    public void advanceDate() {
        setGameDate(gameDate.plusDays(1));
        Log.print("--------------------------------------\n");
        Log.printc(Log.CYAN_BACKGROUND, "GAME DATE | " + gameDate + "\n");
        this.generateGuests();
        this.generateCheckouts();
        Log.printc(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(this.hotel.roomsString(this.hotel.getRooms()));
        this.generateReservations();
        this.generateCheckins();
        Log.printc(Log.WHITE_UNDERLINED, "ROOMS:");
        Log.print(this.hotel.roomsString(this.hotel.getRooms()));
    }

    public static Integer parseNumber(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
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
                this.guests.add(new Guest(people, gameDate, gameDate.plusDays(nights)));
            }
        }

        if (hotelSetupMode == SetupMode.AUTOMATIC) {
            this.printGuests();
        }

    }

    public void printGuests() {

        String s = "";

        for(Guest item: this.guests) {
            if (item.getStatus() == GuestStatus.WAITING || item.getStatus() == GuestStatus.STAYING) {
                s = s + "\t" + item + "\n";
            }
        }

        Log.printc(Log.WHITE_UNDERLINED, "GUESTS:");

        if (!s.isEmpty()) {
            Log.print(s);
        }
        else {
            Log.print("\t-\n");
        }

    }

    // Methods / Reservation

    public void reserveRoom(Room room, Guest guest, LocalDate startDate, LocalDate endDate) {
        room.setStatus(RoomStatus.RESERVED);
        guest.setStatus(GuestStatus.STAYING);
        Reservation reservation = new Reservation(room, guest, startDate, endDate);
        this.hotel.getReservations().add(reservation);
    }

    public void generateReservations() {

        if (hotelSetupMode == SetupMode.MANUAL) {
            Log.printc(Log.WHITE_UNDERLINED, "GUESTS:");
        }

        for(Guest guest: this.guests) {

            if (guest.getStatus() == GuestStatus.WAITING) {

                if (hotelSetupMode == SetupMode.MANUAL) {

                    Log.print("\t" + guest);

                    ArrayList<Room> rooms = this.hotel.subsetRooms(guest.getPeople(), Room.freeStatus);

                    if (rooms.isEmpty()) {
                        Log.print("\tThere are no available rooms\n");
                    }

                    else {
                        Log.print("\n\tAvailable Rooms:");
                        Log.print(this.hotel.roomsString(rooms));
                        Log.print("\tChoose Room number to assign to this guest:");
                        int roomNumber = parseNumber(scanner.nextLine());

                        boolean roomFound = false;
                        for (Room room : this.hotel.getRooms()) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getNumber() == roomNumber) {
                                roomFound = true;
                                reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()));
                            }
                        }
                    }
                }

                else {
                    boolean roomFound = false;
                    for (int size = guest.getPeople(); size <= 4; size++) {
                        for (Room room : this.hotel.getRooms()) {
                            if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getSizeNumber() == size) {
                                roomFound = true;
                                reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()));
                            }
                        }
                    }

                    if (!roomFound) {
                        guest.setStatus(GuestStatus.LOST);
                    }
                }
            }
        }

        Log.printc(Log.BLUE_UNDERLINED, "RESERVATIONS:");
        Log.printc(Log.BLUE, this.hotel.reservationsString());

    }

    public void checkinGuest(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        Log.printc(Log.GREEN, "\tGuest (" + reservation.getGuest() + ") checked in to Room (" + reservation.getRoom() + ")");
    }

    public void checkoutGuest(Reservation reservation) {
        reservation.getRoom().setStatus(RoomStatus.FREE);
        reservation.getGuest().setStatus((GuestStatus.STAYED));
        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        Log.printc(Log.RED, "\tGuest (" + reservation.getGuest() + ") checked out from Room (" + reservation.getRoom() + ")");
    }

    public void generateCheckins() {

        Log.printc(Log.GREEN_UNDERLINED, "CHECK-INS:");

        int checkInsCount = 0;

        for(Reservation reservation: this.hotel.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED && reservation.getStartDate().isEqual(getGameDate())) {
                checkinGuest(reservation);
                checkInsCount++;
            }
        }

        if (checkInsCount == 0) {
            Log.printc(Log.GREEN, "\t-");
        }
        Log.print("");

    }

    public void generateCheckouts() {

        Log.printc(Log.RED_UNDERLINED, "CHECK-OUTS:");

        int checkOutsCount = 0;
        for(Reservation reservation: this.hotel.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CHECKED_IN && reservation.getEndDate().isEqual(getGameDate())) {
                checkoutGuest(reservation);
                checkOutsCount++;
            }
        }

        if (checkOutsCount == 0) {
            Log.printc(Log.RED, "\t-");
        }
        Log.print("");

    }

}