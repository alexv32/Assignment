package com.sysaid.assignment.service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.repositories.UserRepository;

@Component
public class DataInitilization {

    private final UserRepository userRepository;
    private final TaskServiceImpl taskService;
    
    @Autowired
    public DataInitilization(UserRepository userRepository, TaskServiceImpl taskService) {
        this.userRepository=userRepository;
        this.taskService=taskService;
    }

    /**
     * Post contruct to initialize a database with user and tasks, as if we have a logged in user
     * Creates and saves a new user and create tasks for it
     */
    @PostConstruct
    @Transactional
    public void PostConstruct(){
        try{
            userRepository.save(new User("Alex"));
            taskService.createTasksFromApi("Alex");
            //taskService.createTask("Alex");
        }catch(Exception e){
            System.out.println("Error while initializing" + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
