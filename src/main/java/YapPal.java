import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class YapPal {
    public static final String SAVE_DIRECTORY = "data\\save.txt";
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
        Ui.printIntro();
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
        Ui.printGoodbye();
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

    private static void list() {
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        YapPal.tasks.forEach(task -> {
            output.append(index.get()).append(". ").append(task).append("\n");
            index.incrementAndGet();
        });
        Ui.printMsg(output.toString());
    }

    private static void addToList(String command) throws YapPalException{
        Task toAdd = determineTask(command);
        if (toAdd == null) {
            return;
        }
        YapPal.tasks.add(toAdd);
        YapPal.save();
        Ui.printMsg("OK, I've added the following task: " + toAdd);
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

    private static void mark(int ptr) throws YapPalException {
        if (ptr > YapPal.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = YapPal.tasks.get(ptr - 1);
        targetedTask.mark();
        YapPal.save();
        Ui.printMsg(
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
        YapPal.save();
        Ui.printMsg(
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
        Ui.printMsg(
            "OK, I've removed this task: \n" +
            targetedTask
        );
    }

    private static void save() {
        try {
            FileWriter saveFileWriter = new FileWriter(YapPal.SAVE_DIRECTORY);
            for (int i = 0; i < tasks.size(); ++i) {
                saveFileWriter.write(tasks.get(i).saveString() + "\n");
            }
            saveFileWriter.close();
        } catch (IOException error) {
            Ui.printMsg(error.toString());
        }
    }

    private static ArrayList<Task> load() throws YapPalException {
        File saveFile = new File(YapPal.SAVE_DIRECTORY);
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
}
