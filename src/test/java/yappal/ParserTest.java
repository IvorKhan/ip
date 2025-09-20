package yappal;

import yappal.task.Deadline;
import yappal.task.Event;
import yappal.task.ToDo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    private static Parser parser;

    @BeforeAll
    public static void beforeAll() {
        parser = new Parser();
    }

    @Test
    public void listen_terminate_success() {
        assertEquals(YapPal.State.TERMINATE, parser.listen("bye"));
    }

    @Test
    public void listen_list_success() {
        assertEquals(parser.listen("list"), YapPal.State.LIST);
    }

    @Test
    public void listen_mark_success() {
        assertEquals(parser.listen("mark 1"), YapPal.State.MARK);
    }

    @Test
    public void listen_unmark_success() {
        assertEquals(parser.listen("unmark 1"), YapPal.State.UNMARK);
    }

    @Test
    public void listen_delete_success() {
        assertEquals(parser.listen("delete 1"), YapPal.State.DELETE);
    }

    @Test
    public void listen_add_success() {
        assertEquals(parser.listen("todo todo"), YapPal.State.ADD);
    }

    @Test
    public void listen_notCommand_passToAdd() {
        assertEquals(parser.listen("dfjsakflksja"), YapPal.State.ADD);
    }

    @Test
    public void determineTask_toDo_success() {
        try {
            ToDo correctToDo = new ToDo("todo todo");
            assertEquals(parser.determineTask("todo todo").toString(), correctToDo.toString());
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void determineTask_deadline_success() {
        try {
            Deadline correctDeadline = new Deadline("deadline deadline /by 1111-11-11");
            assertEquals(parser.determineTask("deadline deadline /by 1111-11-11").toString(), correctDeadline.toString());
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void determineTask_event_success() {
        try {
            Event correctEvent = new Event("event event /from 1111-11-11 /to 1111-11-11");
            assertEquals(parser.determineTask("event event /from 1111-11-11 /to 1111-11-11").toString(), correctEvent.toString());
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void determineTask_invalidInput_exception() {
        try {
            parser.determineTask("not a command");
            fail();
        } catch (YapPalException e) {
            assertEquals(e.getMessage(), "Invalid task, please try again!");
        }
    }
}
