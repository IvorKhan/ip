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

        int hourIndex = command.indexOf("/hr");
        int minuteIndex = command.indexOf("/min");

        if (hourIndex + OFFSET_HR >= command.length()) {
            throw new YapPalException("No hours specified, please try again!");
        }
        if (hourIndex == -1) {
            this.hours = 0;
        } else {
            try {
                if (hourIndex < minuteIndex) {
                    this.hours = Integer.parseInt(command.substring(hourIndex + OFFSET_HR, minuteIndex - 1).strip());
                } else {
                    this.hours = Integer.parseInt(command.substring(hourIndex + OFFSET_HR).strip());
                }
            } catch (NumberFormatException exception) {
                throw new YapPalException("Invalid argument for hours!");
            }
        }

        if (minuteIndex + OFFSET_MIN >= command.length()) {
            throw new YapPalException("No minutes specified, please try again!");
        }
        if (minuteIndex == -1) {
            this.minutes = 0;
        } else {
            try {
                if (minuteIndex < hourIndex) {
                    this.minutes = Integer.parseInt(command.substring(minuteIndex + OFFSET_MIN, hourIndex - 1).strip());
                } else {
                    this.minutes = Integer.parseInt(command.substring(minuteIndex + OFFSET_MIN).strip());
                }
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
