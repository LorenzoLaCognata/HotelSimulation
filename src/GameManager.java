import java.math.BigDecimal;
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

        String hotelSetupString = Input.askQuestion("Hotel Setup: do you want to insert your input", List.of("MANUALLY", "AUTOMATICALLY"), InputType.SINGLE_CHOICE_TEXT);

        if (hotelSetupString.equalsIgnoreCase("MANUALLY")) {
            hotelSetupMode = SetupMode.MANUAL;
        }

        /* Hotel Name */

        String hotelName = "My Hotel";

        if (hotelSetupMode == SetupMode.MANUAL) {
            hotelName = Input.askQuestion("Choose the name of your hotel", InputType.STRING);
        }

        this.hotel.setName(hotelName);
        Log.print("\nHOTEL | " + this.hotel.getName() + "\n");

        int availableArea = 200;
        int roomNumber = 1;

        RoomSize inputSize = RoomSize.SINGLE;
        RoomType inputType = RoomType.STANDARD;
        int area = 25;
        BigDecimal rate = new BigDecimal(100);

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

                List<String> questionOptions = Room.allowedRoomSizes(Room.maxRoomSize(RoomType.STANDARD, HotelStars.ONE, availableArea));
                String answer = Input.askQuestion("Choose the size for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputSize = Room.stringToRoomSize(answer);

                /* Room Type */

                questionOptions = Room.allowedRoomTypes(Room.maxRoomType(inputSize, HotelStars.ONE));
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

                area = Input.askQuestion("Choose the number of square meters for this room", InputType.INTEGER, Room.minArea(inputType, inputSize, HotelStars.ONE), availableArea);

                /* Room Rate */

                rate = new BigDecimal(Input.askQuestion("Choose the daily rate in € for this room", InputType.INTEGER, 0, Integer.MAX_VALUE));

            }

            this.hotel.addRoom(new Room(roomNumber, inputSize, inputType, area, rate));
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
                String timePeriodString = Input.askQuestion("Choose the shift for " + item + " using the format hh:mm-hh:mm or leaving blank", InputType.TIME_INTERVAL);
                TimePeriod timePeriod = TimePeriod.parseTimePeriod(timePeriodString);
                if (timePeriod != null) {
                    assistant.addShift(new Shift(item, timePeriod));
                }
            }

            this.hotel.getEmployees().add(assistant);

        }

        else {
            assistant.addShift(new Shift(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(18, 0))));
            assistant.addShift(new Shift(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(9,0), LocalTime.of(13, 0))));

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

    public void reserveRoom(Room room, Guest guest, LocalDate startDate, LocalDate endDate, BigDecimal rate) {
        room.setStatus(RoomStatus.RESERVED);
        guest.setStatus(GuestStatus.STAYING);
        Reservation reservation = new Reservation(room, guest, startDate, endDate, rate);
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
                    List<String> roomsOptions = this.hotel.subsetRoomOptions(guest.getPeople(), Room.freeStatus);

                    if (rooms.isEmpty()) {
                        Log.print("\tThere are no available rooms\n");
                    }

                    else {
                        Log.print("\n\tAvailable Rooms:");
                        Log.print(this.hotel.roomsString(rooms));

                        int roomNumber = Input.parseNumber(Input.askQuestion("\tChoose Room number to assign to this guest", roomsOptions, InputType.SINGLE_CHOICE_NUMBER));

                        boolean roomFound = false;
                        for (Room room : this.hotel.getRooms()) {
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
                        for (Room room : this.hotel.getRooms()) {
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

    public void financialSummary() {
        Log.print("Financial Summary");
        Log.print("\tRevenues: " + Log.currency.format(this.hotel.getRevenues()));
    }

}