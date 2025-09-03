package yappal;

import yappal.task.Task;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Storage object that handles save and load interactions with the save file
 */
class Storage {
    private final String SAVE_DIRECTORY;
    private Ui ui;
    private Parser parser;

    /**
     * Instantiates a Storage object for saving and loading
     *
     * @param savePath Path to save file
     * @param ui Reference to ui object for printing messages
     * @param parser Reference to parser object for parsing save file
     */
    public Storage(String savePath, Ui ui, Parser parser) {
        this.SAVE_DIRECTORY = savePath;
        this.ui = ui;
        this.parser = parser;
    }

    /**
     * Saves tasks to the save file
     * Uses the save file path defined during initialisation
     *
     * @param tasks Path to save file
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter saveFileWriter = new FileWriter(this.SAVE_DIRECTORY);
            for (int i = 0; i < tasks.size(); ++i) {
                saveFileWriter.write(tasks.get(i).saveString() + "\n");
            }
            saveFileWriter.close();
        } catch (IOException error) {
            this.ui.printMsg(error.toString());
        }
    }

    /**
     * Load tasks from the save file
     * Uses the save file path defined during initialisation
     * Creates save file if file not found
     *
     * @return Array of tasks read from save file
     * @throws YapPalException If save file is corrupted
     */
    public ArrayList<Task> load() throws YapPalException {
        File saveFile = new File(this.SAVE_DIRECTORY);
        ArrayList<Task> tasks = new ArrayList<>(TaskList.MAX_LIST_LEN);
        try {
            Scanner saveReader = new Scanner(saveFile);
            while (saveReader.hasNextLine()) {
                String command = saveReader.nextLine();
                Task task = parser.determineTask(command);
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
