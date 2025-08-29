public class Event extends Task{
    private String start;
    private String end;

    public Event(String command) throws YapPalException{
        super(command, 6);
        this.start = start;
        this.end = end;
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

        if (startIndex < endIndex) {
            if (endIndex <= startIndex + EVENT_START_OFFSET) {
                throw new YapPalException("No /from field specified, please try again!");
            }
            if (endIndex + EVENT_END_OFFSET >= command.length()) {
                throw new YapPalException("No /to specified, please try again!");
            }
            this.start = command.substring(startIndex + EVENT_START_OFFSET, endIndex - 1);
            this.end = command.substring(endIndex + EVENT_END_OFFSET);
        } else {
            if (startIndex <= endIndex + EVENT_END_OFFSET) {
                throw new YapPalException("No /to field specified, please try again!");
            }
            if (startIndex + EVENT_START_OFFSET >= command.length()) {
                throw new YapPalException("No /from specified, please try again!");
            }
            this.end = command.substring(endIndex + EVENT_END_OFFSET, startIndex - 1);
            this.start = command.substring(startIndex + EVENT_START_OFFSET);
        }
    }

    @Override
    public String saveString() {
        return "event " + super.saveString() + " /from: " + this.start + " /to " + this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + this.start + " to: " + this.end + ")";
    }
}
