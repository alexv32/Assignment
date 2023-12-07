package com.sysaid.assignment.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.service.UserServiceImpl;

@RestController
public class UserController {
    
   
    private final UserServiceImpl userService;

    /**
     * Same as TaskController, constructor for dependency injustion
     * @param userService
     */
    public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody String username){
        User loggedIn = userService.login(username);

        return new ResponseEntity<>(loggedIn, HttpStatus.OK);
    }
}
