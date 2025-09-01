import java.util.Scanner;

public class Parser {
    private Scanner scanner;

    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    private static YapPal.State listen() {
        String command = this.scanner.nextLine();
        if (command.equals("bye")) {
            return YapPal.State.TERMINATE;
        }
        if (command.equals("list")) {
            YapPal.list();
        } else if (command.length() > 4 && command.startsWith("mark")) {
            int taskIndex = Integer.parseInt(command.substring(5));
            try {
                YapPal.mark(taskIndex);
            } catch (YapPalException exception) {
                Ui.printMsg(exception.toString());
            }
        } else if (command.length() > 6 && command.startsWith("unmark")) {
            int taskIndex = Integer.parseInt(command.substring(7));
            try {
                YapPal.unmark(taskIndex);
            } catch (YapPalException exception) {
                Ui.printMsg(exception.toString());
            }
        } else if (command.length() > 6 && command.startsWith("delete")) {
            int taskIndex = Integer.parseInt(command.substring(7));
            try {
                YapPal.delete(taskIndex);
            } catch (YapPalException exception) {
                Ui.printMsg(exception.toString());
            }
        } else {
            try {
                YapPal.addToList(command);
            } catch (YapPalException exception) {
                Ui.printMsg(exception.toString());
            }
        }
        return YapPal.State.LISTENING;
    }

    private static Task determineTask (String command) throws YapPalException{
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