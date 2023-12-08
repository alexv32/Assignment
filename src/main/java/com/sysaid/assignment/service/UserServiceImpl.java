package com.sysaid.assignment.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.repositories.UserRepository;

@Service
public class UserServiceImpl{
   
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Checks if there are no users in the repository
     * @return bollean value
     */
    public boolean notEmpty(){
        long users=userRepository.countUser();
        return users>0;
    }
    /**
     * Saves a new user
     * @param username
     */
    public void SaveUser(String username){
        User user = new User(username);
        userRepository.save(user);  
    }
    
    /**
     * 
     * @param username
     * @return user object with that username
     */
    public User findByUsername(String username){
        User user=userRepository.findByUsername(username);
        return user;
    }
    
}