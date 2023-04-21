package com.example.AdminPanel.Repository;


import com.example.AdminPanel.Models.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UsersRoles, Integer> {
      @Query(
              "Select id From UsersRoles ur Where ur.Roles = :roles"
      )
      Integer findIdByRoles(@Param("roles") String roles);
}
