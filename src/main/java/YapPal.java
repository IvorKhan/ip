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
    private static String[] itemList = new String[100];
    private static int itemListPtr = 0;

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
        for (int i = 0; i < YapPal.itemListPtr; ++i) {
            output += (i+1) + ". " + YapPal.itemList[i] + "\n";
        }
        YapPal.printMsg(output);
    }

    private static void addToList(String item) {
        YapPal.itemList[YapPal.itemListPtr] = item;
        ++YapPal.itemListPtr;
        YapPal.printMsg("added: " + item);
    }

    private static void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
