import java.util.Scanner;

public class Main {
    public void main(String[] args) {

        GameManager gameManager = new GameManager();
        System.out.println("Hotel created");

        Scanner myObj = new Scanner(System.in);

        System.out.println("Choose the name of your hotel");
//        String name = myObj.nextLine();
        String name = "Hotel P&P";

        gameManager.hotel.setName(name);
        System.out.println("Hotel | Name: " + gameManager.hotel.getName());

        int availableArea = 250;
        int roomNumber = 1;
        RoomSize inputSize = null;
        RoomType inputType = null;
        int area = 0;

        while (availableArea >= 11) {
            System.out.println("You have " + availableArea + " square meters available for your rooms");
            System.out.println("Room " + roomNumber);

            boolean correctSize = false;

            while (!correctSize) {
                System.out.println("Choose the size for this room (SINGLE, DOUBLE, TRIPLE or QUADRUPLE)");
//                String inputSizeString = myObj.nextLine();
                String inputSizeString = "TRIPLE";
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

            boolean correctType = false;

            while (!correctType) {
                System.out.println("Choose the type for this room (STANDARD, SUPERIOR, DELUXE, JUNIOR_SUITE or SUITE)");
//                String inputTypeString = myObj.nextLine();
                String inputTypeString = "STANDARD";
                inputType = Room.stringToRoomType(inputTypeString);

                if (inputType == null) {
                    System.out.println("You have not chosen a type correctly, please enter STANDARD, SUPERIOR, DELUXE, JUNIOR_SUITE or SUITE");
                }
                else if (Room.minArea(inputType, inputSize, HotelStars.ONE) == -1) {
                    System.out.println("The room type selected is not compatible with the room size selected. Please choose another room type.");
                }
                else if (availableArea < Room.minArea(inputType, inputSize, HotelStars.ONE)) {
                    System.out.println("The minimum area for this room is higher than the square meters you have available for your rooms. Please choose another room type.");
                }
                else {
                    correctType = true;
                }
            }

            boolean correctArea = false;

            while (!correctArea) {
                System.out.println("Choose the area for this room. These are the requirement for the selected room size and type:");
                int area1 = Room.minArea(inputType, inputSize, HotelStars.ONE);
                int area2 = Room.minArea(inputType, inputSize, HotelStars.TWO);
                int area3 = Room.minArea(inputType, inputSize, HotelStars.THREE);
                int area4 = Room.minArea(inputType, inputSize, HotelStars.FOUR);
                int area5 = Room.minArea(inputType, inputSize, HotelStars.FIVE);
                System.out.println(area1 + ": * | " + area2 + ": ** | " + area3 + ": *** | " + area4 + ": **** | " + area5 + ": *****");
//                area = Room.parseArea(myObj.nextLine());
                area = 25;

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

            gameManager.hotel.addRoom(new Room(roomNumber, inputSize, inputType, area));
            availableArea = availableArea - area;
            roomNumber = roomNumber + 1;

        }

        System.out.println("All the square meters available were allocated to the rooms.");
        System.out.println("Rooms:\n" + gameManager.hotel.getRooms());

    }
}