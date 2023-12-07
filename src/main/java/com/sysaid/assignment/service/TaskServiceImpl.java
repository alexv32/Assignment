package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.*;
import com.sysaid.assignment.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public ResponseEntity<List<Task>> getUncompletedTask(String username) throws CustomException{
        User user=userRepository.findByUsername(username);
        List<Task> incompleteTasks= taskRepository.findByUserAndCompletedFalse(user).subList(0, 10);
        if(incompleteTasks.isEmpty()){
            throw new CustomException("No Uncompleted Tasks");
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

    public ResponseEntity<List<Task>> getCompletedTask(String username) throws CustomException {
        User user= userRepository.findByUsername(username);
        List<Task> completeTasks=taskRepository.findByUserAndCompletedTrue(user);
        if(completeTasks.isEmpty()){
            throw new CustomException("No Completed Tasks");
        }
        return new ResponseEntity<>(completeTasks,HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getWishlistedTask(String username) throws CustomException {
        User user= userRepository.findByUsername(username);
        List<Task> wishlistedTasks=taskRepository.findByUserAndWishlistTrue(user);
        if(wishlistedTasks.isEmpty()){
            throw new CustomException("No Wishlisted Tasks");
        }
        return new ResponseEntity<>(wishlistedTasks,HttpStatus.OK);
    }
    public ResponseEntity<List<Task>> getUnWishlistedTask(String username) throws CustomException {
        User user= userRepository.findByUsername(username);
        List<Task> unwishlistedTasks=taskRepository.findByUserAndWishlistFalse(user);
        if(unwishlistedTasks.isEmpty()){
            throw new CustomException("No Unwishlisted Tasks");
        }
        return new ResponseEntity<>(unwishlistedTasks,HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getAllTasks(String username) throws CustomException{
        User user= userRepository.findByUsername(username);
        List<Task> allTasks=taskRepository.findByUser(user);
        if(allTasks.isEmpty()){
            throw new CustomException("No Tasks");
        }
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

    public void completeTask(String username, String key){
        Task task= taskRepository.findByKey(key);
        task.setCompleted(true);
        task.setRate(2);
    }
    

    public void wishlistTask(String username, String key){
        Task task= taskRepository.findByKey(key);
        task.setWishlist(true);
        task.setRate(1);
    }

    public Task getTasksByRating(String username) throws CustomException{
        User user= userRepository.findByUsername(username);
        List<Task> allTasks=sortTasksByRating(taskRepository.findByUser(user));
        if(allTasks.isEmpty()){
            throw new CustomException("No Tasks");
        }

        double random=Math.random()*100;
        if (random < 20) {
            return allTasks.get(1);
        }else if(random < 40) {
            return allTasks.get(2);
        }else if(random < 50) {
            return allTasks.get(3);
        }else if(random < 55) {
            return allTasks.get(4);
        }else if(random < 60) {
            return allTasks.get(5);
        }else{
            return allTasks.get(new Random().nextInt(allTasks.size()));
        }
    }
        

    private List<Task> sortTasksByRating(List<Task> allTasks){

        Collections.sort(allTasks, (task1, task2) -> Integer.compare(task2.getRate(), task1.getRate()));
        return allTasks;
    }
}
