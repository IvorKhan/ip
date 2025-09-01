import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    private LocalDate start;
    private LocalDate end;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String command) throws YapPalException{
        super(command, 6);
        int EVENT_START_OFFSET = 6;
        int EVENT_END_OFFSET = 4;

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
            if (endIndex <= startIndex + EVENT_START_OFFSET) {
                throw new YapPalException("No /from field specified, please try again!");
            }
            if (endIndex + EVENT_END_OFFSET >= command.length()) {
                throw new YapPalException("No /to specified, please try again!");
            }
            startString = command.substring(startIndex + EVENT_START_OFFSET, endIndex - 1);
            endString = command.substring(endIndex + EVENT_END_OFFSET);
        } else {
            if (startIndex <= endIndex + EVENT_END_OFFSET) {
                throw new YapPalException("No /to field specified, please try again!");
            }
            if (startIndex + EVENT_START_OFFSET >= command.length()) {
                throw new YapPalException("No /from specified, please try again!");
            }
            endString = command.substring(endIndex + EVENT_END_OFFSET, startIndex - 1);
            startString = command.substring(startIndex + EVENT_START_OFFSET);
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
        return "[E]" + super.toString() + "(from: " + this.start.format(Event.FORMATTER) + " to: " + this.end.format(Event.FORMATTER) + ")";
    }
}
