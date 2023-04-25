package com.example.AdminPanel.Repository;

import com.example.AdminPanel.Models.UsersActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoggingRepo extends JpaRepository<UsersActivity, Integer> {
}
