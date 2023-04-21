package com.example.AdminPanel.Repository;

import com.example.AdminPanel.Models.UserUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUuidRepository extends JpaRepository<UserUuid, Integer> {
    UserUuid getUserTokenByUuid(String uuid);
    int deleteByUuid(String uuid);
}
