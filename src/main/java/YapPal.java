import java.util.ArrayList;

public class YapPal {
    // operation objects
    private TaskList taskList;
    private Parser parser;
    private Storage storage;
    private Ui ui;

    // state constants
    public enum State {
        TERMINATE,
        INIT,
        LIST,
        MARK,
        UNMARK,
        DELETE,
        ADD,
    }

    public YapPal() {
        this.parser = new Parser();
        this.ui = new Ui("YapPal");
        this.storage = new Storage("data\\save.txt", ui, parser);
        ArrayList<Task> tasks;
        try {
            tasks = this.storage.load();
        } catch (YapPalException exception) {
            tasks = new ArrayList<>();
        }
        this.taskList = new TaskList(tasks, this.ui);
    }

    public void run() {
        // startup
        this.ui.printIntro();
        State state = YapPal.State.INIT;
        try {
            this.storage.load();
        } catch (YapPalException exception) {
            this.ui.printMsg(exception.toString());
        }

        // listening loop
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
                    case DELETE:
                        index = this.parser.getLastInd(state);
                        this.taskList.delete(index);
                        break;
                    case ADD:
                        task = this.parser.determineTask(command);
                        this.taskList.addToList(task);
                }
                if (state != YapPal.State.LIST) {
                    this.storage.save(this.taskList.getTaskList());
                }
            } catch (YapPalException exception) {
                this.ui.printMsg(exception.toString());
            }
        }

        // termination
        this.ui.printGoodbye();
    }

    public static void main(String[] args) {
        // initialisation
        new YapPal().run();
    }
}
