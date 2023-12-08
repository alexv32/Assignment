package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.Task;
import java.util.List;
import org.springframework.http.ResponseEntity;

/*
 * Interface for the task service, all the methods are implemented later
 * Get methods for each data we need to get
 * and creation and deletion of tasks, and including the rated tasks
 */
public interface ITaskService {
    public ResponseEntity<Task> getRandomTask();
    public ResponseEntity<List<Task>> getUncompletedTask(String user,String type) throws CustomException;
    public ResponseEntity<List<Task>> getCompletedTask(String username) throws CustomException;
    public ResponseEntity<List<Task>> getWishlistedTask(String username) throws CustomException;
    public ResponseEntity<List<Task>> getUnWishlistedTask(String username) throws CustomException;
    public ResponseEntity<List<Task>> getAllTasks(String username) throws CustomException;
    public void createTask(String username);
    public void deleteTask(String username,String key);
    public void completeTask(String username, String key);
    public void wishlistTask(String username, String key);
    public ResponseEntity<Task> getTasksByRating(String username) throws CustomException;
}
