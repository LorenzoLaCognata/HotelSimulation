import java.util.List;
import java.util.Scanner;
import Enum.InputType;

public class Input {

    public static final Scanner scanner = new Scanner(System.in);

    public static String getLetter(int value) {
        char letter = (char) ('A' + value);
        return String.valueOf(letter);
    }

    private static String questionOptionsString(String question, List<String> options) {

        String questionOptions = question + ": ";

        for (int i = 0; i< options.size(); i++) {

            if (i > 0) {
                if (i < options.size() - 1) {
                    questionOptions = questionOptions + ", ";
                } else {
                    questionOptions = questionOptions + " or ";
                }
            }

            questionOptions = questionOptions + options.get(i) + " (" + getLetter(i) + ")";
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
                Log.printc(Log.RED, "Error! Wrong answer selected!");
            }

            Log.print(questionOptionsString(question, options));

            inputString = scanner.nextLine();
            answerCounter++;

            for (int i = 0; i < options.size(); i++) {
                if (inputString.equalsIgnoreCase(getLetter(i))) {
                    validAnswer = true;
                    answer = options.get(i);
                }
            }

        }

        return answer;

    }

    public static String askQuestion(String question, InputType inputType) {

        String answer;
        String inputString;

        Log.print(question);
        inputString = scanner.nextLine();
        answer = inputString;

        return answer;

    }

}