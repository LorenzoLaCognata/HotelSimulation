import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private Hotel hotel = new Hotel();
    private List<Guest> guests = new ArrayList<>();

    private final Random rand = new Random();

    public GameManager() {
            System.out.println("\nWelcome to Hotel Simulation!\n");
    }

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
        RoomSize inputSize;
        RoomType inputType;
        int area;

        while (availableArea >= Room.minArea(RoomType.STANDARD, RoomSize.SINGLE, HotelStars.ONE)) {

            /* Room Size */

            inputSize = RoomSize.TRIPLE;

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

    public void printGuests() {
        String s = "";
        for(Guest item: this.guests) {
            s = s + item + "\n";
        }

        System.out.println("GUESTS:\n" + s);
    }

    public void initGuests() {

        for (int i=0; i<10; i++) {

            double gaussian = rand.nextGaussian();

            while (gaussian < -1.0 || gaussian > 1.0) {
                gaussian = rand.nextGaussian();
            }

            int size = 1 + (int) Math.round((gaussian + 1.0) * 1.5);
            this.guests.add(new Guest(size));
        }

        this.printGuests();
        System.out.println("\nGoodbye!");
    }

}