package com.example.Lab4_NenashevDA_StaryginVA.controller;

import com.example.Lab4_NenashevDA_StaryginVA.dto.TaskRequest;
import com.example.Lab4_NenashevDA_StaryginVA.model.Task;
import com.example.Lab4_NenashevDA_StaryginVA.service.TaskService;
import jakarta.jms.Message;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final JmsTemplate jmsTemplate;

    public TaskController(TaskService taskService, JmsTemplate jmsTemplate) {
        this.taskService = taskService;
        this.jmsTemplate = jmsTemplate;
    }

    private void sendEvent(String action, Task task) {
        jmsTemplate.send("changes.topic", session -> {
            Message msg = session.createTextMessage("TASK:" + action);
            msg.setStringProperty("entity", "Task");
            msg.setLongProperty("entityId", task.getId());
            msg.setStringProperty("action", action);
            msg.setStringProperty("payload",
                    "title=" + task.getTitle() +
                            "; completed=" + task.getCompleted() +
                            "; userId=" + (task.getUser() != null ? task.getUser().getId() : null));
            return msg;
        });
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        try {
            Task created = taskService.createTask(request);
            sendEvent("CREATE", created);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task request) {
        return taskService.updateTask(id, request)
                .map(task -> {
                    sendEvent("UPDATE", task);
                    return ResponseEntity.ok(task);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
