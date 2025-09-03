package yappal.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import yappal.YapPalException;

/**
 * Deadline class that represents a Deadline
 */
public class Deadline extends Task {
    private LocalDate deadline;
    // for formatting dates in output
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final static int OFFSET_BY = 4;
    private final static int OFFSET_NAME = 9;

    /**
     * Instantiates a Deadline object
     *
     * @param command User's input for creating a Deadline Object
     * @throws YapPalException If user's inputs do not follow the deadline instantiation format
     */
    public Deadline(String command) throws YapPalException {
        super(command, OFFSET_NAME);

        int deadlineIndex = command.indexOf("/by");
        if (deadlineIndex == -1) {
            throw new YapPalException("No /by variable specified, please try again!");
        }
        if (deadlineIndex + OFFSET_BY >= command.length()) {
            throw new YapPalException("No deadline specified, please try again!");
        }
        try {
            this.deadline = LocalDate.parse(command.substring(deadlineIndex + OFFSET_BY));
        } catch (DateTimeParseException exception) {
            throw new YapPalException("Please use yyyy-mm-dd format to input the date!");
        }
    }

    @Override
    public String saveString() {
        return "deadline " + super.saveString() + " /by " + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by " + this.deadline.format(Deadline.FORMATTER) + ")";
    }
}
