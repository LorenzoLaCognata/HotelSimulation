import java.time.LocalDate;
import java.util.*;
import Enum.*;

public class GameManager {
    private final Hotel hotel = new Hotel();
    private final List<Guest> guests = new ArrayList<>();

    private static final Random rand = new Random();
    private static LocalDate gameDate = LocalDate.now();
    private static final Double reservationRate = 0.15;

    // Constructor

    public GameManager() {
            System.out.println("\nWelcome to Hotel Simulation!\n");
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

        Scanner myObj = new Scanner(System.in);

        System.out.println("Hotel Setup: do you want to insert your input (A) or setup the hotel automatically? (B)");

        SetupMode hotelSetupMode = SetupMode.AUTOMATIC;
//        if (myObj.nextLine().equalsIgnoreCase("A")) {
//            hotelSetupMode = SetupMode.MANUAL;
//        }

        /* Hotel Name */

        String hotelName = "My Hotel";
        if (hotelSetupMode == SetupMode.MANUAL) {
            System.out.println("Choose the name of your hotel");
            hotelName = myObj.nextLine();
        }
        this.hotel.setName(hotelName);
        System.out.println("\nHOTEL | " + this.hotel.getName());

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
                System.out.println("You have " + availableArea + " square meters available for your rooms");
                System.out.println("Room " + roomNumber);

                boolean correctSize = false;

                while (!correctSize) {
                    System.out.println("Choose the size for this room (SINGLE, DOUBLE, TRIPLE or QUADRUPLE)");
                    String inputSizeString = myObj.nextLine();
                    inputSize = Room.stringToRoomSize(inputSizeString);

                    if (inputSize == null) {
                        System.out.println("You have not chosen a size correctly, please enter SINGLE, DOUBLE, TRIPLE or QUADRUPLE");
                    }
                    else if (availableArea < Room.minArea(RoomType.STANDARD, inputSize, HotelStars.ONE)) {
                        System.out.println("The minimum area for this room is higher than the square meters you have available for your rooms. Please choose another room size.");
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
                    System.out.println("Choose the type for this room (STANDARD, SUPERIOR, DELUXE, JUNIOR_SUITE or SUITE)");
                    String inputTypeString = myObj.nextLine();
                    inputType = Room.stringToRoomType(inputTypeString);

                    if (inputType == null) {
                        System.out.println("You have not chosen a type correctly, please enter STANDARD, SUPERIOR, DELUXE, JUNIOR_SUITE or SUITE");
                    } else if (Room.minArea(inputType, inputSize, HotelStars.ONE) == -1) {
                        System.out.println("The room type selected is not compatible with the room size selected. Please choose another room type.");
                    } else if (availableArea < Room.minArea(inputType, inputSize, HotelStars.ONE)) {
                        System.out.println("The minimum area for this room is higher than the square meters you have available for your rooms. Please choose another room type.");
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
                    System.out.println("Choose the number of square meters for this room. These are the requirements for the selected room size and type:");
                    int area1 = Room.minArea(inputType, inputSize, HotelStars.ONE);
                    int area2 = Room.minArea(inputType, inputSize, HotelStars.TWO);
                    int area3 = Room.minArea(inputType, inputSize, HotelStars.THREE);
                    int area4 = Room.minArea(inputType, inputSize, HotelStars.FOUR);
                    int area5 = Room.minArea(inputType, inputSize, HotelStars.FIVE);
                    System.out.println(area1 + " m²: * | " + area2 + " m²: ** | " + area3 + " m²: *** | " + area4 + " m²: **** | " + area5 + " m²: *****");
                    area = Room.parseArea(myObj.nextLine());

                    if (area <= 0) {
                        System.out.println("You have not chosen an area correctly, please enter a positive number.");
                    }
                    else if (area < Room.minArea(inputType, inputSize, HotelStars.ONE)) {
                        System.out.println("The minimum area for this room is higher. Please choose a bigger area.");
                    }
                    else if (availableArea < area) {
                        System.out.println("The area selected is higher than the square meters you have available for your rooms. Please choose another area.");
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
            System.out.println("All the square meters available were allocated to the rooms.");
        }

        this.hotel.printRooms();

    }

    public void advanceDate() {
        setGameDate(gameDate.plusDays(1));
        System.out.println("GAME DATE | " + gameDate + "\n");
        this.generateGuests();
        this.generateReservations();
        this.generateCheckins();
        this.generateCheckouts();
        this.hotel.printRooms();
    }

    // Methods / Guest

    public void generateGuests() {

        for (int i=0; i<10; i++) {

            if (rand.nextDouble() <= reservationRate) {
                double gaussian = rand.nextGaussian();

                while (gaussian < -1.0 || gaussian > 1.0) {
                    gaussian = rand.nextGaussian();
                }

                int people = 1 + (int) Math.round((gaussian + 1.0) * 1.5);
                int nights = rand.nextInt(1,7);
                this.guests.add(new Guest(people, nights));
            }
        }

        this.printGuests();
    }

    public void printGuests() {
        String s = "";
        for(Guest item: this.guests) {
            if (item.getStatus() == GuestStatus.WAITING || item.getStatus() == GuestStatus.STAYING) {
                s = s + "\t" + item + "\n";
            }
        }

        System.out.println("GUESTS:\n" + s);
    }

    // Methods / Reservation

    public void reserveRoom(Room room, Guest guest, LocalDate startDate, LocalDate endDate) {
        room.setStatus(RoomStatus.RESERVED);
        guest.setStatus(GuestStatus.STAYING);
        Reservation reservation = new Reservation(room, guest, startDate, endDate);
        this.hotel.getReservations().add(reservation);
    }

    public void generateReservations() {

        for(Guest guest: this.guests) {

            if (guest.getStatus() == GuestStatus.WAITING) {

                boolean roomFound = false;
                for (int size = guest.getPeople(); size <= 4; size++) {
                    for (Room room : this.hotel.getRooms()) {
                        if (!roomFound && room.getStatus() == RoomStatus.FREE && room.getSizeNumber() == size) {
                            roomFound = true;
                            reserveRoom(room, guest, gameDate, gameDate.plusDays(guest.getNights()));
                        }
                    }
                }
            }
        }

        this.hotel.printReservations();

    }

    public void checkinGuest(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        System.out.println("\tGuest " + reservation.getGuest() + " checked in to Room " + reservation.getRoom());
    }

    public void checkoutGuest(Reservation reservation) {
        reservation.getRoom().setStatus(RoomStatus.FREE);
        reservation.getGuest().setStatus((GuestStatus.STAYED));
        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        System.out.println("\tGuest " + reservation.getGuest() + " checked out from Room " + reservation.getRoom());
    }

    public void generateCheckins() {

        System.out.println("CHECK INS:");

        for(Reservation reservation: this.hotel.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CONFIRMED && reservation.getStartDate().isEqual(getGameDate())) {
                checkinGuest(reservation);
            }
        }

        System.out.println();

    }

    public void generateCheckouts() {

        System.out.println("CHECK OUTS:");

        for(Reservation reservation: this.hotel.getReservations()) {
            if (reservation.getStatus() == ReservationStatus.CHECKED_IN && reservation.getEndDate().isEqual(getGameDate())) {
                checkoutGuest(reservation);
            }
        }

        System.out.println();
    }

    // Methods / Employee

    public void initEmployees() {

        this.hotel.getEmployees().add(new Employee("John Doe", EmployeeRole.ASSISTANT_GENERAL_MANAGER));

        this.hotel.printEmployees();
    }

}