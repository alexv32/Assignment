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

    public boolean notEmpty(){
        long users=userRepository.countUser();
        return users>0;
    }
    public void SaveUser(String username){
        User user = new User(username);
        userRepository.save(user);  
    }
    
    
}