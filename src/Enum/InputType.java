package Enum;

/**
 *
 */
public enum InputType {
    /**
     * Input is a string
     */
    STRING,
//    BOOLEAN,
//    INTEGER,
    /**
     * Input is a time interval (hh:mm-hh:mm format)
     */
    TIME_INTERVAL,
//    MULTIPLE_CHOICE,
    /**
     * Input is a single choice between strings
     */
    SINGLE_CHOICE_TEXT,
    /**
     * Input is a single choice between numbers
     */
    SINGLE_CHOICE_NUMBER
}
