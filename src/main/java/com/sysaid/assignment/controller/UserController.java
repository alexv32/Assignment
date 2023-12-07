package com.sysaid.assignment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sysaid.assignment.service.UserServiceImpl;

@Controller
@RequestMapping("/api")
public class UserController {
    
   
    private final UserServiceImpl userService;

    /**
     * Same as TaskController, constructor for dependency injustion
     * @param userService
     */
    public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, Model model) {
        // Do something with the username (e.g., store it in a session attribute)
        model.addAttribute("username", username);
        return "redirect:/dashboard"; // Redirect to a dashboard page
    }


}
