public class YapPal {
    // public state
    public static final String botName = "YapPal";
    public static final String introMsg =
        "Hello! I'm " + botName + "\n" +
        "What can I do for you?";
    public static final String goodbyeMsg =
        "Hope to see you again soon!";

    public static void main(String[] args) {
        YapPal.printMsg(introMsg);
        YapPal.printMsg(goodbyeMsg);
    }

    public static void printMsg(String msg) {
        System.out.println(
            "____________________________________________________________ \n" +
            msg + " \n" +
            "____________________________________________________________ \n"
        );
    }
}
