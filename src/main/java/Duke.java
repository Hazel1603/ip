import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    public static String line = "-------------------------------------";
    public static String defaultError = "Wat talking you?";
    public static String addedMsg = "Alright, I've added a new order: ";
    public static ArrayList<Task> list = new ArrayList<>();

    public static Task addItem(String input) throws DukeException {
        String arr[] = input.split(" ", 2);
        Task curr = new Task("");
        if (arr.length == 1) {
            throw new DukeException("The description of a " + arr[0] + " cannot be empty!");
        } else if (arr[0].equals("todo")) {
            curr = new ToDo(arr[1]);
            list.add(curr);
        } else if (arr[0].equals("deadline")) {
            String info[] = arr[1].split("/by", 2);
            if (info.length == 1) {
                throw new DukeException("Deadline not provided!");
            } else {
                curr = new Deadline(info[0], info[1]);
                list.add(curr);
            }
        } else if (arr[0].equals("event")) {
            String info[] = arr[1].split("/at", 2);
            if (info.length == 1) {
                throw new DukeException("Time not provided!");
            } else {
                curr = new Event(info[0], info[1]);
                list.add(curr);
            }
        }
        return curr;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nextLine = "";
        // Introduction
        System.out.println(line);
        System.out.println("Welcome to mel's drive-in!");
        System.out.println("What would you like to have?");
        System.out.println(line);

        nextLine = scanner.nextLine();

        while (!nextLine.equals("bye")) {
            if (nextLine.contains("todo") || nextLine.contains("deadline") || nextLine.contains("event")) {
                try {
                    Task curr = addItem(nextLine);

                    System.out.println(line);
                    System.out.println(addedMsg);
                    System.out.println(curr);
                    System.out.println("You have ordered " + list.size() + " items.");
                    System.out.println(line);
                } catch (DukeException ex1) {
                    System.out.println(line);
                    System.out.println(ex1);
                    System.out.println(line);
                }
                nextLine = scanner.nextLine();
            } else if (nextLine.contains("done")) { // Case 4: checking off an item
                String arr[] = nextLine.split(" ", 2);
                int index = Integer.parseInt(arr[1]);

                if (index > list.size()) {
                    System.out.println(defaultError);
                } else {
                    list.get(index - 1).markAsDone();

                    System.out.println(line);
                    System.out.println("Great choice! I have taken your order: ");
                    System.out.println(list.get(index - 1));
                    System.out.println(line);
                }
                nextLine = scanner.nextLine();
            } else if (nextLine.equals("list")) { // Case 5: Displaying List
                System.out.println(line);
                System.out.println("Here's what you have ordered so far...");
                for (int k = 0; k < list.size(); k++) {
                    System.out.println((k + 1) + ": " + list.get(k));
                }
                System.out.println((line));
                nextLine = scanner.nextLine();
            } else { // Case 6: Default errors
                System.out.println(line);
                System.out.println(defaultError);
                System.out.println(line);
                nextLine = scanner.nextLine();
            }
        }


        // Ending the bot
        System.out.println(line);
        System.out.println("Bye! Please come again!");
        System.out.println(line);
    }
}

