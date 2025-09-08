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

    /**
     * Instantiates a TaskList object for managing the task list
     *
     * @param tasks An array of tasks to set as the initial task list
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "TaskList input should not be null!";
        this.tasks = tasks;
    }

    /**
     * Prints a formatted list of all tasks in the task list
     *
     * @return The list of tasks
     */
    public String list() {
        StringBuilder output = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        this.tasks.forEach(task -> {
            output.append(index.get()).append(". ").append(task).append("\n");
            index.incrementAndGet();
        });
        return output.toString();
    }

    /**
     * Adds a task to the task list
     *
     * @param toAdd The task to be added
     * @return YapPal's response
     */
    public String addToList(Task toAdd) {
        if (toAdd == null) {
            return "No task specified!";
        }
        this.tasks.add(toAdd);
        return "OK, I've added the following task: " + toAdd;
    }

    /**
     * Marks a task in the task list
     *
     * @param ptr Index of the task to be marked
     * @return YapPal's response
     * @throws YapPalException If index is out of list range
     */
    public String mark(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        targetedTask.mark();
        return "Nice! I've marked this task as done: \n"
                + targetedTask;
    }

    /**
     * Unmarks a task in the task list
     *
     * @param command The command input by the user
     * @return The found list
     * @throws YapPalException If index is out of list range
     */
    public String find(String command) throws YapPalException {
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
        return output.toString();
    }

    /**
     * Unmarks a task in the task list
     *
     * @param ptr Index of the task to be unmarked
     * @return YapPal's response
     * @throws YapPalException If index is out of list range
     */
    public String unmark(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        targetedTask.unmark();
        return "OK, I've marked this task as not done yet: \n"
                + targetedTask;
    }

    /**
     * Deletes a task in the task list
     *
     * @param ptr Index of the task to be deleted
     * @return YapPal's response
     * @throws YapPalException If index is out of list range
     */
    public String delete(int ptr) throws YapPalException {
        if (ptr > this.tasks.size() || ptr < 1) {
            throw new YapPalException("Task not in list, please try again!");
        }
        Task targetedTask = this.tasks.get(ptr - 1);
        this.tasks.remove(ptr - 1);
        return "OK, I've removed this task: \n"
                + targetedTask;
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }
}
