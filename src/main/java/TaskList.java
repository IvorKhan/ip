import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskList {
    // operation constants
    private static final int MAX_LIST_LEN = 100;
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>(TaskList.MAX_LIST_LEN);
    }

    private void list() {
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        this.tasks.forEach(task -> {
            output.append(index.get()).append(". ").append(task).append("\n");
            index.incrementAndGet();
        });
        Ui.printMsg(output.toString());
    }

    private void addToList(String command) throws YapPalException{
        Task toAdd = determineTask(command);
        if (toAdd == null) {
            return;
        }
        this.tasks.add(toAdd);
        YapPal.save();
        Ui.printMsg("OK, I've added the following task: " + toAdd);
    }

    public void mark(int ptr) throws YapPalException {
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

    public void unmark(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        targetedTask.unmark();
        YapPal.save();
        Ui.printMsg(
                "OK, I've marked this task as not done yet: \n" +
                        targetedTask
        );
    }

    public static void delete(int ptr) throws YapPalException {
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
