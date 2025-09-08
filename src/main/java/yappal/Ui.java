package yappal;

/**
 * Creates an Ui object for managing outputs
 */
class Ui {
    public final String botName;
    public final String introMsg;
    public final static String GOODBYE_MSG =
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
     * @param message Message to be printed
     */
    public void printMsg(String message) {
        assert message != null : "Null input received, input should not be null!";
        System.out.println(
            "____________________________________________________________ \n"
            + message + " \n"
            + "____________________________________________________________ \n"
        );
    }
}
