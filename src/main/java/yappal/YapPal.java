package yappal;

import java.util.ArrayList;

import yappal.task.Task;

/**
 * The YapPal Class.
 * Runs the YapPal app and centrally manages all functions.
 */
public class YapPal {
    private TaskList taskList; // stores tasks
    private Parser parser; // parses user inputs
    private Storage storage; // manages save and load functions
    private Ui ui; // prints data for the user
    private State state;

    // State enum tells YapPal what to do next
    public enum State {
        TERMINATE,
        LIST,
        MARK,
        UNMARK,
        DELETE,
        ADD,
        FIND,
    }

    /**
     * Instantiates a YapPal instance.
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
        this.taskList = new TaskList(tasks);
        assert tasks != null : "TaskList is not instantiated";
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String command) {
        String response;
        Task task;
        int index;
        state = this.parser.listen(command);
        assert state != null : "Listen did not correctly return a state";
        try {
            switch (state) {
            case LIST:
                response = this.taskList.list();
                break;
            case MARK:
                index = this.parser.getLastInd(state);
                response = this.taskList.mark(index);
                break;
            case UNMARK:
                index = this.parser.getLastInd(state);
                response = this.taskList.unmark(index);
                break;
            case FIND:
                response = this.taskList.find(command);
                break;
            case DELETE:
                index = this.parser.getLastInd(state);
                response = this.taskList.delete(index);
                break;
            case ADD:
                task = this.parser.determineTask(command);
                response = this.taskList.addToList(task);
                break;
            default:
                throw new YapPalException("Unknown command, please try again");
            }
            // save after every action
            if (state != YapPal.State.LIST) {
                this.storage.save(this.taskList.getTaskList());
            }
        } catch (YapPalException exception) { // outputs errors with user inputs
            response = ("Error: " + exception.getMessage());
        }
        assert response != null : "No response returned";
        return response;
    }

    public State getState() {
        return state;
    }

    public String getIntroMsg() {
        return ui.getIntroMsg();
    }
}
