package IO;

import Enum.InputType;
import Record.TimePeriod;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Input {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getLetter(int value) {
        char letter = (char) ('A' + value);
        return String.valueOf(letter);
    }

    public static Integer parseNumber(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

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

    public static String askQuestion(String question, List<String> options, InputType inputType) {

        String answer = "";
        String inputString;

        boolean validAnswer = false;
        int answerCounter = 0;

        while (!validAnswer) {

            if (answerCounter > 0) {
                Log.printColor(Log.RED, "Error! Answer not valid!");
            }

            Log.print(questionOptionsString(question, options, inputType));

            inputString = scanner.nextLine();
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

    public static int askQuestion(String question, int minNumber, int maxNumber) {

        String inputString;
        int answerNumber = -1;

        boolean validAnswer = false;
        int answerCounter = 0;

        while (!validAnswer) {

            if (answerCounter > 0) {
                Log.printColor(Log.RED, "Error! Answer not valid!");
            }

            Log.print(question);

            inputString = scanner.nextLine();
            answerCounter++;
            Log.print("");

            answerNumber = parseNumber(inputString);

            if (answerNumber > 0 && answerNumber >= minNumber && answerNumber <= maxNumber) {
                validAnswer = true;
            }

        }

        return answerNumber;

    }

    public static String askQuestion(String question, InputType inputType) {

        String answer = "";
        String inputString;

        if (inputType == InputType.TIME_INTERVAL) {

            boolean validAnswer = false;
            int answerCounter = 0;

            while (!validAnswer) {

                if (answerCounter > 0) {
                    Log.printColor(Log.RED, "Error! Answer not valid!");
                }

                Log.print(question);

                inputString = scanner.nextLine();
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
            inputString = scanner.nextLine();
            Log.print("");
            answer = inputString;
        }

        return answer;
    }

}