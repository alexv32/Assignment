package com.sysaid.assignment.service;


import org.springframework.stereotype.Service;

import com.sysaid.assignment.repositories.UserRepository;

@Service
public class UserServiceImpl{
   
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}