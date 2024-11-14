package service;

import annotation.OverDueTask;
import model.Task;
import model.TaskStatus;
import exception.InvalidTaskException;
import annotation.ImportantTask;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskService {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int id) throws InvalidTaskException {
        Optional<Task> task = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (task.isPresent()) {
            tasks.remove(task.get());
        } else {
            throw new InvalidTaskException("Task with ID " + id + " not found.");
        }
    }

    public Task findTaskById(int id) throws InvalidTaskException {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidTaskException("Task with ID " + id + " not found."));
    }

    public void updateTaskStatus(int id, TaskStatus newStatus) throws InvalidTaskException {
        Task task = findTaskById(id);
        task.setStatus(newStatus);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> filterTasksByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> getImportantTasks() {
        return tasks.stream()
                .filter(this::isAnnotatedWithImportant)
                .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasks() {
        return tasks.stream()
                .filter(this::isAnnotatedWithOverdue)
                .collect(Collectors.toList());
    }

    private boolean isAnnotatedWithImportant(Task task) {
        try {
            return task.getClass().getMethod("isImportant").isAnnotationPresent(ImportantTask.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAnnotatedWithOverdue(Task task) {
        try {
            return task.getClass().getMethod("isOverdue").isAnnotationPresent(OverDueTask.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }
}
