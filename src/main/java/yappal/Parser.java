package yappal;

import java.util.Scanner;

import yappal.task.Deadline;
import yappal.task.Event;
import yappal.task.Task;
import yappal.task.ToDo;

/**
 * Parser object that parses user input into arguments
 */
class Parser {
    private Scanner scanner;
    private String lastCommand;

    /**
     * Instantiates a parser object for parsing user inputs
     */
    public Parser() {
        this.scanner = new Scanner(System.in); // Scanner for reading user inputs
        this.lastCommand = ""; // command cache
    }

    /**
     * Gets user input and outputs the action to be taken by the chatbot
     *
     * @return The next state of the chatbot
     */
    public YapPal.State listen(String command) {
        this.lastCommand = command;
        if (this.lastCommand.equals("bye")) {
            return YapPal.State.TERMINATE;
        }
        if (this.lastCommand.equals("list")) {
            return YapPal.State.LIST;
        } else if (this.lastCommand.length() > 4 && this.lastCommand.startsWith("mark")) {
            return YapPal.State.MARK;
        } else if (this.lastCommand.length() > 4 && this.lastCommand.startsWith("find")) {
            return YapPal.State.FIND;
        } else if (this.lastCommand.length() > 6 && this.lastCommand.startsWith("unmark")) {
            return YapPal.State.UNMARK;
        } else if (this.lastCommand.length() > 6 && this.lastCommand.startsWith("delete")) {
            return YapPal.State.DELETE;
        }
        return YapPal.State.ADD;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    /**
     * Parses and returns the index for commands that contain indices
     *
     * @param state The action to be taken by the chatbot
     * @return The target index of the command
     */
    public int getLastInd(YapPal.State state) {
        if (state == YapPal.State.MARK) {
            return Integer.parseInt(this.lastCommand.substring(5));
        }
        return Integer.parseInt(this.lastCommand.substring(7));
    }

    /**
     * Given a task creation command, parses the command to create the task
     *
     * @param command The task creation command
     * @return The task created from the task creation command
     * @throws YapPalException If the command does not create a task
     */
    public Task determineTask(String command) throws YapPalException {
        if (command.length() > 4 && command.startsWith("todo")) {
            return new ToDo(command);
        }
        if (command.length() > 8 && command.startsWith("deadline")) {
            return new Deadline(command);
        }
        if (command.length() > 5 && command.startsWith("event")) {
            return new Event(command);
        }
        // if none of the above:
        throw new YapPalException("Invalid task, please try again!");
    }
}
