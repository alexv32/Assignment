package com.sysaid.assignment.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController implements ErrorController{

    private static final String PATH = "/error";

    @GetMapping("/home")
    public String home() {
        return "index"; // Not "index.html" or "/index.html"
    }

    @RequestMapping(value = PATH)
    public String error() {
        // Provide the name of your custom error view (e.g., "error")
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }


}
