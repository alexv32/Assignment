package com.sysaid.assignment.service;

/*
 * Custom exception class to use for cases where are no tasks
 */
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}