public abstract class Task {
    private String name;
    private boolean marked;

    public Task(String command, int offset) throws YapPalException{
        int nameEndIndex = command.indexOf('/');
        if (nameEndIndex == -1) {
            this.name = command.substring(offset);
        }
        else {
            if (nameEndIndex <= offset) {
                throw new YapPalException("No task name specified, please try again!");
            }
            this.name = command.substring(offset, nameEndIndex);
        }
        this.marked = command.contains("/mark");
    }

    public void mark() {
        this.marked = true;
    }

    public void unmark() {
        this.marked = false;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public String saveString() {
        if (this.isMarked()) {
            return this.name + " /mark";
        }
        return name;
    }

    @Override
    public String toString() {
        if (this.isMarked()) {
            return "[X] " + this.name;
        }
        return "[ ] " + this.name;
    }
}
