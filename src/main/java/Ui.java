public class Ui {
    // chatbot constants
    public final String BOT_NAME;
    public final String INTRO_MSG;
    public static final String GOODBYE_MSG =
        "Hope to see you again soon!";

    public Ui(String name) {
        this.BOT_NAME = name;
        this.INTRO_MSG =
            "Hello! I'm " + BOT_NAME + "\n" +
            "What can I do for you?";
    }

    public void printIntro() {
        this.printMsg(this.INTRO_MSG);
    }

    public void printGoodbye() {
        this.printMsg(this.GOODBYE_MSG);
    }

    public void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
