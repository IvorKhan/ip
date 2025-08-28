public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String saveString() {
        return "type: T " + super.saveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
