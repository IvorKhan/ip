package yappal.task;

import yappal.YapPalException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Deadline class that represents a Deadline
 */
public class Deadline extends Task {
    private LocalDate deadline;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy"); // for formatting dates in output

    /**
     * Instantiates a Deadline object
     *
     * @param command User's input for creating a Deadline Object
     * @throws YapPalException If user's inputs do not follow the deadline instantiation format
     */
    public Deadline(String command) throws YapPalException {
        super(command, 9);
        int DEADLINE_DEADLINE_OFFSET = 4;

        int deadlineIndex = command.indexOf("/by");
        if (deadlineIndex == -1) {
            throw new YapPalException("No /by variable specified, please try again!");
        }
        if (deadlineIndex + DEADLINE_DEADLINE_OFFSET >= command.length()) {
            throw new YapPalException("No deadline specified, please try again!");
        }
        try {
            this.deadline = LocalDate.parse(command.substring(deadlineIndex + DEADLINE_DEADLINE_OFFSET));
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
