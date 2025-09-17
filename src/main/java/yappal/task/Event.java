package yappal.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import yappal.YapPalException;

/**
 * Event class representing an Event.
 */
public class Event extends Task {
    private LocalDate start;
    private LocalDate end;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final static int OFFSET_FROM = 6;
    private final static int OFFSET_TO = 4;
    private final static int OFFSET_NAME = 6;

    /**
     * Instantiates an Event object.
     *
     * @param command User input for creating the Event object.
     * @throws YapPalException If user input is missing information.
     */
    public Event(String command) throws YapPalException {
        super(command, OFFSET_NAME);

        String[] dateStrings = extractDates(command);
        this.start = parseDate(dateStrings[0]);
        this.end = parseDate(dateStrings[1]);

        validateDates();
    }

    /**
     * Extracts the start and end dates from the command string.
     *
     * @param command The user input.
     * @return A String array containing the start and end date strings.
     * @throws YapPalException If dates are not found or invalid.
     */
    private String[] extractDates(String command) throws YapPalException {
        int startIndex = command.indexOf("/from");
        int endIndex = command.indexOf("/to");

        if (startIndex == -1) {
            throw new YapPalException("No /from flag specified, please try again!");
        }
        if (endIndex == -1) {
            throw new YapPalException("No /to flag specified, please try again!");
        }

        String startString;
        String endString;

        if (startIndex < endIndex) {
            validateOffsets(startIndex, endIndex, true, command.length());

            startString = command.substring(startIndex + OFFSET_FROM, endIndex - 1);
            endString = command.substring(endIndex + OFFSET_TO);
        } else {
            validateOffsets(endIndex, startIndex, false, command.length());

            endString = command.substring(endIndex + OFFSET_TO, startIndex - 1);
            startString = command.substring(startIndex + OFFSET_FROM);
        }

        return new String[]{startString, endString};
    }

    /**
     * Validates the start and end flags' positions and ensures proper input format.
     *
     * @param firstIndex The index of the first flag.
     * @param secondIndex The index of the second flag.
     * @param commandLength The length of the command.
     * @throws YapPalException If any validation fails.
     */
    private void validateOffsets(int firstIndex, int secondIndex, boolean isStartFirst,
            int commandLength) throws YapPalException {
        int firstOffset = OFFSET_TO;
        int secondOffset = OFFSET_FROM;
        if (isStartFirst) {
            firstOffset = OFFSET_FROM;
            secondOffset = OFFSET_TO;
        }
        if (secondIndex <= firstIndex + firstOffset) {
            throw new YapPalException("No /from field specified, please try again!");
        }
        if (secondIndex + secondOffset >= commandLength) {
            throw new YapPalException("No /to specified, please try again!");
        }
    }

    /**
     * Parses the given date string into a LocalDate object.
     *
     * @param dateString The date in string format.
     * @return The parsed LocalDate object.
     * @throws YapPalException If the date format is incorrect.
     */
    private LocalDate parseDate(String dateString) throws YapPalException {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException exception) {
            throw new YapPalException("Please use yyyy-mm-dd format to input the date!");
        }
    }

    /**
     * Validates that the start date is before or equal to the end date.
     *
     * @throws YapPalException If the start date is after the end date.
     */
    private void validateDates() throws YapPalException {
        if (this.start.isAfter(this.end)) {
            throw new YapPalException("Start date is after end date, please try again!");
        }
    }

    @Override
    public String saveString() {
        return "event " + super.saveString() + " /from " + this.start + " /to " + this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + this.start.format(Event.FORMATTER)
               + " to: " + this.end.format(Event.FORMATTER) + ")";
    }
}
