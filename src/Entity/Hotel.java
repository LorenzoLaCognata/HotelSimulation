package Entity;

import Enum.SetupMode;
import Enum.InputType;
import Enum.RoomSize;
import Enum.RoomType;
import Enum.HotelStars;
import IO.Input;
import IO.Log;
import Manager.EmployeeManager;
import Manager.FinancialManager;
import Manager.ReservationManager;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Hotel
 */
public class Hotel {

    private String name;
    /**
     * Class to manage the hotel employees
     */
    public final EmployeeManager employeeManager = new EmployeeManager();
    /**
     * Class to manage the hotel financials
     */
    public final FinancialManager financialManager = new FinancialManager();
    /**
     * Class to manage the hotel reservations
     */
    public final ReservationManager reservationManager = new ReservationManager();

    /**
     * Sets the name of the hotel
     * @param name Name of the hotel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Initialize the hotel name and hotel rooms
     * @param setupMode SetupMode used to initialize the hotel
     * @param gameDate Game date
     * @param random Random number generator
     */
    public void initHotel(SetupMode setupMode, LocalDate gameDate, Random random) {

        /* Hotel Name */

        String hotelName = "My Hotel";

        if (setupMode == SetupMode.MANUAL) {
            hotelName = Input.askQuestion("Choose the name of your hotel", InputType.STRING);
        }

        setName(hotelName);
        Log.print("HOTEL",  Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
        Log.print("\t" + name);

        int availableArea = 200;

        if (setupMode == SetupMode.MANUAL) {
            availableArea = Input.askQuestion("\nChoose the size of your hotel in m²", 100, 500);
        }

        Log.print("\tAvailable area for your hotel will be " + availableArea + " m²");

        BigDecimal rent = new BigDecimal(availableArea).multiply(new BigDecimal(50));
        financialManager.setRent(rent);
        Log.print("\tRent for your hotel will cost " + Log.currencyToString(rent));
        Log.print("");

        int roomNumber = 1;

        RoomSize inputSize;
        RoomType inputType = RoomType.STANDARD;
        int area = 25;
        BigDecimal rate = new BigDecimal(100);

        while (availableArea >= Room.minArea(RoomType.STANDARD, RoomSize.SINGLE, HotelStars.ONE)) {

            double roomRandom = random.nextDouble();

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

                List<String> questionOptions = Input.roomSizeOptions(RoomSize.maxRoomSize(RoomType.STANDARD, HotelStars.ONE, availableArea));
                String answer = Input.askQuestion("Choose the size for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputSize = RoomSize.parseRoomSize(answer);

                questionOptions = Input.roomTypeOptions(RoomType.maxRoomType(inputSize));
                answer = Input.askQuestion("Choose the type for this room", questionOptions, InputType.SINGLE_CHOICE_TEXT);
                inputType = RoomType.parseRoomType(answer);

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

            reservationManager.addRoom(new Room(roomNumber, inputSize, inputType, area, rate));
            availableArea = availableArea - area;
            roomNumber = roomNumber + 1;

        }

        Log.print("ROOMS", Color.BLACK, Color.WHITE, StyleConstants.Bold, StyleConstants.Underline);
        Log.print(reservationManager.roomsToString(reservationManager.getRooms()));

        employeeManager.defaultEmployee();

        Log.print("GAME DATE | " + gameDate, Color.WHITE, Color.BLACK, StyleConstants.Bold, new Object());
        Log.print("");

    }

}