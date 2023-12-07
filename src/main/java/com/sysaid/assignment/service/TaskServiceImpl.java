package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.*;
import com.sysaid.assignment.repositories.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements  ITaskService{

    @Value("${external.boredapi.baseURL}")
    private String baseUrl;

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public ResponseEntity<Task> getRandomTask() {
        String endpointUrl = String.format("%s/activity", baseUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<Task> responseEntity = template.getForEntity(endpointUrl, Task.class);

        return responseEntity;
    }

    public ResponseEntity<List<Task>> getUncompletedTask(String username){
        User user=userRepository.findByUsername(username);
        List<Task> incompleteTasks= taskRepository.findByUserAndCompletedFalse(user).subList(0, 10);
        if(incompleteTasks.isEmpty()){
            incompleteTasks=createTasksFromApi(username);
        }
        
        return new ResponseEntity<>(incompleteTasks,HttpStatus.OK);
    }

    public List<Task> createTasksFromApi(String username){
        String endpointUrl = String.format("%s/activity", baseUrl);
        List<Task> newTasks=new ArrayList<>();
        User user = userRepository.findByUsername(username);
        for(int i=0;i<10;i++){

                ResponseEntity<Task> responseEntity =new RestTemplate().getForEntity(endpointUrl, Task.class);
                Task task= new Task(responseEntity.getBody());
                task.setUser(user);
                newTasks.add(task);
                
            }
        
        taskRepository.saveAll(newTasks);
        return newTasks;
    }

    public ResponseEntity<List<Task>> getCompletedTask(String username) {
        User user= userRepository.findByUsername(username);
        List<Task> completeTasks=taskRepository.findByUserAndCompletedTrue(user);
        return new ResponseEntity<>(completeTasks,HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getWishlistedTask(String username) {
        User user= userRepository.findByUsername(username);
        List<Task> wishlistedTasks=taskRepository.findByUserAndWishlistTrue(user);
        return new ResponseEntity<>(wishlistedTasks,HttpStatus.OK);
    }
    public ResponseEntity<List<Task>> getUnWishlistedTask(String username) {
        User user= userRepository.findByUsername(username);
        List<Task> unwishlistedTasks=taskRepository.findByUserAndWishlistFalse(user);
        return new ResponseEntity<>(unwishlistedTasks,HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getAllTasks(String username) {
        User user= userRepository.findByUsername(username);
        List<Task> allTasks=taskRepository.findByUser(user);
        return new ResponseEntity<>(allTasks,HttpStatus.OK);
    }

    public void createTask(String username){
        User user= userRepository.findByUsername(username);
        String endpointUrl = String.format("%s/activity", baseUrl);

        ResponseEntity<Task> responseEntity =new RestTemplate().getForEntity(endpointUrl, Task.class);
        Task task= new Task(responseEntity.getBody());
        task.setUser(user);
        taskRepository.save(task);

    }

    public void deleteTask(String username,String key){
        User user= userRepository.findByUsername(username);
        Task task= taskRepository.findByKey(key);
        if(task!=null && task.getUser().equals(user))
            taskRepository.deleteByKey(key);
    }

    public void completeTask(String username, String key) {
        Task task= taskRepository.findByKey(key);
        task.setCompleted(true);
        task.setRate(2);
    }
    

    public void wishlistTask(String username, String key) {
        Task task= taskRepository.findByKey(key);
        task.setWishlist(true);
        task.setRate(1);
    }
}
