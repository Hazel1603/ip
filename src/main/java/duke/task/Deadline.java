package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String storageForm() {
        return "D" + ", "  + (isDone ? "1" : "0") + ", " + description + ", " + by.toString();
    }

    @Override
    public String getType() { return "Deadline"; }

    @Override
    public LocalDate getDate() { return this.by; }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}