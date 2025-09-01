import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskList {
    // operation constants
    public static final int MAX_LIST_LEN = 100;
    private ArrayList<Task> tasks;
    private Ui ui;

    public TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    public void list() {
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        this.tasks.forEach(task -> {
            output.append(index.get()).append(". ").append(task).append("\n");
            index.incrementAndGet();
        });
        this.ui.printMsg(output.toString());
    }

    public void addToList(Task toAdd) throws YapPalException{
        if (toAdd == null) {
            return;
        }
        this.tasks.add(toAdd);
        this.ui.printMsg("OK, I've added the following task: " + toAdd);
    }

    public void mark(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        targetedTask.mark();
        this.ui.printMsg(
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
        this.ui.printMsg(
            "OK, I've marked this task as not done yet: \n" +
            targetedTask
        );
    }

    public void delete(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        this.tasks.remove(ptr - 1);
        this.ui.printMsg(
            "OK, I've removed this task: \n" +
            targetedTask
        );
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }
}
