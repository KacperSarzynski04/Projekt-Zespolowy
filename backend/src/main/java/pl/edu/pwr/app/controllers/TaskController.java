package pl.edu.pwr.app.controllers;

import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.app.models.Task;
import pl.edu.pwr.app.repositories.TaskRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    void addTask(@RequestBody Task task) {
        taskRepository.save(task);
    }
}
