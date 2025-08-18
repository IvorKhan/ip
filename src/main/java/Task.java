public class Task {
    private String name;
    private boolean marked;

    public Task(String name) {
        this.name = name;
        this.marked = false;
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

    @Override
    public String toString() {
        if (this.isMarked()) {
            return "[X] " + this.name;
        }
        return "[ ] " + this.name;
    }
}
