package yappal.task;

import yappal.YapPalException;

/**
 * Abstract class for all Tasks
 */
public abstract class Task {
    private String name;
    private boolean marked;

    /**
     * Initialisation for fields common to all classes
     *
     * @param command User input for creating a task
     * @param offset Offset of name characters based on task type
     * @throws YapPalException If no name was specified
     */
    public Task(String command, int offset) throws YapPalException {
        int nameEndIndex = command.indexOf('/');
        if (nameEndIndex == -1) {
            this.name = command.substring(offset);
        }
        else {
            if (nameEndIndex <= offset) {
                throw new YapPalException("No task name specified, please try again!");
            }
            this.name = command.substring(offset, nameEndIndex);
        }
        this.marked = command.contains("/mark");
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Generates the command for creating the task
     *
     * @return A String that, when input, will generate a task with the same details
     */
    public String saveString() {
        if (this.isMarked()) {
            return this.name + " /mark";
        }
        return name;
    }

    @Override
    public String toString() {
        if (this.isMarked()) {
            return "[X] " + this.name;
        }
        return "[ ] " + this.name;
    }
}
