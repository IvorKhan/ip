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
    public enum State {
        LISTENING,
        TERMINATE,
    }

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
        YapPal.save();
        Ui.printMsg(
            "OK, I've removed this task: \n" +
            targetedTask
        );
    }
}
