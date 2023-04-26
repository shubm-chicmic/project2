package com.example.AdminPanel.Service;

import com.example.AdminPanel.Models.UsersActivity;
import com.example.AdminPanel.Repository.UserLoggingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserActivityService {
    @Autowired
    UserLoggingRepo userLoggingRepo;
    public List<UsersActivity> getAllActivity(int numberOfActivities) {
        Pageable pageable = PageRequest.of(0, numberOfActivities, Sort.Direction.DESC, "id");
        return userLoggingRepo.findAll(pageable).getContent();
    }
    public void deleteAllActivity() {
        userLoggingRepo.deleteAll();
    }
}
