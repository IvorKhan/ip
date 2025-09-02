package yappal;

import yappal.task.Deadline;
import yappal.task.Event;
import yappal.task.Task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yappal.task.ToDo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    Parser parser;

    @BeforeEach
    void setUp() {
        this.parser = new Parser();
    }

    @Test
    public void listen_terminate_success() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("bye".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.TERMINATE);

        System.setIn(systemIn);
    }

    @Test
    public void listen_list_success() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("list".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.LIST);

        System.setIn(systemIn);
    }

    @Test
    public void listen_mark_success() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("mark 1".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.MARK);

        System.setIn(systemIn);
    }

    @Test
    public void listen_unmark_success() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("unmark 1".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.UNMARK);

        System.setIn(systemIn);
    }

    @Test
    public void listen_delete_success() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("delete 1".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.DELETE);

        System.setIn(systemIn);
    }

    @Test
    public void listen_add_success() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("todo todo".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.ADD);

        System.setIn(systemIn);
    }

    @Test
    public void listen_notCommand_passToAdd() {
        InputStream systemIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("dfjsakflksja".getBytes());
        System.setIn(testIn);

        assertEquals(this.parser.listen(), YapPal.State.ADD);

        System.setIn(systemIn);
    }

    @Test
    public void determineTask_toDo_success() {
        try {
            ToDo correctToDo = new ToDo("todo todo");
            assertEquals(this.parser.determineTask("todo todo"), correctToDo);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void determineTask_deadline_success() {
        try {
            Deadline correctToDo = new Deadline("deadline deadline /by 1111-11-11");
            assertEquals(this.parser.determineTask("deadline deadline /by 1111-11-11"), correctToDo);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void determineTask_event_success() {
        try {
            Event correctToDo = new Event("event event /from 1111-11-11 /to 1111-11-11");
            assertEquals(this.parser.determineTask("event event /from 1111-11-11 /to 1111-11-11"), correctToDo);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void determineTask_invalidInput_exception() {
        try {
            this.parser.determineTask("not a command");
            fail();
        } catch (YapPalException e) {
            assertEquals(e.getMessage(), "Invalid task, please try again!");
        }
    }
}
