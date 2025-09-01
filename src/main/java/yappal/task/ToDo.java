package yappal.task;

import yappal.YapPalException;

public class ToDo extends Task {
    public ToDo(String command) throws YapPalException {
        super(command, 5);
    }

    @Override
    public String saveString() {
        return "todo " + super.saveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
