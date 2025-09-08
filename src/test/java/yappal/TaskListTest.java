package yappal;

import yappal.task.Deadline;
import yappal.task.Event;
import yappal.task.Task;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yappal.task.ToDo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    TaskList taskList;
    Ui ui;
    ArrayList<Task> tasks;

    @BeforeEach
    void setUp() {
        this.ui = new Ui("YapPal");
        this.tasks = new ArrayList<>();
        this.taskList = new TaskList(this.tasks);
    }

    @Test
    public void addToList_addToDo_success() {
        try {
            ToDo testToDo = new ToDo("todo todo");
            this.taskList.addToList(testToDo);
            ArrayList<Task> correctList = new ArrayList<>();
            correctList.add(testToDo);
            assertEquals(this.taskList.getTaskList(), correctList);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void addToList_addNull_noAction() {
        this.taskList.addToList(null);
        ArrayList<Task> correctList = new ArrayList<>();
        assertEquals(this.taskList.getTaskList(), correctList);
    }

    @Test
    public void addToList_addDeadline_success() {
        try {
            Deadline testDeadline = new Deadline("deadline deadline /by 1111-11-11");
            this.taskList.addToList(testDeadline);
            ArrayList<Task> correctList = new ArrayList<>();
            correctList.add(testDeadline);
            assertEquals(this.taskList.getTaskList(), correctList);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void addToList_addEvent_success() {
        try {
            Event testEvent = new Event("event event /from 1111-11-11 /to 1111-11-11");
            this.taskList.addToList(testEvent);
            ArrayList<Task> correctList = new ArrayList<>();
            correctList.add(testEvent);
            assertEquals(this.taskList.getTaskList(), correctList);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void markTask_markTaskInList_success() {
        try {
            ToDo toDo = new ToDo("todo todo");
            this.taskList.addToList(toDo);
            this.taskList.mark(1);
            assertEquals(toDo.isMarked(), true);
        } catch (YapPalException e) {
            fail();
        }
    }

    @Test
    public void markTask_markTaskOutOfList_exception() {
        try {
            this.taskList.mark(1);
            fail();
        } catch (YapPalException e) {
            assertEquals(e.getMessage(), "Task not in list, please try again!");
        }
    }
}
