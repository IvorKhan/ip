public class Deadline extends Task {
    private String deadline;

    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String saveString() {
        return "type: D deadline: " + this.deadline + ' ' + super.saveString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by " + this.deadline + ")";
    }
}
