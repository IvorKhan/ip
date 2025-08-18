public class Task {
    private String name;
    private boolean mark;

    public Task(String name) {
        this.name = name;
        this.mark = false;
    }

    public void mark() {
        this.mark = true;
    }

    public void unmark() {
        this.mark = false;
    }

    public boolean isMarked() {
        return this.mark;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
