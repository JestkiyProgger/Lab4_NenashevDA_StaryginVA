package com.example.Lab4_NenashevDA_StaryginVA.repository;

import com.example.Lab4_NenashevDA_StaryginVA.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long userId);
}
