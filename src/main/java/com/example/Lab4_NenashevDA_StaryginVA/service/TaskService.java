package com.example.Lab4_NenashevDA_StaryginVA.service;

import com.example.Lab4_NenashevDA_StaryginVA.dto.TaskRequest;
import com.example.Lab4_NenashevDA_StaryginVA.model.Task;
import com.example.Lab4_NenashevDA_StaryginVA.model.User;
import com.example.Lab4_NenashevDA_StaryginVA.repository.TaskRepository;
import com.example.Lab4_NenashevDA_StaryginVA.dto.ChangeMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }

    public Task createTask(TaskRequest request) {
        User user = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setUser(user);
        task.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);

        Task saved = taskRepository.save(task);
        publishChange("CREATE", "Task", saved.getId(), toJsonSafe(saved));
        return saved;
    }

    public Optional<Task> updateTask(Long id, Task updated) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updated.getTitle());
            task.setCompleted(updated.getCompleted());

            if (updated.getUser() != null && updated.getUser().getId() != null) {
                task.setUser(updated.getUser());
            }
            Task saved = taskRepository.save(task);
            publishChange("UPDATE", "Task", saved.getId(), toJsonSafe(saved));
            return saved;
        });
    }

    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            publishChange("DELETE", "Task", task.getId(), toJsonSafe(task));
            return true;
        }).orElse(false);
    }

    private void publishChange(String type, String entityName, Long entityId, String payload) {
        ChangeMessage msg = new ChangeMessage();
        msg.setChangeType(type);
        msg.setEntityName(entityName);
        msg.setEntityId(entityId);
        msg.setPayload(payload);
        jmsTemplate.convertAndSend("changes.topic", msg);
    }

    private String toJsonSafe(Task task) {
        try {
            return objectMapper.writeValueAsString(task);
        } catch (Exception e) {
            return "{}";
        }
    }
}
