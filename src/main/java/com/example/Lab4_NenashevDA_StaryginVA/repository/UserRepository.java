package com.example.Lab4_NenashevDA_StaryginVA.repository;

import com.example.Lab4_NenashevDA_StaryginVA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
