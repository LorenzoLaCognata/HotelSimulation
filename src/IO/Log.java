package IO;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 */
public class Log {

    private static final DecimalFormat currency = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ITALY);

    private static final JTextPane textPane = new JTextPane();
    private static final Document document = textPane.getStyledDocument();
    private static SimpleAttributeSet set = new SimpleAttributeSet();

    public static JTextPane getTextPane() {
        return textPane;
    }

    /**
     *
     */
    public static void initLog() {

        DecimalFormatSymbols formatSymbols = currency.getDecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(',');
        formatSymbols.setDecimalSeparator('.');

        currency.setDecimalFormatSymbols(formatSymbols);
        currency.setMinimumFractionDigits(2);
        currency.setMaximumFractionDigits(2);

        textPane.setMargin( new Insets(10,10,10,10) );

        Log.print("\nWelcome to Hotel Simulation!\n", Color.BLACK, Color.WHITE, new Object(), StyleConstants.Underline);

    }

    /**
     *
     * @param text
     */
    public static void print(String text) {

        set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, Color.DARK_GRAY);
        textPane.setCharacterAttributes(set, true);

        try {
            document.insertString(document.getLength(), text + "\n", set);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        textPane.setCaretPosition(textPane.getDocument().getLength());
    }

    /**
     *
     * @param text
     * @param color
     */
    public static void print(String text, Color color) {

        set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);

        textPane.setCharacterAttributes(set, true);

        try {
            document.insertString(document.getLength(), text + "\n", set);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        textPane.setCaretPosition(textPane.getDocument().getLength());
    }

    /**
     *
     * @param text
     * @param foreground
     * @param background
     * @param bold
     * @param underlined
     */
    public static void print(String text, Color foreground, Color background, Object bold, Object underlined) {

        set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, foreground);
        StyleConstants.setBackground(set, background);

        if (bold.equals(StyleConstants.Bold)) {
            StyleConstants.setBold(set, true);
        }

        if (underlined.equals(StyleConstants.Underline)) {
            StyleConstants.setUnderline(set, true);
        }

        textPane.setCharacterAttributes(set, true);

        try {
            document.insertString(document.getLength(), text + "\n", set);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        textPane.setCaretPosition(textPane.getDocument().getLength());
    }

    /**
     *
     * @param amount
     * @return
     */
    public static String currencyToString(BigDecimal amount) {
        return currency.format(amount);
    }

}