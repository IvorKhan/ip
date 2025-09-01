public class Ui {
    // chatbot constants
    public static final String BOT_NAME = "YapPal";
    public static final String INTRO_MSG =
            "Hello! I'm " + BOT_NAME + "\n" +
                    "What can I do for you?";
    public static final String GOODBYE_MSG =
            "Hope to see you again soon!";

    public static void printIntro() {
        Ui.printMsg(Ui.INTRO_MSG);
    }

    public static void printGoodbye() {
        Ui.printMsg(Ui.GOODBYE_MSG);
    }

    public static void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
