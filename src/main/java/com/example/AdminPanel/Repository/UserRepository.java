package com.example.AdminPanel.Repository;


import com.example.AdminPanel.Entity.UserDto;
import com.example.AdminPanel.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
    @Transactional
    @Modifying
    @Query(
            value = "Update Users us Set us.firstName = :#{#userDto.firstName},us.lastName = :#{#userDto.lastName}," +
                    " us.phoneNo = :#{#userDto.phone}, us.city = :#{#userDto.city} , us.imageUrl = :#{#userDto.url} Where us.email = :email"
    )
    void updateUserByEmail(@Param("email") String email, @Param("userDto")UserDto userDto);
}
