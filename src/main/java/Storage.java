import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    public static final String SAVE_DIRECTORY = "data\\save.txt";

    private static void save() {
        try {
            FileWriter saveFileWriter = new FileWriter(YapPal.SAVE_DIRECTORY);
            for (int i = 0; i < tasks.size(); ++i) {
                saveFileWriter.write(tasks.get(i).saveString() + "\n");
            }
            saveFileWriter.close();
        } catch (IOException error) {
            Ui.printMsg(error.toString());
        }
    }

    private static ArrayList<Task> load() throws YapPalException {
        File saveFile = new File(YapPal.SAVE_DIRECTORY);
        ArrayList<Task> tasks = new ArrayList<>(Storage.MAX_LIST_LEN);
        try {
            Scanner saveReader = new Scanner(saveFile);
            while (saveReader.hasNextLine()) {
                String command = saveReader.nextLine();
                Task task = determineTask(command);
                tasks.add(task);
            }
            saveReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Save file not found - creating new save");
            try {
                saveFile.getParentFile().mkdir();
                saveFile.createNewFile();
            } catch (IOException fileCreateException) {
                System.out.println("An error occurred");
            }
        }
        return tasks;
    }
}
