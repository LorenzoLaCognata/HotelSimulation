package IO;

import Enum.InputType;
import Enum.RoomSize;
import Enum.RoomType;
import Entity.TimePeriod;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class Input {

    private static final TextField textField = new TextField();
    private static volatile boolean input = false;

    /**
     *
     * @return
     */
    public static TextField getTextField() {
        return textField;
    }

    /**
     *
     * @return
     */
    public static boolean isInput() {
        return input;
    }

    /**
     *
     * @param input
     */
    public static void setInput(boolean input) {
        Input.input = input;
    }



    private static String getLetter(int value) {
        char letter = (char) ('A' + value);
        return String.valueOf(letter);
    }

    /**
     *
     * @param string
     * @return
     */
    public static Integer parseNumber(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static LocalTime parseTime(String string) {
        try {
            return LocalTime.parse(string, DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static String questionOptionsString(String question, List<String> options, InputType inputType) {

        String questionOptions = question;

        if (!question.isEmpty()) {
            questionOptions = questionOptions + ": ";
        }

        for (int i = 0; i< options.size(); i++) {

            if (i > 0) {
                if (i < options.size() - 1) {
                    questionOptions = questionOptions + ", ";
                } else {
                    questionOptions = questionOptions + " or ";
                }
            }

            if (inputType == InputType.SINGLE_CHOICE_NUMBER) {
                questionOptions = questionOptions + options.get(i);
            }
            else {
                questionOptions = questionOptions + options.get(i) + " (" + getLetter(i) + ")";
            }
        }

        return questionOptions;
    }

    /**
     *
     * @param question
     * @param options
     * @param inputType
     * @return
     */
    public static String askQuestion(String question, List<String> options, InputType inputType) {

        String answer = "";
        String inputString;

        boolean validAnswer = false;
        int answerCounter = 0;

        while (!validAnswer) {

            if (answerCounter > 0) {
                Log.printColor(Color.RED, "Error! Answer not valid!");
            }

            Log.print(questionOptionsString(question, options, inputType));

            while (!input) {
                Thread.onSpinWait();
            }

            inputString = textField.getText();
            textField.setText("");
            input = false;

            answerCounter++;
            Log.print("");

            for (int i = 0; i < options.size(); i++) {
                if (inputType == InputType.SINGLE_CHOICE_NUMBER) {
                    if (inputString.equalsIgnoreCase(options.get(i))) {
                        validAnswer = true;
                        answer = options.get(i);
                    }
                }
                else {
                    if (inputString.equalsIgnoreCase(getLetter(i))) {
                        validAnswer = true;
                        answer = options.get(i);
                    }
                }
            }

        }

        return answer;

    }

    /**
     *
     * @param question
     * @param minNumber
     * @param maxNumber
     * @return
     */
    public static int askQuestion(String question, int minNumber, int maxNumber) {

        String inputString;
        int answerNumber = -1;

        boolean validAnswer = false;
        int answerCounter = 0;

        while (!validAnswer) {

            if (answerCounter > 0) {
                Log.printColor(Color.RED, "Error! Answer not valid!");
            }

            Log.print(question);

            inputString = textField.getText();
            textField.setText("");
            input = false;
            answerCounter++;
            Log.print("");

            answerNumber = parseNumber(inputString);

            if (answerNumber > 0 && answerNumber >= minNumber && answerNumber <= maxNumber) {
                validAnswer = true;
            }

        }

        return answerNumber;

    }

    /**
     *
     * @param question
     * @param inputType
     * @return
     */
    public static String askQuestion(String question, InputType inputType) {

        String answer = "";
        String inputString;

        if (inputType == InputType.TIME_INTERVAL) {

            boolean validAnswer = false;
            int answerCounter = 0;

            while (!validAnswer) {

                if (answerCounter > 0) {
                    Log.printColor(Color.RED, "Error! Answer not valid!");
                }

                Log.print(question);

                inputString = textField.getText();
                textField.setText("");
                input = false;
                answerCounter++;
                Log.print("");

                if (inputString.isEmpty()) {
                    validAnswer = true;
                    answer = inputString;
                }

                else {
                    TimePeriod answerTimePeriod = TimePeriod.parseTimePeriod(inputString);

                    if (answerTimePeriod != null) {
                        validAnswer = true;
                        answer = answerTimePeriod.toString();
                    }
                }

            }

        }

        else {
            Log.print(question);
            inputString = textField.getText();
            textField.setText("");
            input = false;
            Log.print("");
            answer = inputString;
        }

        return answer;
    }

    /**
     *
     * @param maxRoomSize
     * @return
     */
    public static List<String> roomSizeOptions(RoomSize maxRoomSize) {

        List<String> allowedRoomSizes = new ArrayList<>();
        if (maxRoomSize == RoomSize.SINGLE || maxRoomSize == RoomSize.DOUBLE || maxRoomSize == RoomSize.TRIPLE || maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("SINGLE");
        }
        if (maxRoomSize == RoomSize.DOUBLE || maxRoomSize == RoomSize.TRIPLE || maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("DOUBLE");
        }
        if (maxRoomSize == RoomSize.TRIPLE || maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("TRIPLE");
        }
        if (maxRoomSize == RoomSize.QUADRUPLE) {
            allowedRoomSizes.add("QUADRUPLE");
        }
        return allowedRoomSizes;

    }

    /**
     *
     * @param maxRoomType
     * @return
     */
    public static List<String> roomTypeOptions(RoomType maxRoomType) {

        List<String> allowedRoomTypes = new ArrayList<>();
        if (maxRoomType == RoomType.STANDARD || maxRoomType == RoomType.SUPERIOR || maxRoomType == RoomType.DELUXE || maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("STANDARD");
        }
        if (maxRoomType == RoomType.SUPERIOR || maxRoomType == RoomType.DELUXE || maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("SUPERIOR");
        }
        if (maxRoomType == RoomType.DELUXE || maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("DELUXE");
        }
        if (maxRoomType == RoomType.JUNIOR_SUITE || maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("JUNIOR_SUITE");
        }
        if (maxRoomType == RoomType.SUITE) {
            allowedRoomTypes.add("SUITE");
        }
        return allowedRoomTypes;

    }
}