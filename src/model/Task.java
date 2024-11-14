package model;

import annotation.ImportantTask;
import annotation.OverDueTask;

import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;

    public Task(int id, String title, String description, LocalDate dueDate, TaskStatus status, TaskPriority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    // Check if the task is important based on priority
    @ImportantTask
    public boolean isImportant() {
        return this.priority == TaskPriority.HIGH;
    }

    // Check if the task is overdue based on the due date
    @OverDueTask
    public boolean isOverdue() {
        return this.dueDate.isBefore(LocalDate.now()) && this.status != TaskStatus.COMPLETED;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task [id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status=" + status +
                "]";
    }
}
