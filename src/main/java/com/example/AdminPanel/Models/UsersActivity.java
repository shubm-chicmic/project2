package com.example.AdminPanel.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "UsersActivity")
public class UsersActivity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    int id;

    String url;
    String message;
}
