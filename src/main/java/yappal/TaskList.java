package yappal;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import yappal.task.Task;

/**
 * Creates a TaskList object for managing tasks
 */
class TaskList {
    public final static int MAX_LIST_LEN = 100;
    private ArrayList<Task> tasks;
    private Ui ui;

    /**
     * Instantiates a TaskList object for managing the task list
     *
     * @param tasks An array of tasks to set as the initial task list
     * @param ui Ui object for outputting text
     */
    public TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Prints a formatted list of all tasks in the task list
     */
    public void list() {
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        this.tasks.forEach(task -> {
            output.append(index.get()).append(". ").append(task).append("\n");
            index.incrementAndGet();
        });
        this.ui.printMsg(output.toString());
    }

    /**
     * Adds a task to the task list
     *
     * @param toAdd The task to be added
     */
    public void addToList(Task toAdd) {
        if (toAdd == null) {
            return;
        }
        this.tasks.add(toAdd);
        this.ui.printMsg("OK, I've added the following task: " + toAdd);
    }

    /**
     * Marks a task in the task list
     *
     * @param ptr Index of the task to be marked
     * @throws YapPalException If index is out of list range
     */
    public void mark(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        targetedTask.mark();
        this.ui.printMsg(
            "Nice! I've marked this task as done: \n"
            + targetedTask
        );
    }

    public void find(String command) throws YapPalException {
        final int offset = 5;
        String keyword = command.substring(offset);
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        this.tasks.forEach(task -> {
            if (task.getName().contains(keyword)) {
                output.append(index.get()).append(". ").append(task).append("\n");
                index.incrementAndGet();
            }
        });
        this.ui.printMsg(output.toString());
    }

    /**
     * Unmarks a task in the task list
     *
     * @param ptr Index of the task to be unmarked
     * @throws YapPalException If index is out of list range
     */
    public void unmark(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        targetedTask.unmark();
        this.ui.printMsg(
            "OK, I've marked this task as not done yet: \n"
            + targetedTask
        );
    }

    /**
     * Deletes a task in the task list
     *
     * @param ptr Index of the task to be deleted
     * @throws YapPalException If index is out of list range
     */
    public void delete(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        this.tasks.remove(ptr - 1);
        this.ui.printMsg(
            "OK, I've removed this task: \n"
            + targetedTask
        );
    }
    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }
}
