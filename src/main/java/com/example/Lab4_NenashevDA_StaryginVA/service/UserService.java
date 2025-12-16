package com.example.Lab4_NenashevDA_StaryginVA.service;

import com.example.Lab4_NenashevDA_StaryginVA.model.User;
import com.example.Lab4_NenashevDA_StaryginVA.repository.UserRepository;
import com.example.Lab4_NenashevDA_StaryginVA.dto.ChangeMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        User saved = userRepository.save(user);
        publishChange("CREATE", "User", saved.getId(), toJsonSafe(saved));
        return saved;
    }

    public Optional<User> updateUser(Long id, User updated) {
        return userRepository.findById(id).map(user -> {
            user.setName(updated.getName());
            user.setEmail(updated.getEmail());
            user.setAge(updated.getAge());
            User saved = userRepository.save(user);
            publishChange("UPDATE", "User", saved.getId(), toJsonSafe(saved));
            return saved;
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            publishChange("DELETE", "User", user.getId(), toJsonSafe(user));
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

    private String toJsonSafe(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            return "{}";
        }
    }
}
