package com.example.contacatapi.contacatapi.repository;

import com.example.contacatapi.contacatapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
