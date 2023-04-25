package com.example.AdminPanel.Models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "UsersActivity")
@Builder
@Data
public class UsersActivity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    int id;

    String url;
    String message;
}
