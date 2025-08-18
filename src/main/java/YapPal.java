import java.util.Scanner;

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
    private static Task[] taskList = new Task[MAX_LIST_LEN];
    private static int taskListPtr = 0;

    public static void main(String[] args) {
        // initialisation
        YapPal.printMsg(YapPal.INTRO_MSG);
        State state = YapPal.State.LISTENING;

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
        } else {
            YapPal.addToList(command);
        }
        return YapPal.State.LISTENING;
    }

    private static void list() {
        String output = "";
        for (int i = 0; i < YapPal.taskListPtr; ++i) {
            output += (i+1) + ". " + YapPal.taskList[i] + "\n";
        }
        YapPal.printMsg(output);
    }

    private static void addToList(String item) {
        Task toAdd = new Task(item);
        YapPal.taskList[YapPal.taskListPtr] = toAdd;
        ++YapPal.taskListPtr;
        YapPal.printMsg("added: " + toAdd);
    }

    private static void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
