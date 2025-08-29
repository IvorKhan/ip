public class Deadline extends Task {
    private String deadline;

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
        this.deadline = command.substring(deadlineIndex + DEADLINE_DEADLINE_OFFSET);
    }

    @Override
    public String saveString() {
        return "deadline " + super.saveString() + " /by: " + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by " + this.deadline + ")";
    }
}
