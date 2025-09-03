package yappal;

import yappal.task.Task;
import java.util.ArrayList;

/**
 * YapPal Class
 * Runs the YapPal app and centrally manages all functions
 */
class YapPal {
    private TaskList taskList; // stores tasks
    private Parser parser; // parses user inputs
    private Storage storage; // manages save and load functions
    private Ui ui; // prints data for the user

    // State enum tells YapPal what to do next
    public enum State {
        TERMINATE,
        INIT,
        LIST,
        MARK,
        UNMARK,
        DELETE,
        ADD,
        FIND,
    }

    /**
     * Instantiates a YapPal instance
     */
    public YapPal() {
        this.parser = new Parser();
        this.ui = new Ui("YapPal");
        this.storage = new Storage("data\\save.txt", ui, parser);
        // instantiate task list for TaskList object
        ArrayList<Task> tasks;
        // attempts to load save file, creates empty ArrayList if not found
        try {
            tasks = this.storage.load();
        } catch (YapPalException exception) {
            tasks = new ArrayList<>();
        }
        this.taskList = new TaskList(tasks, this.ui);
    }

    /**
     * Runs the YapPal instance
     *
     * Input options:
     * bye - Terminates instance
     * list - Prints all tasks in the task list
     * mark [int] - Marks the task at index [int]
     * unmark [int] - Unmarks the task at index [int]
     * delete [int] - Deletes the task at index [int]
     * todo [name] - Appends a todo object to the task list with name [name]
     * deadline [name] /by [date] - Appends a deadline object to the task list with name [name]
     *                              and deadline [date] (date must be input in YYYY-MM-DD)
     * event [event] /from [date1] /to [date2] - Appends an event object to the task list with name [name]
     *                              from [date1] to [date2] (dates must be input in YYYY-MM-DD)
     */
    public void run() {
        // initialises State variable and prints intro
        this.ui.printIntro();
        State state = YapPal.State.INIT;

        // listens for user input, then performs action accordingly
        String command;
        Task task;
        int index;

        while (state != YapPal.State.TERMINATE) {
            state = this.parser.listen();
            command = this.parser.getLastCommand();
            try {
                switch (state) {
                    case LIST:
                        this.taskList.list();
                        break;
                    case MARK:
                        index = this.parser.getLastInd(state);
                        this.taskList.mark(index);
                        break;
                    case UNMARK:
                        index = this.parser.getLastInd(state);
                        this.taskList.unmark(index);
                        break;
                    case FIND:
                        this.taskList.find(command);
                        break;
                    case DELETE:
                        index = this.parser.getLastInd(state);
                        this.taskList.delete(index);
                        break;
                    case ADD:
                        task = this.parser.determineTask(command);
                        this.taskList.addToList(task);
                }
                // save after every action
                if (state != YapPal.State.LIST) {
                    this.storage.save(this.taskList.getTaskList());
                }
            } catch (YapPalException exception) { // outputs errors with user inputs
                this.ui.printMsg(exception.toString());
            }
        }

        // terminates session
        this.ui.printGoodbye();
    }

    public static void main(String[] args) {
        new YapPal().run();
    }
}
