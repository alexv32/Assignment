package com.sysaid.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.service.TaskServiceImpl;
import com.sysaid.assignment.service.UserServiceImpl;

@RestController
public class UserController {
    
   
    private final UserServiceImpl userService;
    /**
     * Same as TaskController, constructor for dependency injustion
     * @param userService
     */
    @Autowired
    public UserController(UserServiceImpl userService,TaskServiceImpl taskService) {
		this.userService = userService;
	}

    
    @PostMapping("/login")
    public String processLoginForm(String username, Model model) {
        userService.SaveUser(username);
        model.addAttribute("username", username);
        // Redirect to the next page
        return "/account";
    }

   /**
    * Getting a user from the database
    * @param user 
    * @return User object holding the pulled data
    */
    @GetMapping("/user/{user}")
    public ResponseEntity<User> getUser(@PathVariable String user){
        User newUser= userService.findByUsername(user);
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }

}
