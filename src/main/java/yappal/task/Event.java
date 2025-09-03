package yappal.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import yappal.YapPalException;

/**
 * Event class representing an Event
 */
public class Event extends Task {
    private LocalDate start;
    private LocalDate end;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final static int OFFSET_FROM = 6;
    private final static int OFFSET_TO = 4;

    /**
     * Instantiates an Event object
     *
     * @param command User Input for creating the Event object
     * @throws YapPalException If user input is missing information
     */
    public Event(String command) throws YapPalException {
        super(command, 6);

        int startIndex = command.indexOf("/from");
        if (startIndex == -1) {
            throw new YapPalException("No /from flag specified, please try again!");
        }
        int endIndex = command.indexOf("/to");
        if (endIndex == -1) {
            throw new YapPalException("No /to flag specified, please try again!");
        }

        String startString;
        String endString;
        if (startIndex < endIndex) {
            if (endIndex <= startIndex + OFFSET_FROM) {
                throw new YapPalException("No /from field specified, please try again!");
            }
            if (endIndex + OFFSET_TO >= command.length()) {
                throw new YapPalException("No /to specified, please try again!");
            }
            startString = command.substring(startIndex + OFFSET_FROM, endIndex - 1);
            endString = command.substring(endIndex + OFFSET_TO);
        } else {
            if (startIndex <= endIndex + OFFSET_TO) {
                throw new YapPalException("No /to field specified, please try again!");
            }
            if (startIndex + OFFSET_FROM >= command.length()) {
                throw new YapPalException("No /from specified, please try again!");
            }
            endString = command.substring(endIndex + OFFSET_TO, startIndex - 1);
            startString = command.substring(startIndex + OFFSET_FROM);
        }
        try {
            this.start = LocalDate.parse(startString);
            this.end = LocalDate.parse(endString);
        } catch (DateTimeParseException exception) {
            throw new YapPalException("Please use yyyy-mm-dd format to input the date!");
        }
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
