package com.example.Lab4_NenashevDA_StaryginVA.controller;

import com.example.Lab4_NenashevDA_StaryginVA.model.User;
import com.example.Lab4_NenashevDA_StaryginVA.service.UserService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JmsTemplate jmsTemplate;

    public UserController(UserService userService, JmsTemplate jmsTemplate) {
        this.userService = userService;
        this.jmsTemplate = jmsTemplate;
    }

    private void sendEvent(String action, User user) {
        jmsTemplate.send("changes.topic", session -> {
            Message msg = session.createTextMessage("USER:" + action);
            msg.setStringProperty("entity", "User");
            msg.setLongProperty("entityId", user.getId());
            msg.setStringProperty("action", action);
            msg.setStringProperty("payload",
                    "name=" + user.getName() +
                            "; email=" + user.getEmail() +
                            "; age=" + user.getAge());
            return msg;
        });
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        sendEvent("CREATE", created);
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser)
                .map(user -> {
                    sendEvent("UPDATE", user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User userToDelete = userService.getUserById(id).orElse(null);
        if (userToDelete != null && userService.deleteUser(id)) {
            sendEvent("DELETE", userToDelete);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
