package com.sysaid.assignment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @PostMapping("/login")
    public String processLoginForm(String username, Model model) {
        userService.SaveUser(username);
        model.addAttribute("username", username);
        // Redirect to the next page
        return "/account";
    }

    @GetMapping("/account")
    public String showAccountPage(@PathVariable String username) {

        return "account";
    }   

}
