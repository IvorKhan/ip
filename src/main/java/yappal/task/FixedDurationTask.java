package yappal.task;

import yappal.YapPalException;

/**
 * FixedDurationTask class that represents a task that takes a fixed duration to complete
 */
public class FixedDurationTask extends Task {
    private int hours;
    private int minutes;
    private final static int OFFSET_HR = 4;
    private final static int OFFSET_MIN = 5;
    private final static int OFFSET_NAME = 9;

    /**
     * Instantiates a FixedDurationTask object
     *
     * @param command User's input for creating a FixedDurationTask Object
     * @throws YapPalException If user's inputs do not follow the deadline instantiation format
     */
    public FixedDurationTask(String command) throws YapPalException {
        super(command, OFFSET_NAME);

        int hrIndex = command.indexOf("/hr");
        if (hrIndex + OFFSET_HR >= command.length()) {
            throw new YapPalException("No hours specified, please try again!");
        }
        if (hrIndex == -1) {
            this.hours = 0;
        } else {
            try {
                this.hours = Integer.parseInt(command.substring(hrIndex + OFFSET_HR));
            } catch (NumberFormatException exception) {
                throw new YapPalException("Invalid argument for hours!");
            }
        }

        int minIndex = command.indexOf("/min");
        if (minIndex + OFFSET_MIN >= command.length()) {
            throw new YapPalException("No minutes specified, please try again!");
        }
        if (minIndex == -1) {
            this.minutes = 0;
        } else {
            try {
                this.minutes = Integer.parseInt(command.substring(minIndex + OFFSET_MIN));
            } catch (NumberFormatException exception) {
                throw new YapPalException("Invalid argument for minutes!");
            }
        }

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
            durationString += this.hours + " hours";
            if (this.minutes != 0) {
                durationString += " ";
            }
        }
        if (this.minutes != 0) {
            durationString += this.minutes + "minutes";
        }
        durationString += ")";
        return "[F]" + super.toString() + durationString;
    }
}
