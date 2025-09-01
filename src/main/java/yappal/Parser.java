package yappal;

import yappal.task.Deadline;
import yappal.task.Event;
import yappal.task.Task;
import yappal.task.ToDo;

import java.util.Scanner;

class Parser {
    private Scanner scanner;
    private String lastCommand;

    public Parser() {
        this.scanner = new Scanner(System.in);
        this.lastCommand = "";
    }

    public YapPal.State listen() {
        this.lastCommand = this.scanner.nextLine();
        if (this.lastCommand.equals("bye")) {
            return YapPal.State.TERMINATE;
        }
        if (this.lastCommand.equals("list")) {
            return YapPal.State.LIST;
        } else if (this.lastCommand.length() > 4 && this.lastCommand.startsWith("mark")) {
            return YapPal.State.MARK;
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

    public int getLastInd(YapPal.State state) {
        if (state == YapPal.State.MARK) {
            return Integer.parseInt(this.lastCommand.substring(5));
        }
        return Integer.parseInt(this.lastCommand.substring(7));
    }

    public Task determineTask (String command) throws YapPalException{
        if (command.length() > 4 && command.startsWith("todo")) {
            return new ToDo(command);
        } else if (command.length() > 8 && command.startsWith("deadline")) {
            return new Deadline(command);
        } else if (command.length() > 5 && command.startsWith("event")) {
            return new Event(command);
        }
        // if none of the above:
        throw new YapPalException("Invalid task, please try again!");
    }
}