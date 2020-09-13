package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDate date;
    protected String time;

    public Event(String description, LocalDate date, String time) {
        super(description);
        this.date = date;
        this.time = time;
    }

    @Override
    public String storageForm() {
        return "E" + ", "  + (isDone ? "1" : "0") + ", " + description + ", " + date.toString() + " " + time;
    }

    @Override
    public String getType() { return "Event"; }

    @Override
    public LocalDate getDate() { return this.date; }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
            + " " + time + ")";
    }
}