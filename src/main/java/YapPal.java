import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class YapPal {
    // chatbot constants
    public static final String BOT_NAME = "YapPal";
    public static final String INTRO_MSG =
        "Hello! I'm " + BOT_NAME + "\n" +
        "What can I do for you?";
    public static final String GOODBYE_MSG =
        "Hope to see you again soon!";
    public static Scanner scanner = new Scanner(System.in);

    // state constants
    private enum State {
        LISTENING,
        TERMINATE,
    }

    // operation constants
    private static final int MAX_LIST_LEN = 100;
    private static ArrayList<Task> tasks = new ArrayList<Task>(YapPal.MAX_LIST_LEN);

    public static void main(String[] args) {
        // initialisation
        YapPal.printMsg(YapPal.INTRO_MSG);
        State state = YapPal.State.LISTENING;
        try {
            YapPal.tasks = YapPal.load();
        } catch (YapPalException exception) {
            System.out.printf(exception.toString());
        }

        // listening loop
        while (state != YapPal.State.TERMINATE) {
            state = YapPal.listen();
        }

        // termination
        YapPal.printMsg(YapPal.GOODBYE_MSG);
    }

    private static State listen() {
        String command = YapPal.scanner.nextLine();
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
                YapPal.printMsg(exception.toString());
            }
        } else if (command.length() > 6 && command.startsWith("unmark")) {
            int taskIndex = Integer.parseInt(command.substring(7));
            try {
                YapPal.unmark(taskIndex);
            } catch (YapPalException exception) {
                YapPal.printMsg(exception.toString());
            }
        } else if (command.length() > 6 && command.startsWith("delete")) {
            int taskIndex = Integer.parseInt(command.substring(7));
            try {
                YapPal.delete(taskIndex);
            } catch (YapPalException exception) {
                YapPal.printMsg(exception.toString());
            }
        } else {
            try {
                YapPal.addToList(command);
            } catch (YapPalException exception) {
                YapPal.printMsg(exception.toString());
            }
        }
        return YapPal.State.LISTENING;
    }

    private static void list() {
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        YapPal.tasks.forEach(task -> {
            output.append(index.get()).append(". ").append(task).append("\n");
            index.incrementAndGet();
        });
        YapPal.printMsg(output.toString());
    }

    private static void addToList(String command) throws YapPalException{
        Task toAdd = determineTask(command);
        if (toAdd == null) {
            return;
        }
        YapPal.tasks.add(toAdd);
        YapPal.save();
        YapPal.printMsg("OK, I've added the following task: " + toAdd);
    }

    private static Task determineTask (String command) throws YapPalException{
        if (command.length() > 4 && command.startsWith("todo")) {
            return createToDo(command);
        } else if (command.length() > 8 && command.startsWith("deadline")) {
            return createDeadline(command);
        } else if (command.length() > 5 && command.startsWith("event")) {
            return createEvent(command);
        }
        // if none of the above:
        throw new YapPalException("Invalid task, please try again!");
    }

    private static ToDo createToDo(String command) {
        int TODO_NAME_OFFSET = 5;

        String taskName = command.substring(TODO_NAME_OFFSET);
        return new ToDo(taskName);
    }

    private static Deadline createDeadline(String command) throws YapPalException{
        int DEADLINE_NAME_OFFSET = 9;
        int DEADLINE_DEADLINE_OFFSET = 4;

        int deadlineIndex = command.indexOf("/by");
        if (deadlineIndex == -1) {
            throw new YapPalException("No /by variable specified, please try again!");
        }
        if (deadlineIndex <= DEADLINE_NAME_OFFSET) {
            throw new YapPalException("No task name specified, please try again!");
        }
        if (deadlineIndex + DEADLINE_DEADLINE_OFFSET >= command.length()) {
            throw new YapPalException("No deadline specified, please try again!");
        }
        String taskName = command.substring(DEADLINE_NAME_OFFSET, deadlineIndex - 1);
        String taskDeadline = command.substring(deadlineIndex + DEADLINE_DEADLINE_OFFSET);
        return new Deadline(taskName, taskDeadline);
    }

    private static Event createEvent(String command) throws YapPalException{
        int EVENT_NAME_OFFSET = 6;
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
        if (startIndex <= EVENT_NAME_OFFSET || endIndex <= EVENT_NAME_OFFSET) {
            throw new YapPalException("No task name specified, please try again!");
        }
        String taskName, taskStart, taskEnd;
        if (startIndex < endIndex) {
            if (endIndex <= startIndex + EVENT_START_OFFSET) {
                throw new YapPalException("No /from field specified, please try again!");
            }
            if (endIndex + EVENT_END_OFFSET >= command.length()) {
                throw new YapPalException("No /to specified, please try again!");
            }
            taskName = command.substring(EVENT_NAME_OFFSET, startIndex - 1);
            taskStart = command.substring(startIndex + EVENT_START_OFFSET, endIndex - 1);
            taskEnd = command.substring(endIndex + EVENT_END_OFFSET);
        } else {
            if (startIndex <= endIndex + EVENT_END_OFFSET) {
                throw new YapPalException("No /to field specified, please try again!");
            }
            if (startIndex + EVENT_START_OFFSET >= command.length()) {
                throw new YapPalException("No /from specified, please try again!");
            }
            taskName = command.substring(EVENT_NAME_OFFSET, endIndex - 1);
            taskEnd = command.substring(endIndex + EVENT_END_OFFSET, startIndex - 1);
            taskStart = command.substring(startIndex + EVENT_START_OFFSET);
        }
        return new Event(taskName, taskStart, taskEnd);
    }

    private static void mark(int ptr) throws YapPalException {
        if (ptr > YapPal.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = YapPal.tasks.get(ptr - 1);
        targetedTask.mark();
        YapPal.printMsg(
            "Nice! I've marked this task as done: \n" +
            targetedTask
        );
    }

    private static void unmark(int ptr) throws YapPalException {
        if (ptr > YapPal.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = YapPal.tasks.get(ptr - 1);
        targetedTask.unmark();
        YapPal.printMsg(
            "OK, I've marked this task as not done yet: \n" +
            targetedTask
        );
    }

    private static void delete(int ptr) throws YapPalException {
        if (ptr > YapPal.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = YapPal.tasks.get(ptr - 1);
        YapPal.tasks.remove(ptr - 1);
        YapPal.printMsg(
            "OK, I've removed this task: \n" +
            targetedTask
        );
    }

    private static void save() {
        try {
            FileWriter saveFileWriter = new FileWriter("..\\data\\save.txt");
            for (int i = 0; i < tasks.size(); ++i) {
                saveFileWriter.write(tasks.get(i).saveString());
            }
            saveFileWriter.close();
        } catch (IOException error) {
            YapPal.printMsg(error.toString());
        }
    }

    private static ArrayList<Task> load() throws YapPalException {
        File saveFile = new File("data\\save.txt");
        ArrayList<Task> tasks = new ArrayList<>(YapPal.MAX_LIST_LEN);
        try {
            Scanner saveReader = new Scanner(saveFile);
            while (saveReader.hasNextLine()) {
                String command = saveReader.nextLine();
                Task task = determineTask(command);
                tasks.add(task);
            }
            saveReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found - creating new save");
            try {
                saveFile.getParentFile().mkdir();
                saveFile.createNewFile();
            } catch (IOException fileCreateException) {
                System.out.println("An error occurred");
            }
        }
        return tasks;
    }

    private static void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
