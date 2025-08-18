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
    private static final int TERMINATE = -1;
    private static final int LISTENING = 0;

    public static void main(String[] args) {
        YapPal.printMsg(YapPal.INTRO_MSG);
        int state = YapPal.LISTENING;
        while (state != YapPal.TERMINATE) {
            state = YapPal.listen();
        }
        YapPal.printMsg(YapPal.GOODBYE_MSG);
    }

    public static int listen() {
        String command = YapPal.scanner.nextLine();
        if (command.equals("bye")) {
            return YapPal.TERMINATE;
        }
        YapPal.printMsg(command);
        return YapPal.LISTENING;
    }

    public static void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
