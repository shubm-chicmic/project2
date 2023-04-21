package com.example.AdminPanel.UserActivity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitor")
@Data
public class Visitor {

    @Id
    @GeneratedValue // (strategy = GenerationType.IDENTITY)
    private int id;
    private String user;
    private String ip;
    private String method;
    private String url;
    private String page;
    private String queryString;
    private String refererPage;
    private String userAgent;
    private LocalDateTime loggedTime;
    private boolean uniqueVisit;

}
