package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;

/**
 * duke.TaskList Class stores the list of tasks and modifies the list according to commands.
 */
public class TaskList {

    public ArrayList<Task> list;

    /**
     * Default constructor that initialises an empty ArrayList
     */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Initialises an Arraylist of Tasks if there is saved data present.
     * @param savedData an ArrayList of tasks
     */
    public TaskList(ArrayList<Task> savedData) {
        this.list = savedData;
    }

    /**
     * Adds a new subclass of duke.task.Task based on a String input.
     * @param input Command from user
     * @return a duke.task.Task that is either a duke.task.ToDo, a duke.task.Deadline or an duke.task.Event.
     * @throws DukeException Throws exception if command is invalid due to missing information.
     */
    public Task addItem(String input) throws DukeException {
        assert input.length() >= 4;
        String arr[] = input.split(" ", 2);
        Task curr = new Task("");
        if (arr.length == 1) {
            throw DukeException.INVALID_TASK_EXCEPTION;
        } else if (arr[0].equals("todo")) {
            curr = new ToDo(arr[1]);
            list.add(curr);
        } else if (arr[0].equals("deadline")) {
            String info[] = arr[1].split("/by ", 2);
            if (info.length == 1) {
                throw DukeException.INVALID_DEADLINE_EXCEPTION;
            } else {
                curr = new Deadline(info[0], Parser.dateParser(info[1]));
                list.add(curr);
            }
        } else if (arr[0].equals("event")) {
            String info[] = arr[1].split("/at ", 2);
            if (info.length == 1) {
                throw DukeException.INVALID_TIME_EXCEPTION;
            } else {
                String[] t = info[1].split(" ", 2);
                try {
                    LocalDate date = Parser.dateParser(t[0]);
//                    String time = Parser.timeParser(t[1]);
                    curr = new Event(info[0], date, t[1]);
                    list.add(curr);
                } catch (DukeException ex1) {
                    throw DukeException.INVALID_TIME_EXCEPTION;
                } catch (NumberFormatException ex1) {
                    throw DukeException.INVALID_TIME_EXCEPTION;
                }
            }
        }
        return curr;
    }

    /**
     * Deletes an item based on the number
     * @param input Command from user
     * @return the duke.task.Task that was deleted
     * @throws DukeException Throws exception if command is invalid due to missing or wrong information.
     */
    public Task deleteItem(String input) throws DukeException{
        assert input.length() >= 6;
        String info[] = input.split(" ", 2);
        Task toBeDeleted = new Task("");
        if (info.length == 1) {
            throw DukeException.INVALID_INDEX_EXCEPTION;
        } else {
            try {
                int index = Integer.parseInt(info[1]);
                if (index > list.size() || index <= 0) {
                    throw DukeException.INVALID_INDEX_EXCEPTION;
                } else {
                    toBeDeleted = list.get(index - 1);
                    list.remove(index - 1);
                    return toBeDeleted;
                }
            } catch (NumberFormatException ex1){
                throw DukeException.INVALID_INDEX_EXCEPTION;
            }
        }
    }

    /**
     * Marks an item as done based on the number
     * @param input Command from user
     * @return the duke.task.Task that was marked as done after it has been marked.
     * @throws DukeException Throws exception if command is invalid due to missing or wrong information.
     */
    public Task doneItem(String input) throws DukeException {
        assert input.length() >= 4;
        String info[] = input.split(" ", 2);
        Task toBeRet = new Task("");
        if (info.length == 1) {
            throw DukeException.INVALID_INDEX_EXCEPTION;
        } else {
            int index = Integer.parseInt(info[1]);
            if (index > list.size() || index <= 0) {
                throw DukeException.INVALID_INDEX_EXCEPTION;
            } else {
                list.get(index-1).markAsDone();
                toBeRet = list.get(index-1);
            }
        }
        return toBeRet;
    }

    /**
     * Finds tasks that contains the string input as part of the task description
     * @param input word to be found in task descriptions
     * @return ArrayList of Tasks that contain the required word
     * @throws DukeException Throws an exception if query is invalid due to missing or wrong information.
     */
    public ArrayList<Task> find(String input) throws DukeException{
        assert input.length() >= 4;
        String[] info = input.split(" ", 2);
        if (info.length == 1) {
            throw DukeException.INVALID_QUERY_EXCEPTION;
        } else {
            ArrayList<Task> ret = new ArrayList<>();
            for (Task k : list) {
                if (k.getDescription().contains(info[1])) {
                    ret.add(k);
                }
            }
            return ret;
        }
    }

    public ArrayList<Task> sort(String input) throws DukeException {
        String[] filter = input.split("/", 2);
        if (filter[1].equals("type")) {
            return sortByType();
        } else if (filter[1].equals("date")){
            return sortByDate();
        } else {
            throw DukeException.INVALID_SORT_EXCEPTION;
        }
    }

    public ArrayList<Task> sortByType() {
        ArrayList<Task> todos = new ArrayList<>();
        ArrayList<Task> deadlines = new ArrayList<>();
        ArrayList<Task> events = new ArrayList<>();

        for (Task k: list) {
            if (k.getType().equals("Todo")) {
                todos.add(k);
            } else if (k.getType().equals("Event")) {
                events.add(k);
            } else if (k.getType().equals("Deadline")) {
                deadlines.add(k);
            }
        }
        todos.addAll(deadlines);
        todos.addAll(events);
        return todos;
    }

    public ArrayList<Task> sortByDate() {
        ArrayList<Task> ret = new ArrayList<>(list);
        Collections.sort(ret);
        return ret;
    }

    /**
     * Returns the number of tasks on the list.
     * @return integer representing the number of tasks on the list
     */
    public int getListSize() {
        return this.list.size();
    }

    /**
     * Returns the current list of Tasks.
     * @return an ArrayList of Tasks
     */
    public ArrayList<Task> getList() {
        return list;
    }

}
