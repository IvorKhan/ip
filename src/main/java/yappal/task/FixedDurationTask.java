package yappal.task;

import yappal.YapPalException;

/**
 * FixedDurationTask class that represents a task that takes a fixed duration to complete.
 */
public class FixedDurationTask extends Task {
    private int hours;
    private int minutes;
    private final static int OFFSET_HOUR = 4;
    private final static int OFFSET_MIN = 5;
    private final static int OFFSET_NAME = 9;

    /**
     * Instantiates a FixedDurationTask object.
     *
     * @param command User's input for creating a FixedDurationTask Object.
     * @throws YapPalException If user's inputs do not follow the deadline instantiation format.
     */
    public FixedDurationTask(String command) throws YapPalException {
        super(command, OFFSET_NAME); // Assuming this is a constructor from a superclass

        validateInput(command);

        this.hours = extractHours(command);
        this.minutes = extractMinutes(command);

        validateDuration();
    }

    /**
     * Validates that the duration is positive and within acceptable limits.
     *
     * @throws YapPalException If the duration is invalid (e.g., negative, over 60 minutes).
     */
    private void validateInput(String command) throws YapPalException {
        int hourIndex = command.indexOf("/hr");
        int minuteIndex = command.indexOf("/min");

        if (hourIndex + OFFSET_HOUR >= command.length()) {
            throw new YapPalException("No hours specified, please try again!");
        }

        if (minuteIndex + OFFSET_MIN >= command.length()) {
            throw new YapPalException("No minutes specified, please try again!");
        }
    }

    /**
     * Extracts the hours from the command string.
     *
     * @param command The user input.
     * @return The parsed hours value.
     * @throws YapPalException If hours are invalid or not specified.
     */
    private int extractHours(String command) throws YapPalException {
        int hourIndex = command.indexOf("/hr");
        if (hourIndex == -1) {
            return 0; // No hours specified
        }

        try {
            int minuteIndex = command.indexOf("/min");
            if (hourIndex < minuteIndex) {
                return Integer.parseInt(command.substring(hourIndex + OFFSET_HOUR, minuteIndex - 1).strip());
            } else {
                return Integer.parseInt(command.substring(hourIndex + OFFSET_HOUR).strip());
            }
        } catch (NumberFormatException exception) {
            throw new YapPalException("Invalid argument for hours!");
        }
    }

    /**
     * Extracts the minutes from the command string.
     *
     * @param command The user input.
     * @return The parsed minutes value.
     * @throws YapPalException If minutes are invalid or not specified.
     */
    private int extractMinutes(String command) throws YapPalException {
        int minuteIndex = command.indexOf("/min");
        if (minuteIndex == -1) {
            return 0; // No minutes specified
        }

        try {
            int hourIndex = command.indexOf("/hr");
            if (minuteIndex < hourIndex) {
                return Integer.parseInt(command.substring(minuteIndex + OFFSET_MIN, hourIndex - 1).strip());
            } else {
                return Integer.parseInt(command.substring(minuteIndex + OFFSET_MIN).strip());
            }
        } catch (NumberFormatException exception) {
            throw new YapPalException("Invalid argument for minutes!");
        }
    }

    /**
     * Validates that the duration is positive and within acceptable limits.
     *
     * @throws YapPalException If the duration is invalid (e.g., negative, over 60 minutes).
     */
    private void validateDuration() throws YapPalException {
        if (this.minutes == 0 && this.hours == 0) {
            throw new YapPalException("No duration specified!");
        }
        if (this.minutes >= 60 || this.hours < 0 || this.minutes < 0) {
            throw new YapPalException("Invalid duration specified!");
        }
    }

    @Override
    public String saveString() {
        return "duration " + super.saveString() + " /hr " + this.hours + " /min " + this.minutes;
    }

    @Override
    public String toString() {
        String durationString = " (takes ";
        if (this.hours != 0) {
            if (this.hours > 1) {
                durationString += this.hours + " hours";
            } else {
                durationString += "1 hour";
            }
            if (this.minutes != 0) {
                durationString += " ";
            }
        }
        if (this.minutes != 0) {
            if (this.minutes > 1) {
                durationString += this.minutes + " minutes";
            } else {
                durationString += "1 minute";
            }
        }
        durationString += ")";
        return "[F]" + super.toString() + durationString;
    }
}
