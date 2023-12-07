package com.sysaid.assignment.controller;


import org.springframework.stereotype.Controller;



import com.sysaid.assignment.service.UserServiceImpl;

@Controller
public class UserController {
    
   
    private final UserServiceImpl userService;

    /**
     * Same as TaskController, constructor for dependency injustion
     * @param userService
     */
    public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

   

}
