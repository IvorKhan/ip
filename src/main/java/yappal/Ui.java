package yappal;

/**
 * Creates an Ui object for managing outputs
 */
class Ui {
<<<<<<< HEAD
    public final String botName;
    public final String introMsg;
    public final static String GOODBYE_MSG =
=======
    private final String BOT_NAME;
    private final String INTRO_MSG;
    private static final String GOODBYE_MSG =
>>>>>>> branch-A-CheckStyle
        "Hope to see you again soon!";

    /**
     * Instantiates the Ui object for managing outputs
     * @param name Name of the bot
     */
    public Ui(String name) {
        this.botName = name;
        this.introMsg =
            "Hello! I'm " + botName + "\n"
            + "What can I do for you?";
    }

    public void printIntro() {
        this.printMsg(this.introMsg);
    }

    public void printGoodbye() {
        this.printMsg(Ui.GOODBYE_MSG);
    }

    /**
     * Prints a formatted message
     *
     * @param msg Message to be printed
     */
    public void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n"
            + msg + " \n"
            + "____________________________________________________________ \n"
        );
    }
}
