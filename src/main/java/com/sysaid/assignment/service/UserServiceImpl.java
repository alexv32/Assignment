package com.sysaid.assignment.service;

import org.springframework.stereotype.Service;

import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
   

    
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username){
        User user= userRepository.findByUsername(username);

        if(user==null){
            user=new User(username);
            userRepository.save(user);
        }
        
        return user;
    }
}