package yappal;

/**
 * Creates an Ui object for managing outputs.
 */
class Ui {
    public final String botName;
    public final String introMsg;

    /**
     * Instantiates the Ui object for managing outputs.
     * @param name The name of the bot.
     */
    public Ui(String name) {
        this.botName = name;
        this.introMsg =
            "Hello! I'm " + botName + "\n"
            + "What can I do for you?";
    }

    public String getIntroMsg() {
        return this.introMsg;
    }

    /**
     * Prints a formatted message.
     *
     * @param message Message to be printed.
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
