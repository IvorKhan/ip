package yappal.task;

import yappal.YapPalException;

/**
 * ToDo class representing a ToDo Task
 */
public class ToDo extends Task {
    /**
     * Instantiates a ToDo object
     *
     * @param command User input for creating the ToDo object
     * @throws YapPalException If user input is missing information
     */
    public ToDo(String command) throws YapPalException {
        super(command, 5);
    }

    @Override
    public String saveString() {
        return "todo " + super.saveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
