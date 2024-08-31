package Utils;

import Enums.LogStatus;

import java.text.NumberFormat;
import java.util.Locale;

public class Logger {

    static NumberFormat formatter = NumberFormat.getInstance(Locale.US);

    public static String formatNumber(int integer) {
        return formatter.format(integer);
    }

    public static void logln(String string) {
        System.out.println(string);
    }

    public static void logln(LogStatus logStatus, String string) {
        if (logStatus == LogStatus.ACTIVE) {
            System.out.println(string);
        }
    }
    public static void log(LogStatus logStatus, String string) {
        if (logStatus == LogStatus.ACTIVE) {
            System.out.print(string);
        }
    }

    public static void log(String string) {
        System.out.print(string);
    }

}
