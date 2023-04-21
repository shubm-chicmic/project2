package com.example.AdminPanel.Repository;


import com.example.AdminPanel.Models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles findRolesByUserId(@Param("userId")int userId);
}
