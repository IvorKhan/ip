import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class YapPal {

    // state constants
    public enum State {
        LISTENING,
        TERMINATE,
    }

    public static void main(String[] args) {
        // initialisation
        Ui.printIntro();
        State state = YapPal.State.LISTENING;
        try {
            YapPal.tasks = YapPal.load();
        } catch (YapPalException exception) {
            System.out.printf(exception.toString());
        }

        // listening loop
        while (state != YapPal.State.TERMINATE) {
            state = YapPal.listen();
        }

        // termination
        Ui.printGoodbye();
    }
}
