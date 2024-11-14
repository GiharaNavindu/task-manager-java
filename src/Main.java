//import model.Task;
//import model.TaskPriority;
//import model.TaskStatus;
//import service.TaskService;
//import exception.InvalidTaskException;
//
//import java.time.LocalDate;
//
//public class Main {
//    public static void main(String[] args) {
//        // Initialize TaskService
//        TaskService taskService = new TaskService();
//
//        // Add some tasks
//        Task task1 = new Task(1, "Complete project", "Finish the project by deadline", LocalDate.now().plusDays(2), TaskStatus.TODO, TaskPriority.HIGH);
//        Task task2 = new Task(2, "Buy groceries", "Get vegetables and fruits", LocalDate.now().minusDays(1), TaskStatus.TODO, TaskPriority.MEDIUM);
//        Task task3 = new Task(3, "Prepare presentation", "Prepare slides for team meeting", LocalDate.now().plusDays(1), TaskStatus.IN_PROGRESS, TaskPriority.HIGH);
//        Task task4 = new Task(4, "Pay bills", "Pay monthly utility bills", LocalDate.now().minusDays(3), TaskStatus.TODO, TaskPriority.LOW);
//
//        taskService.addTask(task1);
//        taskService.addTask(task2);
//        taskService.addTask(task3);
//        taskService.addTask(task4);
//
//        // Display all tasks
//        System.out.println("All Tasks:");
//        taskService.getTasks().forEach(System.out::println);
//
//        // Update a task's status
//        try {
//            taskService.updateTaskStatus(1, TaskStatus.IN_PROGRESS);
//        } catch (InvalidTaskException e) {
//            System.out.println(e.getMessage());
//        }
//
//        // Filter tasks by status
//        System.out.println("\nTasks in progress:");
//        taskService.filterTasksByStatus(TaskStatus.IN_PROGRESS).forEach(System.out::println);
//
//        // Get important tasks (annotated with @ImportantTask)
//        System.out.println("\nImportant Tasks:");
//        taskService.getImportantTasks().forEach(System.out::println);
//
//        // Get overdue tasks (annotated with @OverdueTask)
//        System.out.println("\nOverdue Tasks:");
//        taskService.getOverdueTasks().forEach(System.out::println);
//
//        // Delete a task
//        try {
//            taskService.deleteTask(2);
//            System.out.println("\nTask with ID 2 deleted.");
//        } catch (InvalidTaskException e) {
//            System.out.println(e.getMessage());
//        }
//
//        // Display all tasks after deletion
//        System.out.println("\nAll Tasks after deletion:");
//        taskService.getTasks().forEach(System.out::println);
//    }
//}




import model.Task;
import model.TaskPriority;
import model.TaskStatus;
import service.TaskService;
import exception.InvalidTaskException;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final TaskService taskService = new TaskService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1 -> addTask();
                case 2 -> deleteTask();
                case 3 -> updateTaskStatus();
                case 4 -> listAllTasks();
                case 5 -> filterTasksByStatus();
                case 6 -> listImportantTasks();
                case 7 -> listOverdueTasks();
                case 8 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Exiting application. Goodbye!");
    }

    private static void displayMenu() {
        System.out.println("\n=== Task Management System ===");
        System.out.println("1. Add Task");
        System.out.println("2. Delete Task");
        System.out.println("3. Update Task Status");
        System.out.println("4. List All Tasks");
        System.out.println("5. Filter Tasks by Status");
        System.out.println("6. List Important Tasks");
        System.out.println("7. List Overdue Tasks");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addTask() {
        System.out.print("Enter Task ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Task Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Task Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Due Date (YYYY-MM-DD): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());
        TaskStatus status = TaskStatus.TODO;
        TaskPriority priority = choosePriority();

        Task task = new Task(id, title, description, dueDate, status, priority);
        taskService.addTask(task);
        System.out.println("Task added successfully!");
    }

    private static void deleteTask() {
        System.out.print("Enter Task ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        try {
            taskService.deleteTask(id);
            System.out.println("Task deleted successfully!");
        } catch (InvalidTaskException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateTaskStatus() {
        System.out.print("Enter Task ID to update status: ");
        int id = Integer.parseInt(scanner.nextLine());
        TaskStatus newStatus = chooseStatus();
        try {
            taskService.updateTaskStatus(id, newStatus);
            System.out.println("Task status updated successfully!");
        } catch (InvalidTaskException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllTasks() {
        System.out.println("\n=== All Tasks ===");
        taskService.getTasks().forEach(System.out::println);
    }

    private static void filterTasksByStatus() {
        TaskStatus status = chooseStatus();
        System.out.println("\n=== Tasks with Status: " + status + " ===");
        taskService.filterTasksByStatus(status).forEach(System.out::println);
    }

    private static void listImportantTasks() {
        System.out.println("\n=== Important Tasks ===");
        taskService.getImportantTasks().forEach(System.out::println);
    }

    private static void listOverdueTasks() {
        System.out.println("\n=== Overdue Tasks ===");
        taskService.getOverdueTasks().forEach(System.out::println);
    }

    private static TaskPriority choosePriority() {
        System.out.println("Choose Task Priority: ");
        for (TaskPriority priority : TaskPriority.values()) {
            System.out.println(priority.ordinal() + 1 + ". " + priority);
        }
        System.out.print("Enter choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        return TaskPriority.values()[choice - 1];
    }

    private static TaskStatus chooseStatus() {
        System.out.println("Choose Task Status: ");
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status);
        }
        System.out.print("Enter choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        return TaskStatus.values()[choice - 1];
    }
}

